package br.com.meliw4.projetointegrador.dto.response;

import java.util.List;

import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.entity.ProdutoCategoria;
import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponseDTO {
	private Long id;
	private String nome;
	private Double volume;
	private ProdutoCategoria produtoCategoria;
	private List<ProdutoVendedor> vendedores;

	public static ProdutoResponseDTO toDTO(Produto produto) {
		return ProdutoResponseDTO.builder()
				.id(produto.getId())
				.nome(produto.getNome())
				.volume(produto.getVolume())
				.produtoCategoria(produto.getProdutoCategoria())
				.vendedores(produto.getProdutoVendedores())
				.build();
	}
}
