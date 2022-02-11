package br.com.meliw4.projetointegrador.unit.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import br.com.meliw4.projetointegrador.exception.NotFoundException;
import org.junit.jupiter.api.Test;

import br.com.meliw4.projetointegrador.dto.EnderecoDTO;
import br.com.meliw4.projetointegrador.entity.Endereco;
import br.com.meliw4.projetointegrador.repository.EnderecoRepository;
import br.com.meliw4.projetointegrador.service.EnderecoService;
import org.mockito.Mockito;

import java.util.Optional;

public class EnderecoServiceTest {
	private static EnderecoRepository enderecoRepository = mock(EnderecoRepository.class);
	private static EnderecoService enderecoService = new EnderecoService(enderecoRepository);

	@Test
	void shouldGetById() {
		Mockito.when(enderecoRepository.findById(1L)).thenReturn(Optional.of((Endereco) Endereco.builder().id(1L).build()));

		Endereco response = enderecoService.getById(1L);
		assertEquals(1L, response.getId());
	}

	@Test
	void testRegister() {
		EnderecoDTO enderecoDTO = EnderecoDTO.builder().logradouro("Logradouro 1").numero(1).bairro("bairro")
			.cidade("cidade").estado("estado").build();

		Endereco response = enderecoService.register(enderecoDTO);
		assertEquals(1, response.getNumero());
	}
}
