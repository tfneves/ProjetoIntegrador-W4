
package br.com.meliw4.projetointegrador.repository;

import br.com.meliw4.projetointegrador.entity.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {
}


