
package br.com.meliw4.projetointegrador.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.entity.enumeration.Tipo;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	//Optional<Produto> findById(Long productId);

	//List<Produto> findProductByType(Tipo productType);

	//Optional<List<Produto>> findProductsBySellerId(Integer sellerId);
}

