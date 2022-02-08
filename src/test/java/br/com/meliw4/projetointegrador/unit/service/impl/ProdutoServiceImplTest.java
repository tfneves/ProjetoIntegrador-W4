package br.com.meliw4.projetointegrador.unit.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.com.meliw4.projetointegrador.dto.response.ArmazemProdutoResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.ProdutoResponseDTO;
import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Lote;
import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.entity.ProdutoCategoria;
import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import br.com.meliw4.projetointegrador.exception.NotFoundException;
import br.com.meliw4.projetointegrador.repository.ProdutoRepository;
import br.com.meliw4.projetointegrador.response.ProdutoSetorResponse;
import br.com.meliw4.projetointegrador.response.ProdutoVendedorResponse;
import br.com.meliw4.projetointegrador.service.ProdutoService;
import br.com.meliw4.projetointegrador.service.ProdutoVendedorService;
import br.com.meliw4.projetointegrador.service.impl.ProdutoServiceImpl;

public class ProdutoServiceImplTest {
	private static ProdutoService produtoService;
	private static ProdutoRepository produtoRepository = mock(ProdutoRepository.class);
	private static ProdutoVendedorService produtoVendedorService = mock(ProdutoVendedorService.class);
	private static List<ProdutoResponseDTO> produtoResponseDTOs = new ArrayList<>();
	private static List<Produto> produtos = new ArrayList<>();
	private static List<ProdutoVendedor> produtoVendedores = new ArrayList<>();

	@BeforeAll
	public static void setUp() {
		produtoService = new ProdutoServiceImpl(produtoRepository, produtoVendedorService);
		produtoResponseDTOs = makeProdutosResponseDTO();
		produtoVendedores = makeProdutoVendedores();
		produtos = makeProdutos();
	}

	private static List<ProdutoResponseDTO> makeProdutosResponseDTO() {
		List<Long> vendedoresId = new ArrayList<>();
		vendedoresId.add(1L);
		vendedoresId.add(2L);
		vendedoresId.add(3L);
		produtoResponseDTOs.add(ProdutoResponseDTO.builder()
				.id(1L).nome("Produto Fresco 1").volume(1.0).produtoCategoria(
						ProdutoCategoria.builder().categoria(Categoria.FS).temperaturaMinima(5.0f).build())
				.vendedoresId(vendedoresId).build());

		produtoResponseDTOs.add(ProdutoResponseDTO.builder()
				.id(2L).nome("Produto Fresco 2").volume(2.0).produtoCategoria(
						ProdutoCategoria.builder().categoria(Categoria.FS).temperaturaMinima(5.0f).build())
				.vendedoresId(vendedoresId).build());

		produtoResponseDTOs.add(ProdutoResponseDTO.builder()
				.id(3L).nome("Produto Congelado 1").volume(3.0).produtoCategoria(
						ProdutoCategoria.builder().categoria(Categoria.FF).temperaturaMinima(-15.0f).build())
				.vendedoresId(vendedoresId).build());

		return produtoResponseDTOs;
	}

	private static List<Produto> makeProdutos() {
		List<ProdutoVendedor> produtoVendedores1 = new ArrayList<>();
		produtoVendedores1.add(produtoVendedores.get(0));
		produtoVendedores1.add(produtoVendedores.get(1));
		produtoVendedores1.add(produtoVendedores.get(3));
		produtoVendedores1.add(produtoVendedores.get(4));
		produtoVendedores1.add(produtoVendedores.get(5));

		List<ProdutoVendedor> produtoVendedores2 = new ArrayList<>();
		produtoVendedores2.add(produtoVendedores.get(2));
		produtoVendedores2.add(produtoVendedores.get(6));

		List<ProdutoVendedor> produtoVendedores3 = new ArrayList<>();
		produtoVendedores3.add(produtoVendedores.get(7));

		produtos.add(new Produto(1L, "Produto Fresco 1", 1.0,
				ProdutoCategoria.builder().categoria(Categoria.FS).temperaturaMinima(5.0f).build(),
				produtoVendedores1));
		produtos.add(new Produto(2L, "Produto Fresco 2", 2.0,
				ProdutoCategoria.builder().categoria(Categoria.FS).temperaturaMinima(5.0f).build(),
				produtoVendedores2));
		produtos.add(new Produto(3L, "Produto Congelado 1", 3.0,
				ProdutoCategoria.builder().categoria(Categoria.FF).temperaturaMinima(-15.0f).build(),
				produtoVendedores3));
		return produtos;
	}

	private static List<ProdutoVendedor> makeProdutoVendedores() {
		Lote lote1 = Lote.builder().id(1L).build();
		lote1.setRepresentante(Representante.builder().id(1L).armazem(Armazem.builder().id(1L).build()).build());
		lote1.setSetor(Setor.builder().id(1L).armazem(new Armazem(1L, "Aramazem 1", 111d, null)).build());
		Lote lote2 = Lote.builder().id(2L).build();
		lote2.setRepresentante(Representante.builder().id(2L).armazem(Armazem.builder().id(2L).build()).build());
		lote2.setSetor(Setor.builder().id(2L).armazem(new Armazem(2L, "Aramazem 2", 222d, null)).build());

		produtoVendedores
				.add(ProdutoVendedor.builder().id(1L)
						.quantidadeAtual(1).lote(lote1)
						.dataVencimento(LocalDate.now().plusDays(1))
						.produto(Produto.builder().id(1L).nome("Produto Fresco 1").produtoCategoria(
								ProdutoCategoria.builder().categoria(Categoria.FS).build())
								.build())
						.vendedor(Vendedor.builder().id(1L).nome("Vendedor 1").build())
						.build());
		produtoVendedores
				.add(ProdutoVendedor.builder().id(2L).quantidadeAtual(2).lote(lote1)
						.dataVencimento(LocalDate.now().plusDays(2))
						.produto(Produto.builder().id(1L).nome("Produto Fresco 1").produtoCategoria(
								ProdutoCategoria.builder().categoria(Categoria.FS).build())
								.build())
						.vendedor(Vendedor.builder().id(1L).nome("Vendedor 1").build())
						.build());
		produtoVendedores
				.add(ProdutoVendedor.builder().id(3L).quantidadeAtual(3).lote(lote1)
						.dataVencimento(LocalDate.now().plusDays(3))
						.produto(Produto.builder().id(2L).nome("Produto Fresco 2").produtoCategoria(
								ProdutoCategoria.builder().categoria(Categoria.FS).build())
								.build())
						.vendedor(Vendedor.builder().id(1L).nome("Vendedor 1").build())
						.build());
		produtoVendedores
				.add(ProdutoVendedor.builder().id(4L).quantidadeAtual(4).lote(lote2)
						.dataVencimento(LocalDate.now().plusDays(1))
						.produto(Produto.builder().id(1L).nome("Produto Fresco 1").produtoCategoria(
								ProdutoCategoria.builder().categoria(Categoria.FS).build())
								.build())
						.vendedor(Vendedor.builder().id(2L).nome("Vendedor 2").build())
						.build());
		produtoVendedores
				.add(ProdutoVendedor.builder().id(5L).quantidadeAtual(5).lote(lote2)
						.dataVencimento(LocalDate.now().plusDays(2))
						.produto(Produto.builder().id(1L).nome("Produto Fresco 1").produtoCategoria(
								ProdutoCategoria.builder().categoria(Categoria.FS).build())
								.build())
						.vendedor(Vendedor.builder().id(2L).nome("Vendedor 2").build())
						.build());
		produtoVendedores
				.add(ProdutoVendedor.builder().id(6L).quantidadeAtual(6).lote(lote2)
						.dataVencimento(LocalDate.now().plusDays(3))
						.produto(Produto.builder().id(2L).nome("Produto Fresco 2").produtoCategoria(
								ProdutoCategoria.builder().categoria(Categoria.FS).build())
								.build())
						.vendedor(Vendedor.builder().id(2L).nome("Vendedor 2").build())
						.build());
		produtoVendedores
				.add(ProdutoVendedor.builder().id(7L).quantidadeAtual(7).lote(lote2)
						.dataVencimento(LocalDate.now().plusDays(1))
						.produto(Produto.builder().id(2L).nome("Produto Fresco 2").produtoCategoria(
								ProdutoCategoria.builder().categoria(Categoria.FS).build())
								.build())
						.vendedor(Vendedor.builder().id(2L).nome("Vendedor 2").build())
						.build());
		produtoVendedores
				.add(ProdutoVendedor.builder().id(8L).quantidadeAtual(8).lote(lote1)
						.dataVencimento(LocalDate.now().plusDays(2))
						.produto(Produto.builder().id(3L).nome("Produto Fresco 3").produtoCategoria(
								ProdutoCategoria.builder().categoria(Categoria.FF).build())
								.build())
						.vendedor(Vendedor.builder().id(3L).nome("Vendedor 3").build())
						.build());
		return produtoVendedores;
	}

	@Test
	void shouldReturnNotFoundExceptionIfProductListIsNullInFindAllProducts() {
		List<Produto> produtoEmptyList = new ArrayList<>();
		when(produtoRepository.findAll()).thenReturn(produtoEmptyList);
		assertThrows(NotFoundException.class, () -> produtoService.findAllProdutos());
	}

	@Test
	void shouldReturnCompleteListWhenListExistsInFindAllProducts() {
		when(produtoRepository.findAll()).thenReturn(produtos);
		List<ProdutoResponseDTO> responseDTO = new ArrayList<>();
		responseDTO = produtoService.findAllProdutos();
		assertEquals(3, responseDTO.size());
		assertEquals("Produto Fresco 1", responseDTO.get(0).getNome());
		assertEquals("Produto Congelado 1", responseDTO.get(2).getNome());
		assertEquals(Categoria.FF, responseDTO.get(2).getProdutoCategoria().getCategoria());
	}

	@Test
	void shouldReturnNotFoundExceptionIfProductListIsNullInFindById() {
		assertThrows(NotFoundException.class, () -> produtoService.findById(-1L));
	}

	@Test
	void shouldReturnProdutoWhenExistsInFindById() {
		when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtos.get(0)));
		Produto p = produtoService.findById(1L);
		assertEquals("Produto Fresco 1", p.getNome());
		assertEquals(Categoria.FS, p.getProdutoCategoria().getCategoria());
	}

	@Test
	void shouldSaveProduto() {
		assertDoesNotThrow(
				() -> produtoService.save(produtos.get(0)));
	}

	@Test
	void shouldFindProdutoPorCategoria() {
		List<Produto> armazemListProdutos = new ArrayList<>();
		armazemListProdutos.add(produtos.get(0));
		armazemListProdutos.add(produtos.get(1));
		when(produtoRepository.findProdutoPorCategoria(Categoria.FS.name()))
				.thenReturn(Optional.of(armazemListProdutos));
		List<ProdutoResponseDTO> responseDTO = new ArrayList<>();
		responseDTO = produtoService.findProdutoPorCategoria(Categoria.FS);
		assertEquals(2, responseDTO.size());
		assertEquals("Produto Fresco 1", responseDTO.get(0).getNome());
		assertEquals("Produto Fresco 2", responseDTO.get(1).getNome());
		assertEquals(Categoria.FS, responseDTO.get(0).getProdutoCategoria().getCategoria());
		assertEquals(Categoria.FS, responseDTO.get(1).getProdutoCategoria().getCategoria());
	}

	@Test
	void shouldReturnNotFoundExceptionIfCategoryNonExistentInFindProdutoPorCategoria() {
		List<Produto> armazemListProdutos = new ArrayList<>();
		armazemListProdutos.add(produtos.get(0));
		armazemListProdutos.add(produtos.get(1));
		when(produtoRepository.findProdutoPorCategoria(Categoria.FS.name()))
				.thenReturn(Optional.of(armazemListProdutos));
		assertThrows(NotFoundException.class, () -> produtoService.findProdutoPorCategoria(Categoria.RR));
	}

	@Test
	void shouldFindArmazemPorProduto() {
		List<ProdutoVendedor> listResponseProdutoVendedores1 = new ArrayList<>();
		listResponseProdutoVendedores1.add(produtoVendedores.get(0));
		listResponseProdutoVendedores1.add(produtoVendedores.get(1));

		List<ProdutoVendedor> listResponseProdutoVendedores2 = new ArrayList<>();
		listResponseProdutoVendedores2.add(produtoVendedores.get(3));
		listResponseProdutoVendedores2.add(produtoVendedores.get(4));

		List<ProdutoVendedor> listRepositoryProdutoVendedores = new ArrayList<>();
		listRepositoryProdutoVendedores.add(produtoVendedores.get(0));
		listRepositoryProdutoVendedores.add(produtoVendedores.get(1));
		listRepositoryProdutoVendedores.add(produtoVendedores.get(3));
		listRepositoryProdutoVendedores.add(produtoVendedores.get(4));

		when(produtoVendedorService.findProdutoVendedorByProduto_Id(1L)).thenReturn(listRepositoryProdutoVendedores);
		ArmazemProdutoResponseDTO responseDTO = produtoService.findArmazemPorProduto(1L);
		assertEquals(1L, responseDTO.getProdutoId());
		assertEquals(2, responseDTO.getArmazem().size());
		assertEquals(1L, responseDTO.getArmazem().get(0).getArmazemId());
		assertEquals(listResponseProdutoVendedores1.stream().mapToInt(ProdutoVendedor::getQuantidadeAtual).sum(),
				responseDTO.getArmazem().get(0).getQuantidadeTotal());
		assertEquals(2L, responseDTO.getArmazem().get(1).getArmazemId());
		assertEquals(listResponseProdutoVendedores2.stream().mapToInt(ProdutoVendedor::getQuantidadeAtual).sum(),
				responseDTO.getArmazem().get(1).getQuantidadeTotal());
	}

	@Test
	void shouldListaTodosOsLotes() {
		List<ProdutoVendedor> listRepositoryProdutoVendedores = new ArrayList<>();
		listRepositoryProdutoVendedores.add(produtoVendedores.get(0));
		listRepositoryProdutoVendedores.add(produtoVendedores.get(1));
		listRepositoryProdutoVendedores.add(produtoVendedores.get(3));
		listRepositoryProdutoVendedores.add(produtoVendedores.get(4));

		Map<ProdutoSetorResponse, List<ProdutoVendedorResponse>> responseDTO = new HashMap<>();

		when(produtoVendedorService.findProdutoVendedorByProduto_Id(1L)).thenReturn(listRepositoryProdutoVendedores);

		// Ordened by Lote
		responseDTO = produtoService.listaTodosOsLotes(1L, "L");
		assertTrue(responseDTO.entrySet().iterator().next().getValue().get(0).getLote_id()
				.compareTo(responseDTO.entrySet().iterator().next().getValue().get(1).getLote_id()) >= 0);

		// Ordened by Quantidade Atual
		responseDTO = produtoService.listaTodosOsLotes(1L, "C");
		assertTrue(responseDTO.entrySet().iterator().next().getValue().get(0).getQuantidadeAtual() >= responseDTO
				.entrySet().iterator().next().getValue().get(1).getQuantidadeAtual());

		// Ordened by Data de Vencimento
		responseDTO = produtoService.listaTodosOsLotes(1L, "F");
		assertTrue(responseDTO.entrySet().iterator().next().getValue().get(0).getDataVencimento().compareTo(
				responseDTO.entrySet().iterator().next().getValue().get(1).getDataVencimento()) >= 0);

		// Not ordened
		responseDTO = produtoService.listaTodosOsLotes(1L, "");
		assertEquals(responseDTO.entrySet().iterator().next().getValue().get(0).getProduto_id(),
				listRepositoryProdutoVendedores.get(0).getProduto().getId());
	}

	@Test
	void shouldThrowNotFoundExceptionIfNullListListaTodosOsLotes() {
		when(produtoVendedorService.findProdutoVendedorByProduto_Id(-1L)).thenReturn(anyList());
		assertThrows(NotFoundException.class, () -> produtoService.listaTodosOsLotes(-1L, ""));
	}

	@Test
	void shouldValidateTrueIfExistsProduto() {
		when(produtoRepository.existsById(1L)).thenReturn(true);
		assertEquals(true, produtoService.validateProdutoExists(1L));
	}

	@Test
	void shouldValidateFalseIfDoesntExistsProduto() {
		when(produtoRepository.existsById(1L)).thenReturn(false);
		assertEquals(false, produtoService.validateProdutoExists(1L));
	}
}
