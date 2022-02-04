package br.com.meliw4.projetointegrador.repository;

import br.com.meliw4.projetointegrador.entity.ProdutoCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoCarrinhoRepository extends JpaRepository<ProdutoCarrinho, Long> {

	//@Query(value = "SELECT * FROM produto_carrinho WHERE carrinho_id = :id", nativeQuery = true)
	//List<ProdutoCarrinho> findByPedidoId(@Param("id") Long id);
	List<ProdutoCarrinho> findByCarrinho_Id(Long id);

}
