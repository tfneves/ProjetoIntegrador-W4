package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.Produto;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceOrder {

	private ProdutoService produtoService;

	public ProdutoServiceOrder(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	public Produto findProdutoById(Long produtoId) {
		produtoService.findById(produtoId);
		return produtoService.findById(produtoId);
	}
}
