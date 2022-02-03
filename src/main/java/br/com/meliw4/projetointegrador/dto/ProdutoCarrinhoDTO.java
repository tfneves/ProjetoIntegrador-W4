package br.com.meliw4.projetointegrador.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoCarrinhoDTO {

	@NotNull(message = "O campo anuncioId não pode ser vazio")
	private Long anuncioId;
	@NotNull(message = "A quantidade de produtos não pode ser vazia")
	private Integer quantidade;
}
