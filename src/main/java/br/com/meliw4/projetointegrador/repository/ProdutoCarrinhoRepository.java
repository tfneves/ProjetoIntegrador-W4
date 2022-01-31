package br.com.meliw4.projetointegrador.repository;

import br.com.meliw4.projetointegrador.entity.ProdutoCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoCarrinhoRepository extends JpaRepository<ProdutoCarrinho, Long> {
}
