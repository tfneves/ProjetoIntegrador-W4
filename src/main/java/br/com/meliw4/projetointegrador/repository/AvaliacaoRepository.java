package br.com.meliw4.projetointegrador.repository;

import br.com.meliw4.projetointegrador.entity.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
	@Query(value = "SELECT * FROM avaliacao WHERE comprador_id = :compradorId AND pedido_id = :pedidoId AND " +
		"anuncio_id = :anuncioId LIMIT 1", nativeQuery = true)
	Avaliacao findByCompradorIdAndPedidoIdAndAnuncioId(Long compradorId, Long pedidoId, Long anuncioId);
}
