package br.com.meliw4.projetointegrador.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArmazemProdutoResponseDTO {

	private Long produtoId;
	private List<ArmazemSelecaoResponseDTO> armazem;

	public static ArmazemProdutoResponseDTO toDTO(Long produtoId,
			List<ArmazemSelecaoResponseDTO> armazemSelecaoResponseDTOs) {
		return ArmazemProdutoResponseDTO.builder()
				.produtoId(produtoId)
				.armazem(armazemSelecaoResponseDTOs)
				.build();
	}
}
