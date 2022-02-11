package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.Comprador;
import br.com.meliw4.projetointegrador.entity.Endereco;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompradorDTO {

	@NotNull
	private Long id;
	@NotEmpty(message = "Nome não pode estar em branco")
	@Size(max = 100, message = "Nome não pode exceder 100 caracteres")
	private String nome;
	@NotEmpty(message = "Telefone não pode estar em branco")
	@Size(max = 14, message = "Nome não pode exceder 14 caracteres")
	private String telefone;
	@NotEmpty(message = "Email não pode estar em branco")
	@Size(max = 20, message = "Nome não pode exceder 20 caracteres")
	private String email;
	private LocalDate dataNascimento;
	@NotNull(message = "Endereco inválido")
	private Long endereco_id;

	public static Comprador convert(CompradorDTO compradorDTO, Endereco endereco) {
		return Comprador.builder()
				.id(compradorDTO.getId())
				.nome(compradorDTO.getNome())
				.telefone(compradorDTO.telefone)
				.email(compradorDTO.getTelefone())
				.dataNascimento(compradorDTO.getDataNascimento())
				.endereco(endereco)
				.build();
	}
}
