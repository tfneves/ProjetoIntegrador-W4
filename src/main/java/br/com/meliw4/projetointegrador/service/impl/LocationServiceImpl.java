package br.com.meliw4.projetointegrador.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.meliw4.projetointegrador.dto.response.ArmazemResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.ResponseAddressLatLonDTO;
import br.com.meliw4.projetointegrador.dto.response.ResponseLatLonDTO;
import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.exception.APILocationException;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.exception.InternalServerErrorException;
import br.com.meliw4.projetointegrador.exception.NotFoundException;
import br.com.meliw4.projetointegrador.service.ArmazemService;
import br.com.meliw4.projetointegrador.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {
	private final ArmazemService armazemService;

	public LocationServiceImpl(ArmazemService armazemService) {
		this.armazemService = armazemService;
	}

	@Override
	public ArmazemResponseDTO findArmazemMaisProximo(String userEndereco) {
		validateEndereco(userEndereco);
		return resolveMinDistance(userEndereco).get(0);
	}

	private void validateEndereco(String userEndereco) {
		if (userEndereco.isEmpty()) {
			throw new BusinessValidationException("Endereço inválido.");
		}
	}

	@Override
	public List<ArmazemResponseDTO> resolveMinDistance(String userEndereco) {
		// Compare 2 Armazens a1 , a2
		// Comparator<Armazem> comparatorMinDistEnderecosMap = (a1,
		// a2) -> calculateDistance(this.findLocation(userEndereco),
		// this.findLocation(a1.getEndereco()))
		// .compareTo(calculateDistance(this.findLocation(userEndereco),
		// this.findLocation(a2.getEndereco())));

		// Calcula a distancia min. entre Armazem a e location
		ResponseAddressLatLonDTO userLocation = findLocation(userEndereco);
		Function<Armazem, Double> functionDistEnderecos = a -> calculateDistance(
				Double.valueOf(userLocation.getLat()), Double.valueOf(userLocation.getLon()),
				a.getLat(), a.getLon());

		// Retorna endereço associado com lat lon
		Function<Armazem, String> functionResolveAddress = a -> findLocation(
				a.getLat().toString(), a.getLon().toString())
						.getEndereco();

		List<ArmazemResponseDTO> listArmazemDistEndereco = armazemService.findAll().stream()
				.map(a -> ArmazemResponseDTO.toDTO(
						a,
						functionDistEnderecos.apply(a),
						functionResolveAddress.apply(a)))
				.collect(Collectors.toList());

		if (listArmazemDistEndereco.isEmpty()) {
			throw new NotFoundException("Não localizado armazém para a região.");
		}

		return listArmazemDistEndereco.stream()
				.sorted(Comparator.comparing(ArmazemResponseDTO::getDistancia))
				.collect(Collectors.toList());
	}

	private Double calculateDistance(Double userLat, Double userLon, Double armazemLat, Double armazemLon) {
		if (userLat == null || userLon == null || armazemLat == null || armazemLon == null) {
			throw new InternalServerErrorException("Erro ao executar serviço de localização.");
		}
		double theta = userLon - armazemLon;
		double dist = Math.sin(Math.toRadians(userLat)) * Math.sin(Math.toRadians(armazemLat))
				+ Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(armazemLat))
						* Math.cos(Math.toRadians(theta));
		dist = Math.acos(dist);
		dist = Math.toDegrees(dist);
		dist = dist * 60 * 1.1515 * 1.609344;
		return (dist);
	}

	@Override
	public ResponseAddressLatLonDTO findLocation(String endereco) {
		String stringURL = "https://nominatim.openstreetmap.org/search?q=" + endereco + "&limit=1&format=json";
		return mapToRequestSearchDTO(connect(stringURL));
	}

	@Override
	public ResponseLatLonDTO findLocation(String lat, String lon) {
		String stringURL = "https://nominatim.openstreetmap.org/reverse?lat=" + lat + "&lon=" + lon
				+ "&limit=1&format=json";
		return mapToRequestReverseSearchDTO(connect(stringURL));
	}

	private String connect(String stringURL) {
		StringBuilder responseJSON = new StringBuilder();
		try {
			URL url = new URL(stringURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			Integer responsecode = conn.getResponseCode();

			if (responsecode != 200) {
				throw new APILocationException(
						"Não foi possível conectar ao sistema de localização", Integer.toString(responsecode));
			} else {
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = null;
				while ((line = reader.readLine()) != null) {
					responseJSON.append(line + "\n");
				}
				closeBufferReader(reader);
			}
		} catch (IOException ioe) {
			throw new InternalServerErrorException("Erro ao executar serviço de localização.");
		}
		return responseJSON.toString();
	}

	private ResponseAddressLatLonDTO mapToRequestSearchDTO(String response) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		CollectionType mapCollectionType = mapper.getTypeFactory()
				.constructCollectionType(List.class, ResponseAddressLatLonDTO.class);
		List<ResponseAddressLatLonDTO> responseDTO = null;
		try {
			responseDTO = mapper.readValue(response, mapCollectionType);
		} catch (JsonProcessingException e) {
			throw new InternalServerErrorException("Erro ao executar serviço de localização.");
		}
		if (responseDTO == null) {
			throw new InternalServerErrorException("Erro ao executar serviço de localização.");
		}

		return responseDTO.get(0);
	}

	private ResponseLatLonDTO mapToRequestReverseSearchDTO(String response) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ResponseLatLonDTO responseDTO = null;
		try {
			responseDTO = mapper.readValue(response, ResponseLatLonDTO.class);
		} catch (JsonProcessingException e) {
			throw new InternalServerErrorException("Erro ao executar serviço de localização.");
		}

		return responseDTO;
	}

	private void closeBufferReader(BufferedReader bufferedReader) {
		try {
			bufferedReader.close();
		} catch (IOException ioe) {
			throw new InternalServerErrorException("Erro ao desconectar do serviço de localização.");
		}
	}
}
