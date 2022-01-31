package br.com.meliw4.projetointegrador.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoCarrinho extends JpaRepository<ProdutoCarrinho, Long> {
}
