package br.com.meliw4.projetointegrador.unit.service;

import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.ArmazemRepository;
import br.com.meliw4.projetointegrador.service.ArmazemService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ArmazemServiceTest {

	private static ArmazemService armazemService;
	private static ArmazemRepository armazemRepository = Mockito.mock(ArmazemRepository.class);

	@BeforeAll
	public static void setUp() {
		armazemService = new ArmazemService(
			armazemRepository
		);
	}

	@Test
	public void shouldThrownInvalidArmazemException() {
		assertThrows(
			BusinessValidationException.class,
			() -> armazemService.findArmazemById(1l)
		);
	}

	@Test
	public void shouldNotThrownInvalidArmazemException() {
		Mockito.when(armazemRepository.findById(2l)).thenReturn(Optional.of((Armazem) Armazem.builder().build()));
		assertDoesNotThrow(
			() -> armazemService.findArmazemById(2l)
		);
	}
}
