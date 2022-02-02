package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import br.com.meliw4.projetointegrador.repository.ProdutoVendedorRepository;
import org.springframework.stereotype.Service;

@Service
public class ProdutoVendedorService {

	private ProdutoVendedorRepository produtoVendedorRepository;

	public ProdutoVendedorService(ProdutoVendedorRepository produtoVendedorRepository) {
		this.produtoVendedorRepository = produtoVendedorRepository;
	}

	public void save(ProdutoVendedor produtoVendedor) {
		produtoVendedorRepository.save(produtoVendedor);
	}

	public ProdutoVendedor findByLoteIdAndProdutoIdAndVendedorId(Long loteId, Long produtoId, Long vendedorId) {
		return produtoVendedorRepository.findByLoteIdAndProdutoIdAndVendedorId(loteId, produtoId, vendedorId);
	}
}
