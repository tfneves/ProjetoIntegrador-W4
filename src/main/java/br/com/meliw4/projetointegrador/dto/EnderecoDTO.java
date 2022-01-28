package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.Representante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTO {

	@NotEmpty(message = "Logradouro não pode estar em branco")
	@Size(max = 60, message = "Nome não pode exceder 60 caracteres")
	private String logradouro;
	private Integer numero;
	@NotEmpty(message = "Bairro não pode estar em branco")
	@Size(max = 60, message = "Nome não pode exceder 60 caracteres")
	private String bairro;
	@NotEmpty(message = "Cidade não pode estar em branco")
	@Size(max = 60, message = "Nome não pode exceder 60 caracteres")
	private String cidade;
	@NotEmpty(message = "Estado não pode estar em branco")
	@Size(max = 30, message = "Nome não pode exceder 30 caracteres")
	private String estado;


	public static Representante convert(RepresentanteDTO representanteDTO) {
		return Representante.builder()
			.nome(representanteDTO.getNome())
			.build();
	}
}
