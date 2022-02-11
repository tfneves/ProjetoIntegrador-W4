package br.com.meliw4.projetointegrador.unit.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.Optional;

import br.com.meliw4.projetointegrador.dto.VendedorDTO;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.VendedorRepository;
import br.com.meliw4.projetointegrador.service.VendedorService;

public class VendedorServiceTest {
	private static VendedorRepository vendedorRepository = mock(VendedorRepository.class);
	private static VendedorService vendedorService = new VendedorService(vendedorRepository);

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
		VendedorDTO vendedorDTO = VendedorDTO.builder().id(1L).nome("Vendedor 1").build();
		Vendedor response = vendedorService.register(vendedorDTO);

		assertEquals(1L, response.getId());
		assertEquals("Vendedor 1", response.getNome());
	}
}
