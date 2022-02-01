package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProdutoDTO {

	@NotNull(message = "Id do produto inválido")
	private Long id;
	@NotEmpty(message = "Nome do produto inválido")
	private String nome;
	@NotNull(message = "Data de vencimento inválida")
	private LocalDate dataVencimento;
	@Digits(integer = 6, fraction = 2, message = "O valor inserido no campo volume não exceder 6 dígitos inteiros nem 2 dígitos decimais")
	@NotNull(message = "Volume inválido")
	private Double volume;
	@NotNull(message = "Quantidade Inicial inválida")
	private Integer quantidadeInicial;
	@NotNull(message = "Quantidade Atual inválida")
	private Integer quantidadeAtual;
	@NotNull(message = "Data de manufatura inválida")
	private LocalDate dataManufatura;
	@NotNull(message = "Tipo inválido")
	private ProdutoCategoria produtoCategoria;
	@Digits(integer = 3, fraction = 2, message = "Temperatura deve ser no formato XXX.XX")
	private Float temperaturaAtual;
	@NotNull(message = "Preço inválido")
	private BigDecimal preco;

	public static Produto convert(ProdutoDTO produtoDTO) {
		return Produto.builder()
			.nome(produtoDTO.getNome())
			.volume(produtoDTO.getVolume())
			.produtoCategoria(produtoDTO.getProdutoCategoria())
			.build();
	}

	public static ProdutoVendedor convert(ProdutoDTO produtoDTO, Vendedor vendedor, Produto produto, Lote lote) {
		return ProdutoVendedor.builder()
			.vendedor(vendedor)
			.produto(produto)
			.preco(produtoDTO.getPreco())
			.temperaturaAtual(produtoDTO.getTemperaturaAtual())
			.dataVencimento(produtoDTO.getDataVencimento())
			.dataManufatura(produtoDTO.getDataManufatura())
			.quantidadeInicial(produtoDTO.getQuantidadeInicial())
			.quantidadeAtual(produtoDTO.getQuantidadeAtual())
			.lote(lote)
			.build();
	}

}

