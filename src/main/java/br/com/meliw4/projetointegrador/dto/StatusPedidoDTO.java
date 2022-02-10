package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusPedidoDTO {

	@NotEmpty(message = "O campo nome não pode ser vazio")
	@Size(max = 100, message = "O campo nome não pode exceder 100 caracteres")
	private String statusCode;
	@NotEmpty(message = "O campo isDisposable não pode ser vazio")
	private Boolean isDisposable;

	public static StatusPedido parseToEntity(StatusPedidoDTO dto) {
		return StatusPedido.builder()
				.statusCode(dto.getStatusCode())
				.isDisposable(dto.getIsDisposable())
				.build();
	}
}
