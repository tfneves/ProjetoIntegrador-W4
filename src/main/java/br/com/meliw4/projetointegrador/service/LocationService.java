package br.com.meliw4.projetointegrador.service;

import java.util.List;

import br.com.meliw4.projetointegrador.dto.response.ArmazemResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.ResponseAddressLatLonDTO;
import br.com.meliw4.projetointegrador.dto.response.ResponseLatLonDTO;

public interface LocationService {
	ArmazemResponseDTO findArmazemMaisProximo(String userEndereco);

	List<ArmazemResponseDTO> resolveMinDistance(String userEndereco);

	ResponseAddressLatLonDTO findLocation(String endereco);

	ResponseLatLonDTO findLocation(String lat, String lon);
}
