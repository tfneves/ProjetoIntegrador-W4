package br.com.meliw4.projetointegrador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;

@Repository
public interface VendedorProdutoRepository extends JpaRepository<ProdutoVendedor, Long> {

	@Query(value = "SELECT * FROM produto_vendedor WHERE produto_id = :produtoId", nativeQuery = true)
	List<ProdutoVendedor> findByProdutoId(@Param("produtoId") Long produtoId);
}
