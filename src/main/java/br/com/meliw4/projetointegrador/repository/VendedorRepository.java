package br.com.meliw4.projetointegrador.repository;

import br.com.meliw4.projetointegrador.entity.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface VendedorRepository extends JpaRepository<Vendedor , Long> {
}
