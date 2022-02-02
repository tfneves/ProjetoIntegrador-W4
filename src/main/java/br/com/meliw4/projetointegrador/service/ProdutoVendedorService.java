package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.exception.InternalServerErrorException;
import br.com.meliw4.projetointegrador.repository.ProdutoVendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoVendedorService {

	@Autowired
	private ProdutoVendedorRepository produtoVendedorRepository;

	/**
	 *
	 * Busca ProdutoVendedor pelo id e retorna o obejto
	 * @param id
	 * @return ProdutoVendedor
	 */
	public ProdutoVendedor getProdutoById(Long id) {
		Optional<ProdutoVendedor> produto = produtoVendedorRepository.findById(id);
		produto.orElseThrow(() -> new BusinessValidationException("O produto de id " + id + " não existe ou foi deletado da base de dados"));
		return produto.get();
	}


	/**
	 *
	 * Atualiza (decrementa) quantidade do produto no estoque (tabela produto_vendedor)
	 * @param id
	 * @param quantidadeSolicitada
	 * @return boolean
	 */
	public boolean updateEstoqueProduto(ProdutoVendedor produtoVendedor) {
		produtoVendedorRepository.save(produtoVendedor);
		return true;
	}

}
