package br.com.meliw4.projetointegrador.response;

import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoteProdutosVencimentoResponse {

	private Long setorId;
	private Long loteId;
	private Long anuncioId;
	private Long produtoId;
	private Categoria categoriaProduto;
	private LocalDate dataVencimento;
	private Integer quantidade;
}
