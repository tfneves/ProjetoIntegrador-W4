package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.repository.ArmazemRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepresentanteDTO {

	@NotEmpty(message = "Nome não pode estar em branco")
	@Size(max = 100, message = "Nome não pode exceder 100 caracteres")
	private String nome;
	@NotNull(message = "Armazém inválido")
	private Long armazem_id;

	@Autowired
	ArmazemRepository armazemRepository;

	public Representante convert(RepresentanteDTO payload) {
		Armazem armazem = armazemRepository.findById(payload.armazem_id).get();
		return Representante.builder()
			   .nome(payload.getNome())
				.armazem(armazem)
			   .build();
	}
}
