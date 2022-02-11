package br.com.meliw4.projetointegrador.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ProdutoUpdateDTO {

	@NotNull(message = "Id do produto inválido")
	private Long id;
	@NotNull(message = "Quantidade inválida.")
	@Min(value = 1, message = "Quantidade a retirar deve ser positiva.")
	private Integer quantidadeRetira;

}
