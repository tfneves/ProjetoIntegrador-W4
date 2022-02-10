package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.Armazem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArmazemDTO {

	@NotEmpty(message = "O campo nome não pode ser vazio")
	@Size(max = 100, message = "O campo nome não pode exceder 100 caracteres")
	private String nome;
	@Digits(integer = 6, fraction = 2, message = "O valor inserido no campo volume não exceder 6 dígitos inteiros nem 2 dígitos decimais")
	private Double volume;

	public static ArmazemDTO parseToDTO(Armazem armazem) {
		return ArmazemDTO.builder()
				.nome(armazem.getNome())
				.volume(armazem.getVolume())
				.build();
	}

	public static Armazem parseToEntity(ArmazemDTO dto) {
		return Armazem.builder()
				.nome(dto.getNome())
				.volume(dto.getVolume())
				.build();
	}
}
