package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.Usuario;
import br.com.meliw4.projetointegrador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

	UsuarioRepository usuarioRepository;
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	/**
	 * Verifica se existe algum usuário com o login informado
	 * Se retornar false, significa que o login já existe na base de dados
	 * @return boolean
	 */
	public boolean usuarioCadastrado(String login) {
		return usuarioRepository.getByLogin(login).isEmpty();
	}
}