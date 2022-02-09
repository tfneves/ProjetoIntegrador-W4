
package br.com.meliw4.projetointegrador.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	@Query(value = "SELECT p.* FROM produtos p " +
			"WHERE p.categoria_id = :categoriaId", nativeQuery = true)
	Optional<List<Produto>> findProdutoPorCategoria(@Param("categoriaId") String categoriaId);

	Optional<List<Produto>> findProdutoByProdutoCategoria(Categoria categoria);
}
