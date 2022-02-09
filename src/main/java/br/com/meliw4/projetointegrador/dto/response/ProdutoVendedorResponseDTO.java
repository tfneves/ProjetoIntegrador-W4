package br.com.meliw4.projetointegrador.dto.response;

import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoVendedorResponseDTO {
	private Long vendedor;
	private Long produto;
	private Long lote;
	private LocalDate dataValidade;

	public static ProdutoVendedorResponseDTO converte (ProdutoVendedor pv){
		return ProdutoVendedorResponseDTO.builder()
			.vendedor(pv.getVendedor().getId())
			.produto(pv.getProduto().getId())
			.lote(pv.getLote().getId())
			.dataValidade(pv.getDataVencimento())
			.build();
	}
}
