package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.response.*;
import br.com.meliw4.projetointegrador.entity.StatusPedido;
import br.com.meliw4.projetointegrador.exception.NotFoundException;
import br.com.meliw4.projetointegrador.response.CarrinhoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OutboundOrderService {

	@Autowired
	private ProdutoCarrinhoService produtoCarrinhoService;

	@Autowired
	private ProdutoVendedorService produtoVendedorService;

	@Autowired
	private CarrinhoService carrinhoService;

	@Autowired
	private StatusPedidoService statusPedidoService;

	public Set<Long> listaTodosOsCarrinhosParaOutBound() {
		List<ProdutoCarrinhoResponse> produtos = produtoCarrinhoService.listaTodosOsProdutosPorCarrinho();
		return produtos.stream()
			.filter(produto -> produto.getCarrinho().getStatusPedido().getId() == 2)
			.map(produto -> produto.getCarrinho().getId())
			.collect(Collectors.toSet());
	}

	public CarrinhoResponse realizaOOutboundDoPedidoPorId(Long id){
		StatusPedido statusPedido = this.statusPedidoService.findStatusPedidoById(3L);
		return carrinhoService.atualizaCarrinho(id, statusPedido);
	}

	public Map<Long, List<ProdutoOutboundResponseDTO>>  baixaOutBoundPorId(Long id) {
		List<ProdutoCarrinhoResponse> produtosCarrinho = retornaAListadeCarrinho(id);
		CarrinhoOutboundResponseDTO carrinho = CarrinhoOutboundResponseDTO.converte(retornaAListadeCarrinho(id).get(0));

		if(produtosCarrinho.size() == 0)
			throw new NotFoundException("Carrinho já finalizado ou não existente");

		List<ProdutoOutboundResponseDTO> produtos = retornaOsProdutosOrdenadosPorValidade(produtosCarrinho);
		produtos = produtos.stream().sorted(Comparator.comparing(ProdutoOutboundResponseDTO::getProduto)).collect(Collectors.toList());

		Map<Long, List<ProdutoOutboundResponseDTO>> response = new HashMap<>();
		response.put(carrinho.getCarrinho().getId(), produtos);
		return response;
	}

	private List<ProdutoCarrinhoResponse> retornaAListadeCarrinho(Long id){
		return produtoCarrinhoService.listaTodosOsProdutosPorCarrinho().stream()
			.filter(carrinho -> carrinho.getCarrinho().getId() == id)
			.collect(Collectors.toList());
	}

	private List<ProdutoOutboundResponseDTO> retornaOsProdutosOrdenadosPorValidade(List<ProdutoCarrinhoResponse> produtosCarrinho){
		List<ProdutoOutboundResponseDTO> produtos = new ArrayList<>();
		for (ProdutoCarrinhoResponse carrinho : produtosCarrinho) {
			produtos.add(
				ProdutoOutboundResponseDTO.converte(
				produtoVendedorService.devolveLoteAVencerPorProdutoID(carrinho.getProduto().getId()),
				carrinho.getQuantidade()
				));
		}
		return produtos.stream().sorted(Comparator.comparing(ProdutoOutboundResponseDTO::getProduto)).collect(Collectors.toList());
	}
}
