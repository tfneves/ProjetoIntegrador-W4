package br.com.meliw4.projetointegrador.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArmazemSelecaoResponseDTO {
	private Long armazemId;
	private Integer quantidadeTotal;

	public static ArmazemSelecaoResponseDTO toDTO(Long armazemId, Integer quantidadeTotal) {
		return ArmazemSelecaoResponseDTO.builder()
				.armazemId(armazemId)
				.quantidadeTotal(quantidadeTotal)
				.build();
	}
}
