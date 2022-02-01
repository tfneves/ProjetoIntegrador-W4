
package br.com.meliw4.projetointegrador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.meliw4.projetointegrador.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	@Query(value = "SELECT p.* FROM produtos p " +
	// "LEFT JOIN produto_categorias pc on pc.categoria = p.categoria_id " +
			"WHERE p.categoria_id = :categoriaId", nativeQuery = true)
	List<Produto> findProdutoPorCategoria(@Param("categoriaId") String categoriaId);
}
