package br.com.meliw4.projetointegrador.dto.response;

import br.com.meliw4.projetointegrador.response.CarrinhoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoOutboundResponseDTO {
	private CarrinhoResponse carrinho;

	public static CarrinhoOutboundResponseDTO converte(ProdutoCarrinhoResponse carrinho) {
		return CarrinhoOutboundResponseDTO.builder()
			.carrinho(carrinho.getCarrinho())
			.build();
	}

}
