package br.com.meliw4.projetointegrador.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProdutoVendedorResponse {

	//private ProdutoSetorResponse setorResponse;
	//private Long id;
	private Long produto_id;
	private String produto_nome;
	private LocalDate dataVencimento;
	private String nome_vendedor;
	private int quantidadeAtual;

}

