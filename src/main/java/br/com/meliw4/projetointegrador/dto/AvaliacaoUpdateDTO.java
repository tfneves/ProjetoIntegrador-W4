package br.com.meliw4.projetointegrador.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvaliacaoUpdateDTO {

	@NotNull(message = "Id da avaliação inválido.")
	private Long avaliacaoId;
	@NotEmpty(message = "Comentário inválido.")
	private String comentario;
}
