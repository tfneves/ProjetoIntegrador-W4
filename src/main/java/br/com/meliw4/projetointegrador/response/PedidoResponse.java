package br.com.meliw4.projetointegrador.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponse {

	private List<ProdutoPedidoResponse> produtosPedido = new ArrayList<>();

}
