package br.com.meliw4.projetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;

@Repository
public interface ProdutoVendedorRepository extends JpaRepository<ProdutoVendedor, Long> {
	@Query(value = "SELECT * FROM produto_vendedor pv WHERE lote_id = :loteId AND produto_id = :produtoId AND vendedor_id = :vendedorId LIMIT 1", nativeQuery = true)
	ProdutoVendedor findByLoteIdAndProdutoIdAndVendedorId(@Param("loteId") Long loteId, @Param("produtoId") Long produtoId, @Param("vendedorId") Long vendedorId);
}
