package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.response.ProdutoCarrinhoResponse;
import br.com.meliw4.projetointegrador.entity.ProdutoCarrinho;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.exception.OrderCheckoutException;
import br.com.meliw4.projetointegrador.repository.ProdutoCarrinhoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProdutoCarrinhoService {

	private final ProdutoCarrinhoRepository produtoCarrinhoRepository;

	public ProdutoCarrinhoService(ProdutoCarrinhoRepository produtoCarrinhoRepository) {
		this.produtoCarrinhoRepository = produtoCarrinhoRepository;
	}

	/**
	 * Faz persistencia do ProdutoCarrinho
	 *
	 * @author Thomaz Ferreira
	 * @param produtoCarrinho
	 * @return ProdutoCarrinho
	 */
	public ProdutoCarrinho salvaProdutoCarrinho(ProdutoCarrinho produtoCarrinho) {
		try {
			return produtoCarrinhoRepository.save(produtoCarrinho);
		} catch (RuntimeException e) {
			throw new OrderCheckoutException("Erro ao salvar produtos do carrinho - " + e.getMessage(), 500);
		}
	}

	/**
	 * Busca e retorna lista de Produtos de um carrinho(idCarrinho)
	 *
	 * @author Thomaz Ferreira
	 * @param idCarrinho
	 * @return List
	 */
	public List<ProdutoCarrinho> buscaProdutosCarrinhoById(Long idCarrinho) {
		List<ProdutoCarrinho> produtosCarrinho = produtoCarrinhoRepository.findByCarrinho_Id(idCarrinho);
		if (produtosCarrinho.isEmpty())
			throw new BusinessValidationException("O carrinho informado não possui nenhum produto");
		return produtosCarrinho;
	}

	public List<ProdutoCarrinhoResponse> listaTodosOsProdutosPorCarrinho(){
		return ProdutoCarrinhoResponse.converte(this.produtoCarrinhoRepository.findAll());
	}

}
