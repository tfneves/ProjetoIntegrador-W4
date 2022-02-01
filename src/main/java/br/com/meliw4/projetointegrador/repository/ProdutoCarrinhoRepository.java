package br.com.meliw4.projetointegrador.repository;
import br.com.meliw4.projetointegrador.entity.ProdutoCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoCarrinhoRepository extends JpaRepository<ProdutoCarrinhoRepository, Long> {
	@Query(value = "SELECT * FROM produto_carrinho WHERE carrinh0_id = :id")
	List<ProdutoCarrinho> findByPedidoId(@Param("id") Long id);
}
