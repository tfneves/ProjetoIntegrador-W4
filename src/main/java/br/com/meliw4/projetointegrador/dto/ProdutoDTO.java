package br.com.meliw4.projetointegrador.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.entity.ProdutoCategoria;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author: André Arroxellas
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProdutoDTO {

	@NotEmpty
	private LocalDate dataVencimento;
	@NotEmpty
	private Integer volume;
	@NotEmpty
	private int quantidadeInicial; // TODO: Integer for null
	@NotEmpty
	private int quantidadeAtual; // TODO: Integer for nul
	@NotEmpty
	private LocalDate dataManufatura;
	@NotEmpty
	private LocalDateTime dataStampManufatura;
	@NotEmpty
	private Float temperaturaAtual;
	private Vendedor vendedor;
	private ProdutoCategoria produtoCategoria;

	public static ProdutoDTO convert(Produto produto) {
		return ProdutoDTO.builder()
				.dataManufatura(produto.getDataManufatura())
				.dataStampManufatura(produto.getDataStampManufatura())
				.dataVencimento(produto.getDataVencimento())
				.produtoCategoria(produto.getProdutoCategoria())
				.quantidadeAtual(produto.getQuantidadeAtual())
				.quantidadeInicial(produto.getQuantidadeInicial())
				.volume(produto.getVolume())
				.produtoCategoria(produto.getProdutoCategoria())
				.build();
	}

	public static Produto convert(ProdutoDTO produtoDTO) {
		return Produto.builder()
				.dataManufatura(produtoDTO.getDataManufatura())
				.dataStampManufatura(produtoDTO.getDataStampManufatura())
				.dataVencimento(produtoDTO.getDataVencimento())
				.produtoCategoria(produtoDTO.getProdutoCategoria())
				.quantidadeAtual(produtoDTO.getQuantidadeAtual())
				.quantidadeInicial(produtoDTO.getQuantidadeInicial())
				.volume(produtoDTO.getVolume())
				.produtoCategoria(produtoDTO.getProdutoCategoria())
				.build();
	}
}
