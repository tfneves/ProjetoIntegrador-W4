package br.com.meliw4.projetointegrador.repository;

import br.com.meliw4.projetointegrador.entity.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {
}
