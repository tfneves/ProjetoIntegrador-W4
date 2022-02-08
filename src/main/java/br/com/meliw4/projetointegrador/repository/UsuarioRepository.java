package br.com.meliw4.projetointegrador.repository;

import br.com.meliw4.projetointegrador.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByLogin(String login);
}
