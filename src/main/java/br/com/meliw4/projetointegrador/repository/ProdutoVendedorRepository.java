package br.com.meliw4.projetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoVendedorRepository extends JpaRepository<ProdutoVendedor, Long> {
	@Query(value = "SELECT * FROM produto_vendedor pv WHERE lote_id = :loteId AND produto_id = :produtoId LIMIT 1", nativeQuery = true)
	ProdutoVendedor findByLoteIdAndProdutoId(@Param("loteId") Long loteId, @Param("produtoId") Long produtoId);

	Optional<List<ProdutoVendedor>> findProdutoVendedorByProduto_Id(Long id);

	Optional<List<ProdutoVendedor>> findByProduto_Id(Long produtoId);
}
