package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.Vendedor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendedorDTO {
	@NotNull(message = "Nome não pode estar em branco")
	private Long id;

	@NotEmpty(message = "Nome não pode estar em branco")
	@Size(max = 100, message = "Nome não pode exceder 100 caracteres")
	private String nome;

	public static Vendedor convert(VendedorDTO vendedorDTO) {
		return Vendedor.builder()
				.id(vendedorDTO.getId())
				.nome(vendedorDTO.getNome())
				.build();
	}

}
