package br.com.meliw4.projetointegrador.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.meliw4.projetointegrador.entity.Carrinho;
import br.com.meliw4.projetointegrador.entity.ProdutoCarrinho;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.ProdutoCarrinhoRepository;
import br.com.meliw4.projetointegrador.service.ProdutoCarrinhoService;

public class ProdutoCarrinhoServiceTest {
	private static ProdutoCarrinhoRepository produtoCarrinhoRepository = mock(ProdutoCarrinhoRepository.class);
	private static ProdutoCarrinhoService produtoCarrinhoService = new ProdutoCarrinhoService(
			produtoCarrinhoRepository);

	@Test
	void shouldBuscaProdutosCarrinhoById() {
		List<ProdutoCarrinho> carrinhos = new ArrayList<>();
		ProdutoCarrinho produtoCarrinho1 = ProdutoCarrinho.builder().id(1L).carrinho(Carrinho.builder().id(1L).build())
				.build();
		ProdutoCarrinho produtoCarrinho2 = ProdutoCarrinho.builder().id(2L).carrinho(Carrinho.builder().id(1L).build())
				.build();
		carrinhos.add(produtoCarrinho1);
		carrinhos.add(produtoCarrinho2);

		when(produtoCarrinhoRepository.findByCarrinho_Id(1L)).thenReturn(carrinhos);

		List<ProdutoCarrinho> response = produtoCarrinhoService.buscaProdutosCarrinhoById(1L);
		assertEquals(1L, response.get(0).getCarrinho().getId());
		assertEquals(2, response.size());
	}

	@Test
	void shouldThrowBusinessValidationExceptionWhenListNullInAtualizaCarrinho() {
		assertThrows(BusinessValidationException.class,
				() -> produtoCarrinhoService.buscaProdutosCarrinhoById(any()));
	}

	@Test
	void testSalvaProdutoCarrinho() {
		assertDoesNotThrow(() -> produtoCarrinhoService.salvaProdutoCarrinho(any()));
	}
}
