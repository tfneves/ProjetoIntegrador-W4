package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Perfil;
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
import java.util.List;
import java.util.Set;

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
	@NotNull(message = "Senha não pode ser nula")
	private String senha;
	@NotNull(message = "Email não pode ser nulo")
	private String email;
	@NotNull(message = "Perfil não pode ser nulo")
	private List<Perfil> perfil;

	static BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

	public static Representante convert(RepresentanteDTO representanteDTO, Armazem armazem) {
		enconde(representanteDTO);
		return Representante.builder()
			.nome(representanteDTO.getNome())
			.armazem(armazem)
			.senha(representanteDTO.getSenha())
			.perfis(representanteDTO.getPerfil())
			.build();
	}

	private static void enconde(RepresentanteDTO dto) {
		dto.setSenha(bc.encode(dto.getSenha()));
	}
}
