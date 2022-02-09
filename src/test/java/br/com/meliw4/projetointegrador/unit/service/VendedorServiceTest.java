package br.com.meliw4.projetointegrador.unit.service;

import br.com.meliw4.projetointegrador.service.UsuarioService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.Optional;

import br.com.meliw4.projetointegrador.dto.VendedorDTO;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.VendedorRepository;
import br.com.meliw4.projetointegrador.service.VendedorService;
import org.mockito.Mockito;

public class VendedorServiceTest {
	private static VendedorRepository vendedorRepository = mock(VendedorRepository.class);
	private static UsuarioService usuarioService = mock(UsuarioService.class);
	private static VendedorService vendedorService = new VendedorService(vendedorRepository, usuarioService);

	@Test
	void shouldFindVendedorById() {
		Vendedor vendedor = Vendedor.builder().id(1L).build();
		when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));

		Vendedor response = vendedorService.findVendedorById(1L);
		assertEquals(1L, response.getId());
	}

	@Test
	void shouldReturnBusinessValidationExceptionIfVendedorListIsNullInFindVendedorById() {
		assertThrows(BusinessValidationException.class, () -> vendedorService.findVendedorById(any()));
	}

	@Test
	void shouldRegister() {
		VendedorDTO vendedorDTO = VendedorDTO.builder().login("xpto").senha("xpto").id(1L).nome("Vendedor 1").build();
		Mockito.when(usuarioService.usuarioCadastrado(Mockito.anyString())).thenReturn(true);
		Vendedor response = vendedorService.register(vendedorDTO);

		//assertEquals(1L, response.getId());
		assertEquals("Vendedor 1", response.getNome());
	}

	@Test
	void shouldReturnExceptionWhenRegistering() {
		VendedorDTO vendedorDTO = VendedorDTO.builder().login("xpto").senha("xpto").id(1L).nome("Vendedor 1").build();
		Mockito.when(usuarioService.usuarioCadastrado(Mockito.anyString())).thenReturn(false);
		BusinessValidationException b = assertThrows(
			BusinessValidationException.class,
			() -> vendedorService.register(vendedorDTO)
		);
		assertTrue(b.getMessage().equals("Login já existente na base de dados"));
	}
}
