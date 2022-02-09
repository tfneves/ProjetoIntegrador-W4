package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Representante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class RepresentanteDTO {

	@NotEmpty(message = "Nome não pode estar em branco")
	@Size(max = 100, message = "Nome não pode exceder 100 caracteres")
	private String nome;
	@NotNull(message = "Armazém inválido")
	private Long armazem_id;
	@NotEmpty(message = "O campo login não pode ser vazio")
	@Size(max = 30, message = "O campo login não pode exceder 30 caracteres")
	private String login;
	@NotEmpty(message = "O campo senha não pode ser vazio")
	@Size(max = 30, message = "O campo senha não pode exceder 30 caracteres")
	private String senha;

	public static Representante convert(RepresentanteDTO representanteDTO ,Armazem armazem) {
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		return new Representante(representanteDTO.login, bc.encode(representanteDTO.senha), representanteDTO.nome, armazem);
	}
}
