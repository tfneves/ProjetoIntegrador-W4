package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.Carrinho;
import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.entity.ProdutoCarrinho;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.CarrinhoRepository;
import br.com.meliw4.projetointegrador.repository.ProdutoCarrinhoRepository;
import br.com.meliw4.projetointegrador.response.PedidoResponse;
import br.com.meliw4.projetointegrador.response.ProdutoPedidoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PedidoService {

	@Autowired
	CarrinhoRepository carrinhoRepository;

	@Autowired
	ProdutoCarrinhoRepository produtoCarrinhoRepository;


	public PedidoResponse getPedido(Long id) {
		PedidoResponse pedidoResponse = new PedidoResponse();
		Carrinho pedido = validatePedido(id);
		List<ProdutoCarrinho> produtosCarrinho = produtoCarrinhoRepository.findByPedidoId(id);
		for (ProdutoCarrinho produtoCarrinho : produtosCarrinho) {
			pedidoResponse.getProdutosPedido()
				.add(
					ProdutoPedidoResponse.builder()
						.id(produtoCarrinho.getId())
						.quantidade(produtoCarrinho.getQuantidade())
						.build()
				);
		}
		return pedidoResponse;
	}

	private Carrinho validatePedido(Long id) {
		if (!carrinhoRepository.existsById(id)) {
			throw new BusinessValidationException("O pedido näo existe.");
		}
		Carrinho pedido = carrinhoRepository.getById(id);
		if (pedido.getStatusPedido().getNome() != "Finzalizado") {
			throw new BusinessValidationException("O pedido não foi finalizado.");
		}
		return pedido;
	}
}
