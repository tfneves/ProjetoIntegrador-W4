package br.com.meliw4.projetointegrador.dto.response;

import java.time.LocalDate;

import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.entity.ProdutoCategoria;
import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProdutoResponseDTO {
	private Long id;
	private final LocalDate dataVencimento;
	private final Double volume;
	private final ProdutoCategoria produtoCategoria;

	public static ProdutoResponseDTO toDTO(Produto produto, ProdutoVendedor produtoVendedor) {
		return ProdutoResponseDTO.builder()
				.id(produto.getId())
				.dataVencimento(produtoVendedor.getDataVencimento())
				.volume(produto.getVolume())
				.produtoCategoria(produto.getProdutoCategoria())
				.build();
	}
}
