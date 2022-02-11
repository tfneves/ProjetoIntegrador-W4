package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SetorDTO {

	@NotNull(message = "Categoria inválida")
	private Categoria categoria;
	@NotNull(message = "Volume inválido")
	private Double volume;
	@NotNull(message = "Armazém inválido")
	private Long armazemId;

	public static Setor converte(SetorDTO payload, Armazem armazem) {
		return Setor.builder()
				.categoria(payload.categoria)
				.volume(payload.volume)
				.armazem(armazem)
				.build();
	}

	public static SetorDTO converte(Setor payload) {
		return SetorDTO.builder()
				.categoria(payload.getCategoria())
				.volume(payload.getVolume())
				.armazemId(payload.getArmazem().getId())
				.build();
	}

}
