package br.com.meliw4.projetointegrador.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProdutoPedidoResponse {

	private Long produtoCarrinhoId;
	private Long anuncioId;
	private Long produtoId;
	private Long vendedorId;
	private Integer quantidade;
}
