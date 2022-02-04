package br.com.meliw4.projetointegrador.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.entity.ProdutoCategoria;
import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProdutoResponseDTO {
	private Long id;
	private final String nome;
	private final Double volume;
	private final ProdutoCategoria produtoCategoria;
	private final List<Long> vendedoresId;

	public static ProdutoResponseDTO toDTO(Produto produto) {
		return ProdutoResponseDTO.builder()
				.id(produto.getId())
				.nome(produto.getNome())
				.volume(produto.getVolume())
				.produtoCategoria(produto.getProdutoCategoria())
				.vendedoresId(produto.getProdutoVendedores().stream().map(ProdutoVendedor::getId)
						.collect(Collectors.toList()))
				.build();
	}
}
