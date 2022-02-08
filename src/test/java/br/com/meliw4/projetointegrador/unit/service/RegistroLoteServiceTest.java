package br.com.meliw4.projetointegrador.unit.service;

import br.com.meliw4.projetointegrador.entity.RegistroLote;
import br.com.meliw4.projetointegrador.repository.RegistroLoteRepository;
import br.com.meliw4.projetointegrador.service.RegistroLoteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RegistroLoteServiceTest {

	private static RegistroLoteService registroLoteService;
	private static RegistroLoteRepository registroLoteRepository = Mockito.mock(RegistroLoteRepository.class);

	@BeforeAll
	public static void setUp() {
		registroLoteService = new RegistroLoteService(
			registroLoteRepository
		);
	}

	@Test
	public void shouldSaveRegistroLote() {
		assertDoesNotThrow(
			() -> registroLoteService.save(RegistroLote.builder().build())
		);
	}
}
