package br.com.meliw4.projetointegrador.unit.service;

import br.com.meliw4.projetointegrador.dto.CarrinhoDTO;
import br.com.meliw4.projetointegrador.dto.ProdutoCarrinhoDTO;
import br.com.meliw4.projetointegrador.entity.*;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.exception.OrderCheckoutException;
import br.com.meliw4.projetointegrador.repository.CarrinhoRepository;
import br.com.meliw4.projetointegrador.repository.ProdutoCarrinhoRepository;
import br.com.meliw4.projetointegrador.response.PedidoResponse;
import br.com.meliw4.projetointegrador.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoServiceTest {

	private static PedidoService pedidoService;
	private static List<ProdutoCarrinhoDTO> previousStateProdutoVendedor = new ArrayList<>();
	private static CarrinhoService carrinhoService = Mockito.mock(CarrinhoService.class);
	private static ProdutoCarrinhoService produtoCarrinhoService = Mockito.mock(ProdutoCarrinhoService.class);
	private static StatusPedidoService statusPedidoService = Mockito.mock(StatusPedidoService.class);
	private static CompradorService compradorService = Mockito.mock(CompradorService.class);
	private static ProdutoServiceOrder produtoServiceOrder = Mockito.mock(ProdutoServiceOrder.class);
	private static ProdutoVendedorService produtoVendedorService = Mockito.mock(ProdutoVendedorService.class);
	private static CarrinhoRepository carrinhoRepository = Mockito.mock(CarrinhoRepository.class);
	private static ProdutoCarrinhoRepository produtoCarrinhoRepository = Mockito.mock(ProdutoCarrinhoRepository.class);

	@BeforeAll
	public static void setUp() {
		pedidoService = new PedidoService(
			carrinhoService, produtoCarrinhoService, statusPedidoService, compradorService, produtoServiceOrder,
			produtoVendedorService, carrinhoRepository, produtoCarrinhoRepository
		);
	}

	@Test
	public void shouldClearPreviousStateProdutoVendedorList() {
		PedidoService.previousStateProdutoVendedor.add(ProdutoCarrinhoDTO.builder().build());
		PedidoService.clearQueueProdutoVendedor();
		assertEquals(PedidoService.previousStateProdutoVendedor.size(), 0);
	}

	@Test
	public void shouldReturnPedidoResponse() {
		List<ProdutoCarrinho> produtosCarrinho = new ArrayList<>();
		produtosCarrinho.add(
			ProdutoCarrinho.builder()
				.id(1l)
				.produto(
					ProdutoVendedor.builder()
						.id(1l)
						.produto(
							Produto.builder()
								.id(1l)
								.build()
						)
						.vendedor(
							Vendedor.builder()
								.id(1l)
								.build()
						)
						.build()
				)
				.quantidade(10)
				.build()
		);
		produtosCarrinho.add(
			ProdutoCarrinho.builder()
				.id(2l)
				.produto(
					ProdutoVendedor.builder()
						.id(2l)
						.produto(
							Produto.builder()
								.id(2l)
								.build()
						)
						.vendedor(
							Vendedor.builder()
								.id(2l)
								.build()
						)
						.build()
				)
				.quantidade(20)
				.build()
		);
		Mockito.when(carrinhoRepository.existsById(1l)).thenReturn(true);
		Mockito.when(carrinhoRepository.findById(1l)).thenReturn(
			Optional.of((Carrinho) Carrinho.builder()
				.statusPedido(StatusPedido.builder()
					.id(1l)
					.statusCode("Finalizado")
					.build())
				.build())
		);
		Mockito.when(produtoCarrinhoRepository.findByCarrinho_Id(1l)).thenReturn(
			produtosCarrinho
		);
		PedidoResponse pedidoResponse = pedidoService.getPedido(1l);
		assertEquals(pedidoResponse.getProdutosPedido().size(), 2);
	}

	@Test
	public void shouldThrowInalidPedidoException() {
		BusinessValidationException ex =
			assertThrows(
				BusinessValidationException.class,
				() -> pedidoService.validatePedido(1l)
			);
		assertEquals(ex.getMessage(), "O pedido não existe.");
	}

	@Test
	public void shouldThrowInalidPedidoStatusException() {
		Mockito.when(carrinhoRepository.existsById(2l)).thenReturn(true);
		Mockito.when(carrinhoRepository.findById(2l)).thenReturn(
			Optional.of((Carrinho) Carrinho.builder()
				.statusPedido(StatusPedido.builder()
					.id(1l)
					.statusCode("Cancelado")
					.build())
				.build())
		);
		BusinessValidationException ex =
			assertThrows(
				BusinessValidationException.class,
				() -> pedidoService.validatePedido(2l)
			);
		assertEquals(ex.getMessage(), "O pedido não foi finalizado.");
	}

	@Test
	public void shouldReturnCarrinhoTotalValue() {
		List<ProdutoCarrinho> produtosCarrinho = new ArrayList<>();
		produtosCarrinho.add(
			ProdutoCarrinho.builder()
				.produto(
					ProdutoVendedor.builder()
						.id(1l)
						.build()
				).quantidade(10)
				.build()
		);
		produtosCarrinho.add(
			ProdutoCarrinho.builder()
				.produto(
					ProdutoVendedor.builder()
						.id(2l)
						.build()
				)
				.quantidade(5)
				.build()
		);
		Mockito.when(produtoCarrinhoService.buscaProdutosCarrinhoById(1l)).thenReturn(produtosCarrinho);
		Mockito.when(produtoVendedorService.getProdutoById(1l)).thenReturn(
			ProdutoVendedor.builder()
				.preco(new BigDecimal("10.5"))
				.build()
		);
		Mockito.when(produtoVendedorService.getProdutoById(2l)).thenReturn(
			ProdutoVendedor.builder()
				.preco(new BigDecimal("5.5"))
				.build()
		);
		BigDecimal valorTotal = pedidoService.calculaValorTotalCarrinho(1l);
		assertEquals(new BigDecimal("132.5"), pedidoService.calculaValorTotalCarrinho(1l));
	}

	@Test
	public void shouldThrowProdutoCatchException() {
		ProdutoVendedor produtoVendedor = ProdutoVendedor.builder()
			.dataVencimento(null)
			.id(1l)
			.build();
		OrderCheckoutException ex =
			assertThrows(
				OrderCheckoutException.class,
				() -> pedidoService.verificaValidadeProduto(produtoVendedor)
			);
		assertEquals(ex.getMessage(), "Houve um erro ao pegar a validade do produto de ID 1");
	}

	@Test
	public void shouldThrowProdutoValidadeException() {
		ProdutoVendedor produtoVendedor = ProdutoVendedor.builder()
			.dataVencimento(LocalDate.now())
			.id(1l)
			.build();
		OrderCheckoutException ex =
			assertThrows(
				OrderCheckoutException.class,
				() -> pedidoService.verificaValidadeProduto(produtoVendedor)
			);
		assertEquals(ex.getMessage(), "O produto de ID 1 possui um prazo de validade inferior a 21 dias");
	}

	@Test
	public void shouldValidateProdutoValidade() {
		ProdutoVendedor produtoVendedor = ProdutoVendedor.builder()
			.dataVencimento(LocalDate.now().plusDays(25))
			.id(1l)
			.build();
		assertTrue(pedidoService.verificaValidadeProduto(produtoVendedor));
	}

	@Test
	public void shouldThrowInvalidQuantityException() {
		ProdutoVendedor produtoVendedor = ProdutoVendedor.builder()
			.quantidadeAtual(10)
			.id(1l)
			.build();
		ProdutoCarrinhoDTO produtoCarrinhoDTO = ProdutoCarrinhoDTO.builder()
			.quantidade(0)
			.build();
		OrderCheckoutException ex =
			assertThrows(
				OrderCheckoutException.class,
				() -> pedidoService.verificaDisponibilidadeEstoque(produtoVendedor, produtoCarrinhoDTO)
			);
		assertEquals(ex.getMessage(), "A quantidade solicitada do produto de ID 1 deve ser igual ou superior a 1 " +
			"volume");
	}

	@Test
	public void shouldThrowEmptyStockException() {
		ProdutoVendedor produtoVendedor = ProdutoVendedor.builder()
			.quantidadeAtual(0)
			.id(1l)
			.build();
		ProdutoCarrinhoDTO produtoCarrinhoDTO = ProdutoCarrinhoDTO.builder()
			.quantidade(5)
			.build();
		OrderCheckoutException ex =
			assertThrows(
				OrderCheckoutException.class,
				() -> pedidoService.verificaDisponibilidadeEstoque(produtoVendedor, produtoCarrinhoDTO)
			);
		assertEquals(ex.getMessage(), "O produto de ID 1 não se encontra disponível em estoque no momento");
	}

	@Test
	public void shouldThrowInvalidStockQuantityException() {
		ProdutoVendedor produtoVendedor = ProdutoVendedor.builder()
			.quantidadeAtual(3)
			.id(1l)
			.build();
		ProdutoCarrinhoDTO produtoCarrinhoDTO = ProdutoCarrinhoDTO.builder()
			.quantidade(5)
			.build();
		OrderCheckoutException ex =
			assertThrows(
				OrderCheckoutException.class,
				() -> pedidoService.verificaDisponibilidadeEstoque(produtoVendedor, produtoCarrinhoDTO)
			);
		assertEquals(ex.getMessage(), "A quantidade solicitada do produto de ID 1 é superior a existente em estoque." +
			" " +
			"\n Quantidade disponivel: 3");
	}

	@Test
	public void shouldValidateStock() {
		ProdutoVendedor produtoVendedor = ProdutoVendedor.builder()
			.quantidadeAtual(10)
			.id(1l)
			.build();
		ProdutoCarrinhoDTO produtoCarrinhoDTO = ProdutoCarrinhoDTO.builder()
			.quantidade(5)
			.build();
		assertTrue(pedidoService.verificaDisponibilidadeEstoque(produtoVendedor, produtoCarrinhoDTO));
	}

	@Test
	public void shouldThrowStockCatchException() {
		ProdutoCarrinhoDTO produtoCarrinhoDTO = ProdutoCarrinhoDTO.builder().build();
		ProdutoVendedor produtoVendedor = ProdutoVendedor.builder()
			.id(1l)
			.build();
		OrderCheckoutException ex =
			assertThrows(
				OrderCheckoutException.class,
				() -> pedidoService.atualizaQuantidadeEstoque(produtoVendedor, produtoCarrinhoDTO)
			);
		assertEquals(ex.getMessage(), "Houve um erro ao pegar a quantidade disponível em estoque do produto de ID 1");
	}

	@Test
	public void shouldThrowQuantityCatchException() {
		ProdutoCarrinhoDTO produtoCarrinhoDTO = ProdutoCarrinhoDTO.builder()
			.anuncioId(1l)
			.build();
		ProdutoVendedor produtoVendedor = ProdutoVendedor.builder()
			.id(1l)
			.quantidadeAtual(1)
			.build();
		OrderCheckoutException ex =
			assertThrows(
				OrderCheckoutException.class,
				() -> pedidoService.atualizaQuantidadeEstoque(produtoVendedor, produtoCarrinhoDTO)
			);
		assertEquals(ex.getMessage(), "Houve um erro ao pegar a quantidade solicitada na compra do produto de ID 1");
	}

	@Test
	public void shouldUpdateStockQuantity() {
		ProdutoCarrinhoDTO produtoCarrinhoDTO = ProdutoCarrinhoDTO.builder()
			.anuncioId(1l)
			.quantidade(1)
			.build();
		ProdutoVendedor produtoVendedor = ProdutoVendedor.builder()
			.id(1l)
			.quantidadeAtual(1)
			.build();
		assertTrue(pedidoService.atualizaQuantidadeEstoque(produtoVendedor, produtoCarrinhoDTO));
	}

	@Test
	public void shouldSavePedido() {
		List<ProdutoCarrinhoDTO> produtosCarrinhoDTO = new ArrayList<>();
		produtosCarrinhoDTO.add(
			ProdutoCarrinhoDTO.builder()
				.anuncioId(1l)
				.quantidade(3)
				.build()
		);
		produtosCarrinhoDTO.add(
			ProdutoCarrinhoDTO.builder()
				.anuncioId(2l)
				.quantidade(3)
				.build()
		);
		CarrinhoDTO dto =CarrinhoDTO.builder()
			.idComprador(1l)
			.produtos(produtosCarrinhoDTO)
			.build();
		Mockito.when(statusPedidoService.findStatusCodeWithName("FINALIZADO")).thenReturn(
			StatusPedido.builder()
				.statusCode("FINALIZADO")
				.build()
		);
		Mockito.when((compradorService.findCompradorById(1l))).thenReturn(
			Comprador.builder()
				.build()
		);
		Mockito.when(produtoVendedorService.getProdutoById(1l)).thenReturn(
			ProdutoVendedor.builder()
				.dataVencimento(LocalDate.now().plusDays(30))
				.quantidadeAtual(5)
				.build()
		);
		Mockito.when(produtoVendedorService.getProdutoById(2l)).thenReturn(
			ProdutoVendedor.builder()
				.dataVencimento(LocalDate.now().plusDays(30))
				.quantidadeAtual(5)
				.build()
		);
		assertDoesNotThrow(
			() -> pedidoService.salvaPedido(dto)
		);
	}
}
