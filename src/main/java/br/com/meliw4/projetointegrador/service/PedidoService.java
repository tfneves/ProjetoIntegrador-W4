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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	@Autowired
	ProdutoVendedorService produtoVendedorService;


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

		//calculaValorTotalCarrinho(5L);
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


	/**
	 *
	 * Calcula valor total do carrinho
	 * @param idCarrinho
	 * @return
	 */
	public BigDecimal calculaValorTotalCarrinho(Long idCarrinho) {
		BigDecimal valorTotal = new BigDecimal("0.0");
		List<ProdutoCarrinho> produtosCarrinho = produtoCarrinhoRepository.findByCarrinho_Id(idCarrinho);
		for(ProdutoCarrinho produtoCarrinho : produtosCarrinho){
			ProdutoVendedor produto = produtoVendedorService.getProdutoById(produtoCarrinho.getProduto().getId());
			valorTotal.add(produto.getPreco().multiply(new BigDecimal(produtoCarrinho.getQuantidade())));
		}
		return valorTotal;
	}


	public PedidoResponse getPedido(Long id) {
		PedidoResponse pedidoResponse = new PedidoResponse();
		Carrinho pedido = validatePedido(id);
		List<ProdutoCarrinho> produtosCarrinho = produtoCarrinhoRepository.findByCarrinho_Id(id);
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
