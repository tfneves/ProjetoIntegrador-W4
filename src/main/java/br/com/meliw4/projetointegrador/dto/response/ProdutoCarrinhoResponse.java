package br.com.meliw4.projetointegrador.dto.response;

import br.com.meliw4.projetointegrador.entity.*;
import br.com.meliw4.projetointegrador.response.CarrinhoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCarrinhoResponse {

	private Long id;
	private CarrinhoResponse  carrinho;
	private ProdutoVendedorDTO produto;
	private Integer quantidade;


	public static List<ProdutoCarrinhoResponse> converte(List<ProdutoCarrinho> produtos){
		List<ProdutoCarrinhoResponse> produtosResponse = new ArrayList<>();
		for (ProdutoCarrinho produto : produtos) {
			produtosResponse.add(ProdutoCarrinhoResponse.builder()
				.id(produto.getId())
				.carrinho(CarrinhoResponse.converte(produto.getCarrinho()))
				.produto(ProdutoVendedorDTO.converte(produto.getProduto()))
				.quantidade(produto.getQuantidade())
				.build());
		}
		return produtosResponse;
	}

}
