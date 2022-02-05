package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.repository.ArmazemRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
	@NotNull(message = "Senha não pode ser nula")
	private String senha;
	@NotNull(message = "Email não pode ser nulo")
	private String email;

	static BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

	public static Representante convert(RepresentanteDTO representanteDTO, Armazem armazem) {
		enconde(representanteDTO);
		return Representante.builder()
			.nome(representanteDTO.getNome())
			.armazem(armazem)
			.senha(representanteDTO.getSenha())
			.build();
	}

	private static void enconde(RepresentanteDTO dto) {
		dto.setSenha(bc.encode(dto.getSenha()));
	}
}
