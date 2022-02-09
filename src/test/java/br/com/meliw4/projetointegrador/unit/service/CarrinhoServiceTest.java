package br.com.meliw4.projetointegrador.unit.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.com.meliw4.projetointegrador.entity.Carrinho;
import br.com.meliw4.projetointegrador.entity.Comprador;
import br.com.meliw4.projetointegrador.entity.StatusPedido;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.CarrinhoRepository;
import br.com.meliw4.projetointegrador.response.CarrinhoResponse;
import br.com.meliw4.projetointegrador.service.CarrinhoService;
import br.com.meliw4.projetointegrador.service.StatusPedidoService;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

public class CarrinhoServiceTest {
	private static CarrinhoService carrinhoService;
	private static CarrinhoRepository carrinhoRepository = mock(CarrinhoRepository.class);
	private static StatusPedidoService statusPedidoService = mock(StatusPedidoService.class);

	@BeforeAll
	static void setUp() {
		carrinhoService = new CarrinhoService(carrinhoRepository, statusPedidoService);
	}

	@Test
	void shouldAtualizaCarrinho() {
		StatusPedido statusPedido1 = new StatusPedido(1L, "Status 1");
		StatusPedido statusPedido2 = new StatusPedido(2L, "Status 2");
		Carrinho carrinho = Carrinho.builder().id(1L).data(LocalDate.MIN)
				.comprador(Comprador.builder().id(1L).build())
				.statusPedido(statusPedido1).build();
		when(carrinhoRepository.findById(1L)).thenReturn(Optional.of(carrinho));
		when(statusPedidoService.findStatusPedidoById(1L)).thenReturn(statusPedido1);
		when(statusPedidoService.findStatusPedidoById(2L)).thenReturn(statusPedido2);

		CarrinhoResponse response = carrinhoService.atualizaCarrinho(1L, statusPedido2);
		assertEquals("Status 2", response.getStatusPedido().getStatusCode());
		assertEquals(2L, response.getStatusPedido().getId());
		assertEquals(1L, response.getId());
		assertEquals(LocalDate.MIN, response.getData());
	}

	@Test
	void shouldNotThrowBusinessValidationExceptionWhenAtualizaCarrinho() {
		assertDoesNotThrow(
				() -> carrinhoService.salvaCarrinho(any()));
	}

	@Test
	void shouldThrowBusinessValidationExceptionWhenEmptyCarrinhoInAtualizaCarrinho() {
		assertThrows(BusinessValidationException.class,
				() -> carrinhoService.atualizaCarrinho(-1L, new StatusPedido()));
	}

	@Test
	void shouldNotThrowOrderCheckoutExceptionWhenSalvaCarrinho() {
		assertDoesNotThrow(
				() -> carrinhoService.salvaCarrinho(any()));
	}
}
