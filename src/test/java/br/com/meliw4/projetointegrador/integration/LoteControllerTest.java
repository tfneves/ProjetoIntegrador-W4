package br.com.meliw4.projetointegrador.integration;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class LoteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(0)
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

/*	@Order(1)
	@Test
	public void shouldUpdateLote() throws Exception {
		LocalDate dueDate = LocalDate.now().plusDays(60);
		URI uri = new URI("/api/v1/fresh-products/inboundorder/");
		String json = "{\"loteId\":1,\"produtosUpdateDTO\":[{\"id\":2,\"quantidadeRetira\":1}]}";
		mockMvc.perform(
			MockMvcRequestBuilders
				.put(uri).content(json).contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(201));
	}
*/
}
