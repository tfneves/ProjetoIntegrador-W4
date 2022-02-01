package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.CarrinhoDTO;
import br.com.meliw4.projetointegrador.dto.ProdutoCarrinhoDTO;
import br.com.meliw4.projetointegrador.entity.*;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.CarrinhoRepository;
import br.com.meliw4.projetointegrador.repository.ProdutoCarrinhoRepository;
import br.com.meliw4.projetointegrador.repository.ProdutoRepository;
import br.com.meliw4.projetointegrador.response.PedidoResponse;
import br.com.meliw4.projetointegrador.response.ProdutoPedidoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

	@Autowired
	CarrinhoRepository carrinhoRepository;
	@Autowired
	ProdutoCarrinhoRepository produtoCarrinhoRepository;
	@Autowired
	StatusPedidoService statusPedidoService;
	@Autowired
	CompradorService compradorService;
	@Autowired
	ProdutoServiceOrder produtoServiceOrder;


	public PedidoService(CarrinhoRepository carrinhoRepository, ProdutoCarrinhoRepository produtoCarrinhoRepository) {
		this.carrinhoRepository = carrinhoRepository;
		this.produtoCarrinhoRepository = produtoCarrinhoRepository;
	}


	/**
	 *
	 * Salva pedido nas tabelas pertinentes
	 * @param dto
	 * @return
	 */
	public boolean salvarPedido(CarrinhoDTO dto) {
		List<ProdutoCarrinho> produtosCarrinho = new ArrayList<>();

		StatusPedido statusPedido = statusPedidoService.findStatusCodeWithName("CHECKOUT");
		Comprador comprador = compradorService.findCompradorById(dto.getIdComprador());
		Carrinho carrinho = CarrinhoDTO.parseToEntityCarrinho(statusPedido, comprador);

		for(ProdutoCarrinhoDTO produtoCarrinhoDTO : dto.getProdutos()) {
			Produto produto = produtoServiceOrder.findProdutoById(produtoCarrinhoDTO.getProdutoId());
			produtosCarrinho.add(CarrinhoDTO.parseToEntityProdutoCarrinho(produtoCarrinhoDTO, produto, carrinho));
		}

		carrinhoRepository.save(carrinho);
		for(ProdutoCarrinho p : produtosCarrinho){
			produtoCarrinhoRepository.save(p);
		}

		return true;
	}


	//public


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
									.build());
		}
		return pedidoResponse;
	}

	private Carrinho validatePedido(Long id) {
		if (!carrinhoRepository.existsById(id)) {
			throw new BusinessValidationException("O pedido näo existe.");
		}
		Carrinho pedido = carrinhoRepository.getById(id);
		if (!pedido.getStatusPedido().getStatusCode().equals("Finzalizado")) {
			throw new BusinessValidationException("O pedido não foi finalizado.");
		}
		return pedido;
	}
}
