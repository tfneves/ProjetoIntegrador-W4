package br.com.meliw4.projetointegrador.response;

import br.com.meliw4.projetointegrador.entity.Lote;
import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProdutoVendedorResponse {

	private Long id;
	private Vendedor vendedor;
	private Produto produto;
	private BigDecimal preco;
	private Float temperaturaAtual;
	private LocalDate dataVencimento;
	private LocalDate dataManufatura;
	private LocalDateTime dataStampManufatura;
	private int quantidadeInicial;
	private int quantidadeAtual;
	private Long lote;

}
