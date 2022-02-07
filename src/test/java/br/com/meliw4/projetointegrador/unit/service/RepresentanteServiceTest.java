package br.com.meliw4.projetointegrador.unit.service;

import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.ArmazemRepository;
import br.com.meliw4.projetointegrador.repository.RepresentanteRepository;
import br.com.meliw4.projetointegrador.service.ArmazemService;
import br.com.meliw4.projetointegrador.service.RepresentanteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RepresentanteServiceTest {

	private static RepresentanteService representanteService;
	private static RepresentanteRepository representanteRepository = Mockito.mock(RepresentanteRepository.class);
	private static ArmazemService armazemService = Mockito.mock(ArmazemService.class);

	@BeforeAll
	public static void setUp() {
		representanteService = new RepresentanteService(
			representanteRepository,
			armazemService
		);
	}

	@Test
	public void shouldThrownInvalidRepresentanteException() {
		assertThrows(
			BusinessValidationException.class,
			() -> representanteService.findRepresentanteById(1l)
		);
	}

	@Test
	public void shouldNotThrownInvalidRepresentanteException() {
		Mockito.when(representanteRepository.findById(2l)).thenReturn(Optional.of((Representante) Representante.builder().build()));
		assertDoesNotThrow(
			() -> representanteService.findRepresentanteById(2l)
		);
	}
}
