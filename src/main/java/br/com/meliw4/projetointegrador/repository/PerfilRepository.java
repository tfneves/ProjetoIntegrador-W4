package br.com.meliw4.projetointegrador.repository;

import br.com.meliw4.projetointegrador.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}
