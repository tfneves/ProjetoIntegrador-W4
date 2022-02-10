package br.com.meliw4.projetointegrador.unit.service;

import br.com.meliw4.projetointegrador.entity.Usuario;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import br.com.meliw4.projetointegrador.repository.UsuarioRepository;
import br.com.meliw4.projetointegrador.service.UsuarioService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsuarioServiceTest {

	private static UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
	private static UsuarioService usuarioService = new UsuarioService(usuarioRepository);

	@Test
	void shouldReturnFalse() {
		List<Usuario> usuarioList = new ArrayList<>();
		usuarioList.add(Vendedor.builder().build());
		when(usuarioRepository.getByLogin(Mockito.anyString())).thenReturn(usuarioList);
		Assert.assertFalse(usuarioService.usuarioCadastrado("xpto"));
	}

	@Test
	void shouldReturnTrue() {
		List<Usuario> usuarioList = new ArrayList<>();
		when(usuarioRepository.getByLogin(Mockito.anyString())).thenReturn(usuarioList);
		Assert.assertTrue(usuarioService.usuarioCadastrado("xpto"));
	}
}
