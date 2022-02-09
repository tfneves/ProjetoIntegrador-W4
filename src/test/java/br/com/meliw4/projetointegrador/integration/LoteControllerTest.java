package br.com.meliw4.projetointegrador.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.meliw4.projetointegrador.response.LoteProdutosVencimentoResponse;
import br.com.meliw4.projetointegrador.response.LotesSetorVencimentoResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class LoteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldSaveLote() throws Exception {
		LocalDate dueDate = LocalDate.now().plusDays(60);
		URI uri = new URI("/api/v1/fresh-products/inboundorder/");
		String json = "{\"setorId\":1,\"armazemId\":1,\"vendedorId\":1,\"representanteId\":1," +
				"\"produtosDTO\":[{\"id\":1,\"nome\":\"banana\",\"dataVencimento\":\"" + dueDate + "\",\"volume\":1," +
				"\"quantidadeInicial\":2,\"quantidadeAtual\":2,\"dataManufatura\":\"2021-10-10\"," +
				"\"produtoCategoria\":{\"categoria\":\"FS\",\"temperaturaMinima\":19.0},\"temperaturaAtual\":25.0," +
				"\"preco\":2},{\"id\":2,\"nome\":\"naranja\",\"dataVencimento\":\"" + dueDate + "\",\"volume\":1," +
				"\"quantidadeInicial\":2,\"quantidadeAtual\":2,\"dataManufatura\":\"2021-10-10\"," +
				"\"produtoCategoria\":{\"categoria\":\"FS\",\"temperaturaMinima\":19.0},\"temperaturaAtual\":25.0," +
				"\"preco\":2}]}";
		mockMvc.perform(
				MockMvcRequestBuilders
						.post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(201));
	}

	@Test
	public void shouldUpdateLote() throws Exception {
		URI uri = new URI("/api/v1/fresh-products/inboundorder/");
		String json = "{\"loteId\":1,\"produtosUpdateDTO\":[{\"id\":1,\"quantidadeRetira\":1}]}";
		mockMvc.perform(
				MockMvcRequestBuilders
						.put(uri).content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(201));
	}

	@Test
	void testGetLotesProdutosOrderedAndFilteredByDueDate() throws Exception {
		URI uri = new URI("/api/v1/fresh-products/due-date/");
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
						.get(uri)
						.param("section", "1")
						.param("days", "100"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String json = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule())
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		LotesSetorVencimentoResponse value = mapper.readValue(json, LotesSetorVencimentoResponse.class);

		assertInstanceOf(LotesSetorVencimentoResponse.class, value);
		assertEquals(1L, value.getEstoque().get(0).getSetorId());
	}

	@Test
	void testGetProdutosInSetorsOrderedAndFilteredByDueDate() throws Exception {
		URI uri = new URI("/api/v1/fresh-products/due-date/list");
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders
						.get(uri)
						.param("category", "FS")
						.param("order", "desc")
						.param("days", "1000"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String json = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule())
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		CollectionType mapCollectionType = mapper.getTypeFactory()
				.constructCollectionType(List.class, LoteProdutosVencimentoResponse.class);
		List<LoteProdutosVencimentoResponse> value = mapper.readValue(json, mapCollectionType);

		assertInstanceOf(LoteProdutosVencimentoResponse.class, value.get(0));
		assertEquals(1L, value.get(0).getSetorId());
	}
}
