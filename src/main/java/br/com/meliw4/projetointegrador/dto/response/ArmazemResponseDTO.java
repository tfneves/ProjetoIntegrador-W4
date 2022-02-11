package br.com.meliw4.projetointegrador.dto.response;

import br.com.meliw4.projetointegrador.entity.Armazem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArmazemResponseDTO {

	private String nome;
	private Double volume;
	private String endereco;
	private Double distancia;

	public static ArmazemResponseDTO toDTO(Armazem armazem, Double distancia, String endereco) {
		return ArmazemResponseDTO.builder()
				.nome(armazem.getNome())
				.volume(armazem.getVolume())
				.endereco(endereco)
				.distancia(distancia)
				.build();
	}

}
