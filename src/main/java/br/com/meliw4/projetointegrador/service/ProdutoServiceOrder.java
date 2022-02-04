package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceOrder {

	@Autowired
	ProdutoRepository produtoRepository;

	public Produto findProdutoById(Long id) {
		return produtoRepository.findById(id)
				.orElseThrow(() -> new BusinessValidationException("O produto de ID " + id + " não existe"));
	}
}
