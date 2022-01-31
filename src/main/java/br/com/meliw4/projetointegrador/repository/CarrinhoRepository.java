package br.com.meliw4.projetointegrador.repository;

import br.com.meliw4.projetointegrador.entity.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
}
