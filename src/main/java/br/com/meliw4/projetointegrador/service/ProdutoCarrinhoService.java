package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.ProdutoCarrinho;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.exception.OrderCheckoutException;
import br.com.meliw4.projetointegrador.repository.ProdutoCarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoCarrinhoService {

	@Autowired
	ProdutoCarrinhoRepository produtoCarrinhoRepository;

	/**
	 * Faz persistencia do ProdutoCarrinho
	 * @author Thomaz Ferreira
	 * @param produtoCarrinho
	 * @return ProdutoCarrinho
	 */
	public ProdutoCarrinho salvaProdutoCarrinho(ProdutoCarrinho produtoCarrinho) {
		try{
			return produtoCarrinhoRepository.save(produtoCarrinho);
		}catch (RuntimeException e){
			throw new OrderCheckoutException("Erro ao salvar produtos do carrinho - " + e.getMessage(), 500);
		}
	}


	/**
	 * Busca e retorna lista de Produtos de um carrinho(idCarrinho)
	 * @author Thomaz Ferreira
	 * @param idCarrinho
	 * @return List
	 */
	public List<ProdutoCarrinho> buscaProdutosCarrinhoById(Long idCarrinho) {
		List<ProdutoCarrinho> produtosCarrinho = produtoCarrinhoRepository.findByCarrinho_Id(idCarrinho);
		if(produtosCarrinho.isEmpty())
			throw new BusinessValidationException("O carrinho informado não possui nenhum produto");
		return produtosCarrinho;
	}

}
