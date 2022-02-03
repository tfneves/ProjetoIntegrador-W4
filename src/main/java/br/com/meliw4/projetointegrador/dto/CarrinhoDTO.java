package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarrinhoDTO {

	private Long idComprador;
	private List<ProdutoCarrinhoDTO> produtos;


	public static Carrinho parseToEntityCarrinho(StatusPedido statusPedido, Comprador comprador) {
		return Carrinho.builder()
				.data(LocalDate.now())
				.comprador(comprador)
				.statusPedido(statusPedido)
				.build();
	}


	/**
	 * Converte Lista de ProdutoCarrinhoDTO para ProdutoCarrinho
	 * @author Thomaz Ferreira
	 * @param produtoCarrinhoDTO
	 * @param produtoVendedor
	 * @param carrinho
	 * @return ProdutoCarrinho
	 */
	public static ProdutoCarrinho parseToEntityProdutoCarrinho(ProdutoCarrinhoDTO produtoCarrinhoDTO, ProdutoVendedor produtoVendedor, Carrinho carrinho) {
		return ProdutoCarrinho.builder()
			.produto(produtoVendedor)
			.carrinho(carrinho)
			.quantidade(produtoCarrinhoDTO.getQuantidade())
			.build();
	}
}
