
package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.Lote;
import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import br.com.meliw4.projetointegrador.entity.enumeration.Tipo;
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
	private Tipo tipo;
	@Digits(integer = 3, fraction = 2, message = "Temperatura deve ser no formato XXX.XX")
	private Float temperaturaAtual;
	@Digits(integer = 3, fraction = 2, message = "Temperatura deve ser no formato XXX.XX")
	private Float temperaturaMinima;
	@NotNull(message = "Preço inválido")
	private BigDecimal preco;

	public static Produto convert(ProdutoDTO produtoDTO, Vendedor vendedor, Lote lote) {
		return Produto.builder()
			.dataManufatura(produtoDTO.getDataManufatura())
			.dataVencimento(produtoDTO.getDataVencimento())
			.quantidadeInicial(produtoDTO.getQuantidadeInicial())
			.quantidadeAtual(produtoDTO.getQuantidadeAtual())
			.vendedor(vendedor)
			.temperaturaAtual(produtoDTO.getTemperaturaAtual())
			.temperaturaMinima(produtoDTO.getTemperaturaMinima())
			.tipo(produtoDTO.getTipo())
			.lote(lote)
			.preco(produtoDTO.getPreco())
			.nome(produtoDTO.getNome())
			.volume(produtoDTO.getVolume())
			.build();
	}

	public static ProdutoDTO convert(Produto produto) {
		return ProdutoDTO.builder()
			.id(produto.getId())
			.dataManufatura(produto.getDataManufatura())
			.dataVencimento(produto.getDataVencimento())
			.quantidadeInicial(produto.getQuantidadeInicial())
			.quantidadeAtual(produto.getQuantidadeAtual())
			.temperaturaAtual(produto.getTemperaturaAtual())
			.temperaturaMinima(produto.getTemperaturaMinima())
			.tipo(produto.getTipo())
			.preco(produto.getPreco())
			.nome(produto.getNome())
			.volume(produto.getVolume())
			.build();
	}

}

