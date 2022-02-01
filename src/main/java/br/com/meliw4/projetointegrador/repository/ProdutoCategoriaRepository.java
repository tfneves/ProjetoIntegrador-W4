package br.com.meliw4.projetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.meliw4.projetointegrador.entity.ProdutoCategoria;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoCategoriaRepository extends JpaRepository<ProdutoCategoria, Long> {

	@Query(value = "SELECT pc.* FROM produto_categorias pc " +
			"WHERE pc.categoria = :categoriaId", nativeQuery = true)
	ProdutoCategoria getByCategoria(@Param("categoriaId") String categoriaId);

	@Query(value = "SELECT COUNT(*) FROM produto_categorias pc " +
			"WHERE pc.categoria = :categoriaId", nativeQuery = true)
	Integer existsByCategoria(@Param("categoriaId") String categoriaId);
}
