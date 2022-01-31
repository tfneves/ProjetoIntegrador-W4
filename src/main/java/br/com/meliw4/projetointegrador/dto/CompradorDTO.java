package br.com.meliw4.projetointegrador.dto;


import br.com.meliw4.projetointegrador.entity.Comprador;
import br.com.meliw4.projetointegrador.entity.Endereco;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompradorDTO {

	@NotEmpty(message = "Nome não pode estar em branco")
	@Size(max = 100, message = "Nome não pode exceder 100 caracteres")
	private String nome;
	@NotEmpty(message = "Telefone não pode estar em branco")
	@Size(max = 14, message = "Nome não pode exceder 14 caracteres")
	private String telefone;
	@NotEmpty(message = "Email não pode estar em branco")
	@Size(max = 20, message = "Nome não pode exceder 20 caracteres")
	private String email;
	@NotEmpty(message = "Data de Nascimento não pode estar em branco")
	@Size(max = 10, message = "Nome não pode exceder 10 caracteres")
	private String dataNascimento;
	@NotEmpty(message = "Codigo Endereco não pode estar em branco")
	private Endereco endereco;

	public static Comprador convert(CompradorDTO compradorDTO) {
		return Comprador.builder()
			.nome(compradorDTO.getNome())
			.telefone(compradorDTO.telefone)
			.email(compradorDTO.getTelefone())
			.dataNascimento(compradorDTO.getDataNascimento())
			.build();
	}
}
