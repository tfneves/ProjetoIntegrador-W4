package br.com.meliw4.projetointegrador.unit.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.meliw4.projetointegrador.dto.ProdutoCarrinhoDTO;
import br.com.meliw4.projetointegrador.entity.Lote;
import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import br.com.meliw4.projetointegrador.exception.NotFoundException;
import br.com.meliw4.projetointegrador.exception.OrderCheckoutException;
import br.com.meliw4.projetointegrador.repository.ProdutoVendedorRepository;
import br.com.meliw4.projetointegrador.service.ProdutoVendedorService;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoVendedorServiceTest {
	private static ProdutoVendedorService produtoVendedorService;
	private static ProdutoVendedorRepository produtoVendedorRepository = mock(ProdutoVendedorRepository.class);
	private static List<ProdutoVendedor> produtoVendedores;

	@BeforeAll
	static void setUp() {
		produtoVendedorService = new ProdutoVendedorService(produtoVendedorRepository);
	}

	@BeforeEach
	void setUpEach() {
		produtoVendedores = new ArrayList<>();
		produtoVendedores = makeProdutoVendedores();
	}

	private static List<ProdutoVendedor> makeProdutoVendedores() {
		produtoVendedores.add(
				ProdutoVendedor.builder().id(1L)
						.lote(Lote.builder().id(1L).build())
						.produto(Produto.builder().id(1L).build())
						.quantidadeAtual(1)
						.build());
		produtoVendedores.add(
				ProdutoVendedor.builder().id(2L)
						.lote(Lote.builder().id(1L).build())
						.produto(Produto.builder().id(2L).build())
						.quantidadeAtual(2)
						.build());
		produtoVendedores.add(
				ProdutoVendedor.builder().id(3L)
						.lote(Lote.builder().id(2L).build())
						.produto(Produto.builder().id(2L).build())
						.quantidadeAtual(3)
						.build());
		produtoVendedores.add(
				ProdutoVendedor.builder().id(4L)
						.lote(Lote.builder().id(2L).build())
						.produto(Produto.builder().id(2L).build())
						.quantidadeAtual(4)
						.build());

		return produtoVendedores;
	}

	@Test
	void shouldDevolveProdutoEstoque() {
		List<ProdutoCarrinhoDTO> produtoCarrinhoDTOs = new ArrayList<>();
		ProdutoCarrinhoDTO produtoCarrinhoDTO1 = ProdutoCarrinhoDTO.builder().anuncioId(1L).quantidade(1).build();
		ProdutoCarrinhoDTO produtoCarrinhoDTO2 = ProdutoCarrinhoDTO.builder().anuncioId(2L).quantidade(2).build();
		produtoCarrinhoDTOs.add(produtoCarrinhoDTO1);
		produtoCarrinhoDTOs.add(produtoCarrinhoDTO2);

		ProdutoVendedorService spyProdutoVendedorService = spy(produtoVendedorService);
		doReturn(produtoVendedores.get(0)).when(spyProdutoVendedorService).getProdutoById(1L);
		doReturn(produtoVendedores.get(1)).when(spyProdutoVendedorService).getProdutoById(2L);

		spyProdutoVendedorService.devolveProdutoEstoque(produtoCarrinhoDTOs);
		assertEquals(2, produtoVendedores.get(0).getQuantidadeAtual());
		assertEquals(4, produtoVendedores.get(1).getQuantidadeAtual());
	}

	@Test
	void shouldThrowNotFoundExceptionIfNullListInFindAll() {
		assertThrows(NotFoundException.class,
				() -> produtoVendedorService.findAll());
	}

	@Test
	void shouldFindAll() {
		when(produtoVendedorRepository.findAll()).thenReturn(produtoVendedores);
		List<ProdutoVendedor> response = produtoVendedorService.findAll();
		assertEquals(4, response.size());
	}

	@Test
	void shouldFindByLoteIdAndProdutoId() {
		ProdutoVendedor responseProdutoVendedores = produtoVendedores.get(0);
		when(produtoVendedorRepository.findByLoteIdAndProdutoId(1L, 1L)).thenReturn(responseProdutoVendedores);

		ProdutoVendedor response = produtoVendedorService.findByLoteIdAndProdutoId(1L, 1L);
		assertEquals(1L, response.getLote().getId());
		assertEquals(1L, response.getProduto().getId());
	}

	@Test
	void shouldFindProdutoVendedorByProduto_Id() {
		List<ProdutoVendedor> responseProdutoVendedores = new ArrayList<>();
		responseProdutoVendedores.add(produtoVendedores.get(1));
		responseProdutoVendedores.add(produtoVendedores.get(2));
		responseProdutoVendedores.add(produtoVendedores.get(3));
		when(produtoVendedorRepository.findByProduto_Id(2L)).thenReturn(Optional.of(responseProdutoVendedores));

		assertEquals(3, responseProdutoVendedores.size());
		assertEquals(2L, responseProdutoVendedores.get(0).getProduto().getId());
	}

	@Test
	void shouldThrowNotFoundExceptionIfNullListInFindProdutoVendedorByProduto_Id() {
		assertThrows(NotFoundException.class,
				() -> produtoVendedorService.findProdutoVendedorByProduto_Id(any()));
	}

	@Test
	void shouldThrowNotFoundExceptionIfNullListInGetProdutoById() {
		assertThrows(OrderCheckoutException.class,
				() -> produtoVendedorService.getProdutoById(any()));
	}

	@Test
	void shouldGetProdutoById() {
		when(produtoVendedorRepository.findById(1L)).thenReturn(Optional.of(produtoVendedores.get(0)));
		ProdutoVendedor response = produtoVendedorService.getProdutoById(1L);
		assertEquals(1L, response.getId());
		assertEquals(1L, response.getLote().getId());
	}

	@Test
	void testSave() {
		assertDoesNotThrow(
				() -> produtoVendedorService.save(any(ProdutoVendedor.class)));
	}

	@Test
	void shouldUpdateEstoqueProduto() {
		ProdutoVendedorService spyProdutoVendedorService = spy(produtoVendedorService);
		doReturn(produtoVendedores.get(0)).when(spyProdutoVendedorService).getProdutoById(1L);
		assertDoesNotThrow(
				() -> produtoVendedorRepository.save(any(ProdutoVendedor.class)));

		spyProdutoVendedorService.updateEstoqueProduto(11, 1L);
		assertEquals(11, produtoVendedores.get(0).getQuantidadeAtual());
		assertEquals(1L, produtoVendedores.get(0).getId());
	}
}
