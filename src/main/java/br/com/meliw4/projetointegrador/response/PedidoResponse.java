package br.com.meliw4.projetointegrador.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponse {

	private List<ProdutoPedidoResponse> produtosPedido = new ArrayList<>();

}
