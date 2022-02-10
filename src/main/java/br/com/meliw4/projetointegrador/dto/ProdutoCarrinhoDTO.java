package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.ProdutoCarrinho;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoCarrinhoDTO {

	@NotNull(message = "O campo anuncioId não pode ser vazio")
	private Long anuncioId;
	@NotNull(message = "A quantidade de produtos não pode ser vazia")
	private Integer quantidade;

	public static ProdutoCarrinhoDTO parseToDTO(ProdutoCarrinho produtoCarrinho) {
		return ProdutoCarrinhoDTO.builder()
			.anuncioId(produtoCarrinho.getProduto().getId())
			.quantidade(produtoCarrinho.getQuantidade())
			.build();
	}
}
