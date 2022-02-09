package br.com.meliw4.projetointegrador.dto;


import br.com.meliw4.projetointegrador.entity.Comprador;
import br.com.meliw4.projetointegrador.entity.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
	private LocalDate dataNascimento;
	@NotNull(message = "Endereco inválido")
	private Long endereco_id;
	@NotEmpty(message = "O campo login não pode ser vazio")
	@Size(max = 30, message = "O campo login não pode exceder 30 caracteres")
	private String login;
	@NotEmpty(message = "O campo senha não pode ser vazio")
	@Size(max = 30, message = "O campo senha não pode exceder 30 caracteres")
	private String senha;

	public static Comprador convert(CompradorDTO compradorDTO , Endereco endereco) {
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		return new Comprador(
			compradorDTO.login,
			bc.encode(compradorDTO.getSenha()),
			compradorDTO.getNome(),
			compradorDTO.getTelefone(),
			compradorDTO.getEmail(),
			compradorDTO.getDataNascimento(),
			endereco);
	}
}
