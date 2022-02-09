package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.response.ProdutoCarrinhoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OutboundOrderService {

	@Autowired
	private ProdutoCarrinhoService produtoCarrinhoService;

	public Set<Long> listaTodosOsCarrinhosParaOutBound() {
		List<ProdutoCarrinhoResponse> produtos = produtoCarrinhoService.listaTodosOsProdutosPorCarrinho();
		return produtos.stream()
			.filter(produto -> produto.getCarrinho().getStatusPedido().getId() == 2)
			.map(produto -> produto.getCarrinho().getId())
			.collect(Collectors.toSet());
	}

	public List<?> baixaOutBoundPorId(Long id) {
		List<ProdutoCarrinhoResponse> produtos = produtoCarrinhoService.listaTodosOsProdutosPorCarrinho();
		return produtos.stream()
			.filter(carrinho -> carrinho.getCarrinho().getId() == id)
			.collect(Collectors.toList());
	}
}
