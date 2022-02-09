package br.com.meliw4.projetointegrador.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import br.com.meliw4.projetointegrador.dto.RepresentanteDTO;
import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.service.UsuarioService;
import org.junit.jupiter.api.Test;

import br.com.meliw4.projetointegrador.dto.CompradorDTO;
import br.com.meliw4.projetointegrador.entity.Comprador;
import br.com.meliw4.projetointegrador.entity.Endereco;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.CompradorRepository;
import br.com.meliw4.projetointegrador.service.CompradorService;
import br.com.meliw4.projetointegrador.service.EnderecoService;
import org.mockito.Mockito;

public class CompradorServiceTest {
	private static CompradorRepository compradorRepository = mock(CompradorRepository.class);
	private static EnderecoService enderecoService = mock(EnderecoService.class);
	private static UsuarioService usuarioService = mock(UsuarioService.class);

	public static CompradorService compradorService = new CompradorService(compradorRepository, enderecoService, usuarioService);

	@Test
	void testFindCompradorById() {
		Comprador comprador = Comprador.builder().id(1L).build();
		when(compradorRepository.findById(1L)).thenReturn(Optional.of(comprador));

		Comprador response = compradorService.findCompradorById(1L);
		assertEquals(1L, response.getId());
	}

	@Test
	void shouldNotThrowBusinessValidationExceptionWhenAtualizaCarrinho() {
		assertThrows(BusinessValidationException.class,
				() -> compradorService.findCompradorById(any()));
	}

	@Test
	void testRegister() {
		Endereco endereco1 = Endereco.builder().id(1L).build();
		when(enderecoService.findById(1L)).thenReturn(endereco1);
		CompradorDTO compradorDTO = CompradorDTO.builder().login("xpto").senha("xpto").id(1L).nome("Comprador 1").dataNascimento(LocalDate.MIN)
				.email("email@email.com").telefone("9999999").endereco_id(1L).build();
		Mockito.when(usuarioService.usuarioCadastrado(Mockito.anyString())).thenReturn(true);

		Comprador response = compradorService.register(compradorDTO);
		//assertEquals(1L, response.getId());
		assertEquals(1L, response.getEndereco().getId());
	}


	@Test
	void shouldReturnExceptionWhenRegistering() {
		Endereco endereco1 = Endereco.builder().id(1L).build();
		when(enderecoService.findById(1L)).thenReturn(endereco1);
		CompradorDTO compradorDTO = CompradorDTO.builder().login("xpto").senha("xpto").id(1L).nome("Comprador 1").dataNascimento(LocalDate.MIN)
			.email("email@email.com").telefone("9999999").endereco_id(1L).build();
		Mockito.when(usuarioService.usuarioCadastrado(Mockito.anyString())).thenReturn(false);
		BusinessValidationException b = assertThrows(
			BusinessValidationException.class,
			() -> compradorService.register(compradorDTO)
		);
		assertTrue(b.getMessage().equals("Login já existente na base de dados"));
	}
}
