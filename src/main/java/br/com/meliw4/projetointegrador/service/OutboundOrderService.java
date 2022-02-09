package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.response.ProdutoCarrinhoResponse;
import br.com.meliw4.projetointegrador.dto.response.ProdutoOutboundResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.ProdutoVendedorDTO;
import br.com.meliw4.projetointegrador.dto.response.ProdutoVendedorResponseDTO;
import br.com.meliw4.projetointegrador.entity.Lote;
import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import br.com.meliw4.projetointegrador.repository.LoteRepository;
import br.com.meliw4.projetointegrador.repository.ProdutoVendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OutboundOrderService {

	@Autowired
	private ProdutoCarrinhoService produtoCarrinhoService;

	@Autowired
	private ProdutoVendedorService produtoVendedorService;

	public Set<Long> listaTodosOsCarrinhosParaOutBound() {
		List<ProdutoCarrinhoResponse> produtos = produtoCarrinhoService.listaTodosOsProdutosPorCarrinho();
		return produtos.stream()
			.filter(produto -> produto.getCarrinho().getStatusPedido().getId() == 2)
			.map(produto -> produto.getCarrinho().getId())
			.collect(Collectors.toSet());
	}

	public List<ProdutoOutboundResponseDTO> baixaOutBoundPorId(Long id) {
		List<ProdutoCarrinhoResponse> produtosCarrinho = retornaAListadeCarrinho(id);
		List<ProdutoOutboundResponseDTO> produtos = retornaOsProdutosOrdenadosPorValidade(produtosCarrinho);
		return produtos.stream().sorted(Comparator.comparing(ProdutoOutboundResponseDTO::getProduto)).collect(Collectors.toList());
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
