package br.com.meliw4.projetointegrador.response;

import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoSetorResponse {

	private Long setor_id;
	private Long armazem_id;

	public static ProdutoSetorResponse retornaOSetor(ProdutoVendedor produtoVendedor){
		return ProdutoSetorResponse.builder()
			.setor_id(produtoVendedor.getLote().getSetor().getId())
			.armazem_id(produtoVendedor.getLote().getSetor().getArmazem().getId())
			.build();
	}

}
