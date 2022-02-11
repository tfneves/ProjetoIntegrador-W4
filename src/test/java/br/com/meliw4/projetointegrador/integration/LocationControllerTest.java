package br.com.meliw4.projetointegrador.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.meliw4.projetointegrador.dto.ArmazemDTO;
import br.com.meliw4.projetointegrador.dto.response.ArmazemResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.ResponseAddressLatLonDTO;
import br.com.meliw4.projetointegrador.dto.response.ResponseLatLonDTO;
import br.com.meliw4.projetointegrador.exception.APILocationException;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.net.URI;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class LocationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldFindLocationBasedOnUserAddress() throws Exception {
		URI uri = new URI("/api/v1/warehouse/location");
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
						.get(uri)
						.param("address", "Avenida Paulista 1000"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String json = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule())
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ResponseLatLonDTO responseDTO = mapper.readValue(json,
				ResponseLatLonDTO.class);

		assertInstanceOf(ResponseLatLonDTO.class, responseDTO);
		assertEquals(55583204, responseDTO.getPlaceId());
	}

	@Test
	void shouldFindClosestArmazemLocationBasedOnUserAddress() throws Exception {
		URI uri = new URI("/api/v1/warehouse/closest");
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
						.get(uri)
						.param("address", "Avenida Paulista 1000"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String json = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule())
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArmazemResponseDTO responseDTO = mapper.readValue(json,
				ArmazemResponseDTO.class);

		assertInstanceOf(ArmazemResponseDTO.class, responseDTO);
		assertEquals(222.0d, responseDTO.getVolume());
		assertEquals("Armazem2", responseDTO.getNome());
	}

	@Test
	void shouldFindArmazemLocationBasedOnLatLon() throws Exception {
		URI uri = new URI("/api/v1/warehouse/closestlist");
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
						.get(uri)
						.param("address", "Avenida Paulista 1000"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String json = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule())
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		CollectionType mapCollectionType = mapper.getTypeFactory()
				.constructCollectionType(List.class, ArmazemResponseDTO.class);
		List<ArmazemResponseDTO> responseDTO = mapper.readValue(json,
				mapCollectionType);

		assertEquals(222.0d, responseDTO.get(0).getVolume());
		assertEquals("Armazem2", responseDTO.get(0).getNome());
		assertEquals(333.0d, responseDTO.get(1).getVolume());
	}
}
