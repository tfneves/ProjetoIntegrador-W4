package br.com.meliw4.projetointegrador.unit.service;

import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.SetorRepository;
import br.com.meliw4.projetointegrador.service.ArmazemService;
import br.com.meliw4.projetointegrador.service.SetorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SetorServiceTest {

	private static SetorService setorService;
	private static SetorRepository setorRepository = Mockito.mock(SetorRepository.class);
	private static ArmazemService armazemService = Mockito.mock(ArmazemService.class);

	@BeforeAll
	public static void setUp() {
		setorService = new SetorService(
			setorRepository,
			armazemService
		);
	}

	@Test
	public void shouldThrownInvalidSetorException() {
		assertThrows(
			BusinessValidationException.class,
			() -> setorService.findSetorById(1l)
		);
	}

	@Test
	public void shouldNotThrownInvalidSetorException() {
		Mockito.when(setorRepository.findById(2l)).thenReturn(Optional.of((Setor) Setor.builder().build()));
		assertDoesNotThrow(
			() -> setorService.findSetorById(2l)
		);
	}
}
