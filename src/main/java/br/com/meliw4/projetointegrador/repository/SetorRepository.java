package br.com.meliw4.projetointegrador.repository;

import br.com.meliw4.projetointegrador.entity.Setor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {

	Optional<List<Setor>> findSetorByArmazem_Id(Long armazemId);
}
