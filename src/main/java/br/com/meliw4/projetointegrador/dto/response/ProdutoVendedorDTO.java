package br.com.meliw4.projetointegrador.dto.response;

import br.com.meliw4.projetointegrador.entity.Lote;
import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoVendedorDTO {
	private Long id;
	private Long vendedor;
	private Long produto;
	private LocalDate dataVencimento;

	public static ProdutoVendedorDTO converte (ProdutoVendedor pv){
		return ProdutoVendedorDTO.builder()
			.id(pv.getId())
			.vendedor(pv.getVendedor().getId())
			.produto(pv.getProduto().getId())
			.dataVencimento(pv.getDataVencimento())
			.build();
	}
}
