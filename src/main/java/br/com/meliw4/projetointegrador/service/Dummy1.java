package br.com.meliw4.projetointegrador.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.meliw4.projetointegrador.dto.request.RequestAddressLatLngDTO;

public class Dummy1 {
	public static void connect() {
		try {
			String endereco = "Avenida Paulista 1000";
			URL url = new URL("https://nominatim.openstreetmap.org/search?q=" + endereco + "&limit=1&format=json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();

			if (responsecode != 200) {
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			} else {
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder response = new StringBuilder();
				String line = null;

				while ((line = reader.readLine()) != null) {
					response.append(line + "\n");
				}
				ObjectMapper mapper = new ObjectMapper();
				mapper.registerModule(new JavaTimeModule());
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				CollectionType mapCollectionType = mapper.getTypeFactory()
						.constructCollectionType(List.class, RequestAddressLatLngDTO.class);

				List<RequestAddressLatLngDTO> value = mapper.readValue(response.toString(), mapCollectionType);

				System.out.println("==============================================");
				System.out.println(value.get(0).toString());
				System.out.println("==============================================");
				if (reader != null) {
					closeBufferReader(reader);
				}
			}

			// Disconnect the HttpURLConnection stream
			// conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void closeBufferReader(BufferedReader bufferedReader) {
		try {
			bufferedReader.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
