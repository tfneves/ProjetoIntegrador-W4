package br.com.meliw4.projetointegrador.repository;

import br.com.meliw4.projetointegrador.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByLogin(String login);
	List<Usuario> getByLogin(String login);
}
