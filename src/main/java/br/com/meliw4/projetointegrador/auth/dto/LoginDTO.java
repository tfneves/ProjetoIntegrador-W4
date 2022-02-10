package br.com.meliw4.projetointegrador.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginDTO {
	@NotEmpty(message = "O campo login não pode ser vazio")
	@Size(max = 30, message = "O campo login não pode exceder 30 caracteres")
	private String login;
	@NotEmpty(message = "O campo senha não pode ser vazio")
	@Size(max = 30, message = "O campo senha não pode exceder 30 caracteres")
	private String senha;
}
