package br.com.meliw4.projetointegrador.auth.config;

import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.entity.Usuario;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import br.com.meliw4.projetointegrador.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

	private UsuarioRepository usuarioRepository;

	public AuthenticationService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByLogin(login);
		if(usuario instanceof Representante){
			return new Representante(usuario.getId(), usuario.getLogin(), usuario.getSenha());
		}
		return new Vendedor(usuario.getId(), usuario.getLogin(), usuario.getSenha());
	}
}
