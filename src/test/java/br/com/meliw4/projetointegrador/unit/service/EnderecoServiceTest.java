package br.com.meliw4.projetointegrador.unit.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import br.com.meliw4.projetointegrador.entity.Comprador;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import org.junit.jupiter.api.Test;

import br.com.meliw4.projetointegrador.dto.EnderecoDTO;
import br.com.meliw4.projetointegrador.entity.Endereco;
import br.com.meliw4.projetointegrador.repository.EnderecoRepository;
import br.com.meliw4.projetointegrador.service.EnderecoService;
import org.mockito.Mockito;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public class EnderecoServiceTest {
	private static EnderecoRepository enderecoRepository = mock(EnderecoRepository.class);
	private static EnderecoService enderecoService = new EnderecoService(enderecoRepository);

	@Test
	void shouldGetById() {
		Endereco endereco = Endereco.builder().logradouro("Logradouro 1").numero(1).bairro("bairro")
			.cidade("cidade").estado("estado").build();

		when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));
		Endereco response = enderecoService.findById(1L);
		assertEquals("bairro", response.getBairro());
	}

	void shouldNotThrowExceptionWhenGetById() {
		assertDoesNotThrow(
				() -> enderecoService.findById(any()));
	}

	@Test
	void testRegister() {
		EnderecoDTO enderecoDTO = EnderecoDTO.builder().logradouro("Logradouro 1").numero(1).bairro("bairro")
				.cidade("cidade").estado("estado").build();
		Endereco endereco = Endereco.builder().logradouro("Logradouro 1").numero(1).bairro("bairro")
			.cidade("cidade").estado("estado").build();

		Endereco response = enderecoService.register(enderecoDTO);
		assertEquals(1, response.getNumero());
	}


	@Test
	void shouldReturnExceptionWhenRegistering() {
		BusinessValidationException b = assertThrows(
			BusinessValidationException.class,
			() -> enderecoService.findById(Mockito.anyLong())
		);
		assertTrue(b.getMessage().equals("O endereço informado não existe no sistema"));
	}
}
