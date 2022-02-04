package br.com.meliw4.projetointegrador.response;

import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProdutoVendedorResponse {

	private Long lote_id;
	private Long produto_id;
	private String produto_nome;
	private LocalDate dataVencimento;
	private String nome_vendedor;
	private int quantidadeAtual;

	public static List<ProdutoVendedorResponse> converte(List<ProdutoVendedor> produtoVendedor){
		List<ProdutoVendedorResponse> response = new ArrayList<>();
		for (ProdutoVendedor pv : produtoVendedor) {
			response.add(ProdutoVendedorResponse.builder()
				.produto_id(pv.getProduto().getId())
				.produto_nome(pv.getProduto().getNome())
				.dataVencimento(pv.getDataVencimento())
				.nome_vendedor(pv.getVendedor().getNome())
				.lote_id(pv.getLote().getId())
				.quantidadeAtual(pv.getQuantidadeAtual())
				.build());
		}
		return response;
	}

}

