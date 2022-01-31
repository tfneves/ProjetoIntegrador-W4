package br.com.meliw4.projetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import br.com.meliw4.projetointegrador.entity.ProdutoVendedorId;

@Repository
public interface VendedorProdutoRepository extends JpaRepository<ProdutoVendedor, ProdutoVendedorId> {

	// BigDecimal getByProdutoVendedorId(ProdutoVendedorId produtoVendedorId);
}
