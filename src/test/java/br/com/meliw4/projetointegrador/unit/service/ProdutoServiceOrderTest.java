package br.com.meliw4.projetointegrador.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.service.ProdutoService;
import br.com.meliw4.projetointegrador.service.ProdutoServiceOrder;

public class ProdutoServiceOrderTest {
	private static ProdutoService produtoService = mock(ProdutoService.class);
	private static ProdutoServiceOrder produtoServiceOrder = new ProdutoServiceOrder(produtoService);

	@Test
	void shouldFindProdutoById() {
		when(produtoService.findById(1L)).thenReturn(Produto.builder().id(1L).build());

		Produto response = produtoServiceOrder.findProdutoById(1L);
		assertEquals(1L, response.getId());
	}
}
