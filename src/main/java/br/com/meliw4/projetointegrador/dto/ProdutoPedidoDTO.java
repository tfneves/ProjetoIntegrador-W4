package br.com.meliw4.projetointegrador.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoPedidoDTO {

	private Long produtoId;
	private Integer quantidade;
}
