package br.com.meliw4.projetointegrador.response;

import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoteProdutosVencimentoResponse {

	private Long loteId;
	private Long anuncioId;
	private Long produtoId;
	private Categoria categoriaProduto;
	private LocalDate dataVencimento;
	private Integer quantidade;
}
