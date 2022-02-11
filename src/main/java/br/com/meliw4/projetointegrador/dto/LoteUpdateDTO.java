package br.com.meliw4.projetointegrador.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Component
public class LoteUpdateDTO {

	@NotNull(message = "Id do lote inválido")
	private Long loteId;
	@NotEmpty(message = "A lista de produtosUpdateDTO não pode estar vazia.")
	private List<@Valid ProdutoUpdateDTO> produtosUpdateDTO;

}
