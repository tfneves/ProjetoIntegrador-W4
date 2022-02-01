package br.com.meliw4.projetointegrador.repository;

import br.com.meliw4.projetointegrador.entity.RegistroLote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroLoteRepository extends JpaRepository<RegistroLote, Long> {
}

