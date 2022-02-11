package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusPedidoDTO {

	@NotEmpty(message = "O campo nome não pode ser vazio")
	@Size(max = 100, message = "O campo nome não pode exceder 100 caracteres")
	private String statusCode;

	public static StatusPedido parseToEntity(StatusPedidoDTO dto) {
		return StatusPedido.builder()
				.statusCode(dto.getStatusCode())
				.build();
	}
}
