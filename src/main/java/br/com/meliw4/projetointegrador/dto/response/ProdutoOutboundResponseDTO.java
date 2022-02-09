package br.com.meliw4.projetointegrador.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoOutboundResponseDTO {

	private Long vendedor;
	private Long produto;
	private Long lote;
	private Integer quantidade;
	private LocalDate dataValidade;

	public static ProdutoOutboundResponseDTO converte(ProdutoVendedorResponseDTO pv, Integer carrinho) {
		return ProdutoOutboundResponseDTO.builder()
			.vendedor(pv.getVendedor())
			.lote(pv.getLote())
			.produto(pv.getProduto())
			.dataValidade(pv.getDataValidade())
			.quantidade(carrinho)
			.build();
	}
}
