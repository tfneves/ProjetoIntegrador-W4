package br.com.meliw4.projetointegrador.unit.service;

import br.com.meliw4.projetointegrador.dto.ProdutoDTO;
import br.com.meliw4.projetointegrador.dto.ProdutoUpdateDTO;
import br.com.meliw4.projetointegrador.entity.*;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import br.com.meliw4.projetointegrador.entity.enumeration.Ordenamento;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.LoteRepository;
import br.com.meliw4.projetointegrador.response.LoteProdutosVencimentoResponse;
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
	private static ArmazemService armazemService = Mockito.mock(ArmazemService.class);
	private static VendedorService vendedorService = Mockito.mock(VendedorService.class);
	private static SetorService setorService = Mockito.mock(SetorService.class);
	private static RepresentanteService representanteService = Mockito.mock(RepresentanteService.class);
	private static RegistroLoteService registroLoteService = Mockito.mock(RegistroLoteService.class);
	private static ProdutoService produtoService = Mockito.mock(ProdutoService.class);
	private static ProdutoVendedorService produtoVendedorService = Mockito.mock(ProdutoVendedorService.class);
	private static List<ProdutoDTO> produtosDTO;
	private static List<ProdutoUpdateDTO> produtosUpdateDTO;

	@BeforeAll
	public static void setUp() {
		loteService = new LoteService(
				loteRepository,
				armazemService,
				vendedorService,
				setorService,
				representanteService,
				produtoService,
				registroLoteService,
				produtoVendedorService);
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
								ProdutoCategoria.builder().categoria(Categoria.FS).temperaturaMinima(20f).build())
						.temperaturaAtual(23f)
						.preco(new BigDecimal("2"))
						.build());
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
								ProdutoCategoria.builder().categoria(Categoria.FS).temperaturaMinima(20f).build())
						.temperaturaAtual(23f)
						.preco(new BigDecimal("3"))
						.build());
		return produtos;
	}

	private static List<ProdutoUpdateDTO> makeProdutosUpdateDTOList() {
		List<ProdutoUpdateDTO> produtos = new ArrayList<>();
		produtos.add(
				ProdutoUpdateDTO.builder()
						.id(1l)
						.quantidadeRetira(1)
						.build());
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
				() -> loteService.validateLoteExists(0l));
	}

	@Test
	public void shouldFindLote() {
		Mockito.when(loteRepository.existsById(1l)).thenReturn(true);
		assertDoesNotThrow(
				() -> loteService.validateLoteExists(1l));
	}

	@Test
	public void shouldSaveLote() {
		assertDoesNotThrow(
				() -> loteService.save(Lote.builder().build()));
	}

	@Test
	public void shouldSaveRegister() {
		assertDoesNotThrow(
				() -> loteService.createRegister(
						Lote.builder().build(),
						Representante.builder().build(),
						Vendedor.builder().build()));
	}

	@Test
	public void shouldThrowInvalidPrecoZeroException() {
		assertThrows(
				BusinessValidationException.class,
				() -> loteService.validatePreco(BigDecimal.ZERO));
	}

	@Test
	public void shouldThrowInvalidPrecoNegativeException() {
		assertThrows(
				BusinessValidationException.class,
				() -> loteService.validatePreco(BigDecimal.valueOf(-1)));
	}

	@Test
	public void shouldNotThrowInvalidPrecoException() {
		assertDoesNotThrow(
				() -> loteService.validatePreco(BigDecimal.valueOf(1)));
	}

	@Test
	public void shouldThrowInvalidCategoriaSetorException() {
		Setor setor = Setor.builder().categoria(Categoria.FS).build();
		produtosDTO.get(0).setProdutoCategoria(
				ProdutoCategoria.builder().categoria(Categoria.FF).temperaturaMinima(-5f).build());
		assertThrows(
				BusinessValidationException.class,
				() -> loteService.validateProdutosDTOCategoria(setor, produtosDTO));
	}

	@Test
	public void shouldNotThrowInvalidCategoriaSetorException() {
		Setor setor = Setor.builder().categoria(Categoria.FS).build();
		produtosDTO.get(0).setProdutoCategoria(
				ProdutoCategoria.builder().categoria(Categoria.FS).temperaturaMinima(20f).build());
		assertDoesNotThrow(
				() -> loteService.validateProdutosDTOCategoria(setor, produtosDTO));
	}

	@Test
	public void shouldReturnExistingProducts() {
		Mockito.when(produtoService.validateProdutoExists(10l)).thenReturn(true);
		Mockito.when(produtoService.validateProdutoExists(20l)).thenReturn(true);
		Mockito.when(produtoService.findById(10l)).thenReturn(Produto.builder().id(10).build());
		Mockito.when(produtoService.findById(20l)).thenReturn(Produto.builder().id(20).build());
		List<Produto> produtos = loteService.checkProdutosDTO(produtosDTO);
		assertEquals(produtos.get(0).getId(), 10l);
		assertEquals(produtos.get(1).getId(), 20l);
	}

	@Test
	public void shouldRegisterNewProducts() {
		Mockito.when(produtoService.validateProdutoExists(10l)).thenReturn(true);
		Mockito.when(produtoService.validateProdutoExists(20l)).thenReturn(true);
		List<Produto> produtos = loteService.checkProdutosDTO(produtosDTO);
		assertEquals(10l, produtos.get(0).getId());
		assertEquals(20l, produtos.get(1).getId());
	}

	@Test
	public void shouldCalculateProdutosDTOTotalVolume() {
		assertEquals(loteService.calculateProdutosDTOTotalVolume(produtosDTO), 7.0);
	}

	@Test
	public void shouldSaveAnuncios() {
		Mockito.when(produtoService.findById(10l)).thenReturn(Produto.builder().build());
		Mockito.when(produtoService.findById(20l)).thenReturn(Produto.builder().build());
		assertDoesNotThrow(
				() -> loteService.saveAnuncios(Lote.builder().build(), produtosDTO, Vendedor.builder().build()));
	}

	@Test
	public void shouldThrowInvalidProdutoUpdateException() {
		assertThrows(
				BusinessValidationException.class,
				() -> loteService.validateProdutosUpdate(produtosUpdateDTO));
	}

	@Test
	public void shouldNotThrowInvalidProdutoUpdateException() {
		Mockito.when(produtoService.validateProdutoExists(1l)).thenReturn(true);
		Mockito.when(produtoService.validateProdutoExists(2l)).thenReturn(true);
		assertDoesNotThrow(
				() -> loteService.validateProdutosUpdate(produtosUpdateDTO));
	}

	@Test
	void shouldThrowInvalidProdutoVendedorUpdateException() {
		BusinessValidationException ex = assertThrows(
				BusinessValidationException.class,
				() -> loteService.updateLoteProdutos(1l, produtosUpdateDTO));
		assertEquals(ex.getMessage(), "Produto não cadastrado pelo vendedor no lote solicitado.");
	}

	@Test
	void shouldThrowInvalidProdutoQuantityUpdateException() {
		Mockito.when(
				produtoVendedorService.findByLoteIdAndProdutoId(1l, 1l))
				.thenReturn(ProdutoVendedor.builder().quantidadeAtual(0).build());
		Mockito.when(
				produtoVendedorService.findByLoteIdAndProdutoId(1l, 2l))
				.thenReturn(ProdutoVendedor.builder().quantidadeAtual(0).build());
		BusinessValidationException ex = assertThrows(
				BusinessValidationException.class,
				() -> loteService.updateLoteProdutos(1l, produtosUpdateDTO));
		assertEquals(ex.getMessage(), "A quantidade a retirar não deve exceder a quantidade atual de um produto.");
	}

	@Test
	void shouldReturnUpdatedProdutosDTOList() {
		Mockito.when(
				produtoVendedorService.findByLoteIdAndProdutoId(1l, 1l)).thenReturn(
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
																.build())
												.build())
								.build());
		Mockito.when(
				produtoVendedorService.findByLoteIdAndProdutoId(1l, 2l)).thenReturn(
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
																.build())
												.build())
								.build());
		List<ProdutoDTO> produtos = loteService.updateLoteProdutos(1l, produtosUpdateDTO);
		assertEquals(produtos.get(0).getQuantidadeAtual(), 0);
		assertEquals(produtos.get(1).getQuantidadeAtual(), 0);
	}

	@Test
	public void shouldReturnLoteProdutosVencimentoResponse() {
		LocalDate today = LocalDate.now();
		LocalDate limitDate = today.plusDays(30);
		List<ProdutoVendedor> anuncios = new ArrayList<>();
		anuncios.add(
				ProdutoVendedor.builder()
						.id(1l)
						.produto(
								Produto.builder()
										.id(1l)
										.produtoCategoria(
												ProdutoCategoria.builder()
														.categoria(Categoria.FS)
														.build())
										.build())
						.dataVencimento(today.plusDays(15))
						.build());
		anuncios.add(
				ProdutoVendedor.builder()
						.id(2l)
						.produto(
								Produto.builder()
										.id(2l)
										.produtoCategoria(
												ProdutoCategoria.builder()
														.categoria(Categoria.FS)
														.build())
										.build())
						.dataVencimento(limitDate)
						.build());
		anuncios.add(
				ProdutoVendedor.builder()
						.id(3l)
						.produto(
								Produto.builder()
										.id(3l)
										.produtoCategoria(
												ProdutoCategoria.builder()
														.categoria(Categoria.FS)
														.build())
										.build())
						.dataVencimento(today)
						.build());
		anuncios.add(
				ProdutoVendedor.builder()
						.id(4l)
						.produto(
								Produto.builder()
										.id(4l)
										.produtoCategoria(
												ProdutoCategoria.builder()
														.categoria(Categoria.FS)
														.build())
										.build())
						.dataVencimento(today.plusDays(40))
						.build());
		Lote lote = Lote.builder()
				.id(1l)
				.setor(
						Setor.builder()
								.id(1l)
								.build())
				.produtoVendedores(anuncios)
				.build();
		List<LoteProdutosVencimentoResponse> responseList = new ArrayList<>();
		loteService.getFilteredProdutosByLote(lote, today, limitDate, responseList);
		assertEquals(responseList.size(), 3);
		assertEquals(responseList.get(0).getDataVencimento(), today.plusDays(15));
		assertEquals(responseList.get(1).getDataVencimento(), limitDate);
		assertEquals(responseList.get(2).getDataVencimento(), today);
	}

	@Test
	public void shouldReturnFilteredListByCategory() {
		List<Setor> setores = new ArrayList<>();
		setores.add(
				Setor.builder()
						.categoria(Categoria.FF)
						.build());
		setores.add(
				Setor.builder()
						.categoria(Categoria.FS)
						.build());
		setores.add(
				Setor.builder()
						.categoria(Categoria.RR)
						.build());
		List<Setor> setoresResponse = loteService.filterByCategory(setores, Categoria.FS);
		assertEquals(setoresResponse.size(), 1);
		assertEquals(setoresResponse.get(0).getCategoria(), Categoria.FS);
	}

	@Test
	public void shouldReturnListOrderedByDate() {
		LocalDate today = LocalDate.now();
		List<LoteProdutosVencimentoResponse> responseList = new ArrayList<>();
		responseList.add(
				LoteProdutosVencimentoResponse.builder()
						.dataVencimento(today.plusDays(15))
						.build());
		responseList.add(
				LoteProdutosVencimentoResponse.builder()
						.dataVencimento(today.plusDays(30))
						.build());
		responseList.add(
				LoteProdutosVencimentoResponse.builder()
						.dataVencimento(today.plusDays(20))
						.build());
		responseList = loteService.orderByDate(responseList, Ordenamento.asc);
		responseList = loteService.orderByDate(responseList, Ordenamento.asc);
		assertEquals(responseList.get(0).getDataVencimento(), today.plusDays(15));
		assertEquals(responseList.get(2).getDataVencimento(), today.plusDays(30));
		responseList = loteService.orderByDate(responseList, Ordenamento.desc);
		assertEquals(responseList.get(0).getDataVencimento(), today.plusDays(30));
		assertEquals(responseList.get(2).getDataVencimento(), today.plusDays(15));
	}

	@Test
	public void shouldReturnListFilteredByDueDate() {
		LocalDate today = LocalDate.now();
		List<LoteProdutosVencimentoResponse> responseList = new ArrayList<>();
		responseList.add(
				LoteProdutosVencimentoResponse.builder()
						.dataVencimento(today.plusDays(30))
						.build());
		responseList.add(
				LoteProdutosVencimentoResponse.builder()
						.dataVencimento(today)
						.build());
		responseList.add(
				LoteProdutosVencimentoResponse.builder()
						.dataVencimento(today.plusDays(20))
						.build());
		responseList = loteService.filterDueDateUntilDate(responseList, 25);
		assertEquals(responseList.size(), 2);
		assertEquals(responseList.get(0).getDataVencimento(), today);
		assertEquals(responseList.get(1).getDataVencimento(), today.plusDays(20));
	}

}
