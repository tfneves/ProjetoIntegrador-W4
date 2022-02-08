package br.com.meliw4.projetointegrador.unit.service;

import br.com.meliw4.projetointegrador.entity.StatusPedido;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.StatusPedidoRepository;
import br.com.meliw4.projetointegrador.service.StatusPedidoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class StatusPedidoServiceTest {

	private static StatusPedidoService statusPedidoService;
	private static StatusPedidoRepository statusPedidoRepository = Mockito.mock(StatusPedidoRepository.class);

	@BeforeAll
	public static void setUp() {
		statusPedidoService = new StatusPedidoService(
			statusPedidoRepository
		);
	}

	@Test
	public void shouldThrowInvalidStatusPedidoExcepetion() {
		assertThrows(
			BusinessValidationException.class,
			() -> statusPedidoService.findStatusPedidoById(1l)
		);
	}

	@Test
	public void shouldNotThrowInvalidStatusPedidoExcepetion() {
		Mockito.when(statusPedidoRepository.findById(1l)).thenReturn(
			Optional.of((StatusPedido) StatusPedido.builder().build())
		);
		assertDoesNotThrow(
			() -> statusPedidoService.findStatusPedidoById(1l)
		);
	}

	@Test
	public void shouldThrowInvalidStatusCodeNameExcepetion() {
		assertThrows(
			BusinessValidationException.class,
			() -> statusPedidoService.findStatusCodeWithName("Teste")
		);
	}

	@Test
	public void shouldNotThrowInvalidStatusCodeNameExcepetion() {
		Mockito.when(statusPedidoRepository.findByStatusCode("Finalizado")).thenReturn(
			StatusPedido.builder()
				.statusCode("Finalizado")
				.build()
		);
		assertDoesNotThrow(
			() -> statusPedidoService.findStatusCodeWithName("Finalizado")
		);
	}
}
