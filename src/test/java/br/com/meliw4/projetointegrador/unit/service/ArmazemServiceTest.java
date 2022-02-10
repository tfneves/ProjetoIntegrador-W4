package br.com.meliw4.projetointegrador.unit.service;

import br.com.meliw4.projetointegrador.dto.ArmazemDTO;
import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.ArmazemRepository;
import br.com.meliw4.projetointegrador.service.ArmazemService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ArmazemServiceTest {

	private static ArmazemService armazemService;
	private static ArmazemRepository armazemRepository = Mockito.mock(ArmazemRepository.class);

	@BeforeAll
	public static void setUp() {
		armazemService = new ArmazemService(
				armazemRepository);
	}

	@Test
	public void shouldThrowInvalidArmazemException() {
		assertThrows(
				BusinessValidationException.class,
				() -> armazemService.findArmazemById(1l));
	}

	@Test
	public void shouldNotThrowInvalidArmazemException() {
		Mockito.when(armazemRepository.findById(2l)).thenReturn(Optional.of((Armazem) Armazem.builder().build()));
		assertDoesNotThrow(
				() -> armazemService.findArmazemById(2l));
	}

	@Test
	public void shouldNotThrowInvalidArmazemExceptionWhileFindAll() {
		assertDoesNotThrow(
				() -> armazemService.findAll());
	}

	@Test
	public void shouldReturnListArmazemDTO() {
		List<Armazem> armazens = new ArrayList<>();
		armazens.add(
				Armazem.builder()
						.nome("Teste 1")
						.volume(2000.0)
						.build());
		armazens.add(
				Armazem.builder()
						.nome("Teste 2")
						.volume(2000.0)
						.build());
		Mockito.when(armazemRepository.findAll()).thenReturn(armazens);
		List<ArmazemDTO> armazensDTO = armazemService.findAllDTO();
		assertEquals(armazensDTO.size(), 2);
		assertEquals(armazensDTO.get(0).getNome(), "Teste 1");
		assertEquals(armazensDTO.get(1).getNome(), "Teste 2");
	}

	@Test
	public void shouldSaveArmazem() {
		assertDoesNotThrow(
				() -> armazemService.save(Armazem.builder().build()));
	}
}
