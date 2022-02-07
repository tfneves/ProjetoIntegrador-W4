package br.com.meliw4.projetointegrador.repository;

import br.com.meliw4.projetointegrador.entity.Representante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepresentanteRepository extends JpaRepository<Representante, Long> {
	Representante findByEmail(String email);
}
