package br.com.meliw4.projetointegrador.unit.service;

import br.com.meliw4.projetointegrador.dto.ProdutoDTO;
import br.com.meliw4.projetointegrador.dto.ProdutoUpdateDTO;
import br.com.meliw4.projetointegrador.entity.*;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.LoteRepository;
import br.com.meliw4.projetointegrador.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LoteServiceTest {


	private static LoteService loteService;
	private static LoteRepository loteRepository = Mockito.mock(LoteRepository.class);
	private static ProdutoService produtoService = Mockito.mock(ProdutoService.class);
	private static ProdutoVendedorService produtoVendedorService = Mockito.mock(ProdutoVendedorService.class);
	private static List<ProdutoDTO> produtosDTO;
	private static List<ProdutoUpdateDTO> produtosUpdateDTO;

	@BeforeAll
	public static void setUp() {
		loteService = new LoteService(
			loteRepository,
			Mockito.mock(ArmazemService.class),
			Mockito.mock(VendedorService.class),
			Mockito.mock(SetorService.class),
			Mockito.mock(RepresentanteService.class),
			produtoService,
			Mockito.mock(RegistroLoteService.class),
			produtoVendedorService
		);
		produtosDTO = makeProdutosDTOList();
		produtosUpdateDTO = makeProdutosUpdateDTOList();
	}

	private static List<ProdutoDTO> makeProdutosDTOList() {
		List<ProdutoDTO> produtos = new ArrayList<>();
		produtos.add(
			ProdutoDTO.builder()
				.id(10l)
				.nome("Manzana")
				.dataVencimento(LocalDate.of(2100, Month.DECEMBER, 12))
				.volume(2.0)
				.quantidadeInicial(2)
				.quantidadeAtual(2)
				.dataManufatura(LocalDate.of(2022, Month.FEBRUARY, 02))
				.produtoCategoria(
					ProdutoCategoria.builder().categoria(Categoria.FS).temperaturaMinima(20f).build()
				)
				.temperaturaAtual(23f)
				.preco(new BigDecimal("2"))
				.build()
		);
		produtos.add(
			ProdutoDTO.builder()
				.id(20l)
				.nome("Naranja")
				.dataVencimento(LocalDate.of(2100, Month.DECEMBER, 12))
				.volume(1.0)
				.quantidadeInicial(3)
				.quantidadeAtual(3)
				.dataManufatura(LocalDate.of(2022, Month.FEBRUARY, 02))
				.produtoCategoria(
					ProdutoCategoria.builder().categoria(Categoria.FS).temperaturaMinima(20f).build()
				)
				.temperaturaAtual(23f)
				.preco(new BigDecimal("3"))
				.build()
		);
		return produtos;
	}

	private static List<ProdutoUpdateDTO> makeProdutosUpdateDTOList() {
		List<ProdutoUpdateDTO> produtos = new ArrayList<>();
		produtos.add(
			ProdutoUpdateDTO.builder()
				.id(1l)
				.quantidadeRetira(1)
				.build()
		);
		produtos.add(
			ProdutoUpdateDTO.builder()
				.id(2l)
				.quantidadeRetira(1)
				.build());
		return produtos;
	}

	@Test
	public void shouldThrowInvaliLoteException() {
		assertThrows(
			BusinessValidationException.class,
			() -> loteService.validateLoteExists(0l)
		);
	}

	@Test
	public void shouldFindLote() {
		Mockito.when(loteRepository.existsById(1l)).thenReturn(true);
		assertDoesNotThrow(
			() -> loteService.validateLoteExists(1l)
		);
	}

	@Test
	public void shouldSaveLote() {
		assertDoesNotThrow(
			() -> loteService.save(Lote.builder().build())
		);
	}

	@Test
	public void shouldSaveRegister() {
		assertDoesNotThrow(
			() -> loteService.createRegister(
				Lote.builder().build(),
				Representante.builder().build(),
				Vendedor.builder().build()
			)
		);
	}

	@Test
	public void shouldThrowInvalidPrecoZeroException() {
		assertThrows(
			BusinessValidationException.class,
			() -> loteService.validatePreco(BigDecimal.ZERO)
		);
	}

	@Test
	public void shouldThrowInvalidPrecoNegativeException() {
		assertThrows(
			BusinessValidationException.class,
			() -> loteService.validatePreco(BigDecimal.valueOf(-1))
		);
	}

	@Test
	public void shouldNotThrowInvalidPrecoException() {
		assertDoesNotThrow(
			() -> loteService.validatePreco(BigDecimal.valueOf(1))
		);
	}

	@Test
	public void shouldThrowInvalidCategoriaSetorException() {
		Setor setor = Setor.builder().categoria(Categoria.FS).build();
		produtosDTO.get(0).setProdutoCategoria(
			ProdutoCategoria.builder().categoria(Categoria.FF).temperaturaMinima(-5f).build()
		);
		assertThrows(
			BusinessValidationException.class,
			() -> loteService.validateProdutosDTOCategoria(setor, produtosDTO)
		);
	}

	@Test
	public void shouldNotThrowInvalidCategoriaSetorException() {
		Setor setor = Setor.builder().categoria(Categoria.FS).build();
		produtosDTO.get(0).setProdutoCategoria(
			ProdutoCategoria.builder().categoria(Categoria.FS).temperaturaMinima(20f).build()
		);
		assertDoesNotThrow(
			() -> loteService.validateProdutosDTOCategoria(setor, produtosDTO)
		);
	}

	@Test
	public void shouldReturnExistingProducts() {
		Mockito.when(produtoService.validateProdutoExists(10l)).thenReturn(true);
		Mockito.when(produtoService.validateProdutoExists(20l)).thenReturn(true);
		Mockito.when(produtoService.getProdutoById(10l)).thenReturn(Produto.builder().id(10).build());
		Mockito.when(produtoService.getProdutoById(20l)).thenReturn(Produto.builder().id(20).build());
		List<Produto> produtos = loteService.checkProdutosDTO(produtosDTO);
		assertEquals(produtos.get(0).getId(), 10l);
		assertEquals(produtos.get(1).getId(), 20l);
	}

	@Test
	public void shouldRegisterNewProducts() {
		Mockito.when(produtoService.validateProdutoExists(10l)).thenReturn(false);
		Mockito.when(produtoService.validateProdutoExists(20l)).thenReturn(false);
		List<Produto> produtos = loteService.checkProdutosDTO(produtosDTO);
		assertEquals(0l, produtos.get(0).getId());
		assertEquals(0l, produtos.get(1).getId());
	}

	@Test
	public void shouldCalculateProdutosDTOTotalVolume() {
		assertEquals(loteService.calculateProdutosDTOTotalVolume(produtosDTO), 7.0);
	}

	@Test
	public void shouldSaveAnuncios() {
		Mockito.when(produtoService.getProdutoById(10l)).thenReturn(Produto.builder().build());
		Mockito.when(produtoService.getProdutoById(20l)).thenReturn(Produto.builder().build());
		assertDoesNotThrow(
			() -> loteService.saveAnuncios(Lote.builder().build(), produtosDTO, Vendedor.builder().build())
		);
	}

	@Test
	public void shouldThrowInvalidProdutoUpdateException() {
		assertThrows(
			BusinessValidationException.class,
			() -> loteService.validateProdutosUpdate(produtosUpdateDTO)
		);
	}

	@Test
	public void shouldNotThrowInvalidProdutoUpdateException() {
		Mockito.when(produtoService.validateProdutoExists(1l)).thenReturn(true);
		Mockito.when(produtoService.validateProdutoExists(2l)).thenReturn(true);
		assertDoesNotThrow(
			() -> loteService.validateProdutosUpdate(produtosUpdateDTO)
		);
	}

	@Test
	void shouldThrowInvalidProdutoVendedorUpdateException() {
		assertThrows(
			BusinessValidationException.class,
			() -> loteService.updateLoteProdutos(1l, produtosUpdateDTO)
		);
	}

	@Test
	void shouldThrowInvalidProdutoQuantityUpdateException() {
		Mockito.when(
			produtoVendedorService.findByLoteIdAndProdutoId(1l, 1l)
		).thenReturn(ProdutoVendedor.builder().quantidadeAtual(0).build());
		Mockito.when(
			produtoVendedorService.findByLoteIdAndProdutoId(1l, 2l)
		).thenReturn(ProdutoVendedor.builder().quantidadeAtual(0).build());
		assertThrows(
			BusinessValidationException.class,
			() -> loteService.updateLoteProdutos(1l, produtosUpdateDTO)
		);
	}

	@Test
	void shouldReturnUpdatedProdutosDTOList() {
		Mockito.when(
			produtoVendedorService.findByLoteIdAndProdutoId(1l, 1l)
		).thenReturn(
			ProdutoVendedor.builder()
				.quantidadeAtual(1)
				.produto(
					Produto.builder()
						.id(1l)
						.nome("naranja")
						.volume(1.0)
						.produtoCategoria(
							ProdutoCategoria.builder()
								.categoria(Categoria.FS)
								.temperaturaMinima(18f)
								.build()
						)
						.build()
				)
				.build()
		);
		Mockito.when(
			produtoVendedorService.findByLoteIdAndProdutoId(1l, 2l)
		).thenReturn(
			ProdutoVendedor.builder()
				.quantidadeAtual(1)
				.produto(
					Produto.builder()
						.id(2l)
						.nome("manzana")
						.volume(1.0)
						.produtoCategoria(
							ProdutoCategoria.builder()
								.categoria(Categoria.FS)
								.temperaturaMinima(18f)
								.build()
						)
						.build()
				)
				.build()
		);
		List<ProdutoDTO> produtos = loteService.updateLoteProdutos(1l, produtosUpdateDTO);
		assertEquals(produtos.get(0).getQuantidadeAtual(), 0);
		assertEquals(produtos.get(1).getQuantidadeAtual(), 0);
	}
}
