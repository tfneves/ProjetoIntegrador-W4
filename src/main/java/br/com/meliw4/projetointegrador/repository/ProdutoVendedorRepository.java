package br.com.meliw4.projetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;

@Repository
public interface ProdutoVendedorRepository extends JpaRepository<ProdutoVendedor, Long> {
	// BigDecimal getByProdutoVendedorId(ProdutoVendedorId produtoVendedorId);
}
