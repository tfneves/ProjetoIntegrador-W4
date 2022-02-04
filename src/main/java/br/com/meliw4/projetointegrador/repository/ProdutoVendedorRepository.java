package br.com.meliw4.projetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;

import java.util.List;

@Repository
public interface ProdutoVendedorRepository extends JpaRepository<ProdutoVendedor, Long> {
	@Query(value = "SELECT * FROM produto_vendedor pv WHERE lote_id = :loteId AND produto_id = :produtoId LIMIT 1",
		nativeQuery = true)
	ProdutoVendedor findByLoteIdAndProdutoId(@Param("loteId") Long loteId, @Param("produtoId") Long produtoId);

	List<ProdutoVendedor> findProdutoVendedorByProduto_Id(Long id);
}
