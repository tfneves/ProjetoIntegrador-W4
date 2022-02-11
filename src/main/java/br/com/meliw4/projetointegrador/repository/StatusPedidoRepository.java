package br.com.meliw4.projetointegrador.repository;

import br.com.meliw4.projetointegrador.entity.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusPedidoRepository extends JpaRepository<StatusPedido, Long> {

	StatusPedido findByStatusCode(String statusCode);
}
