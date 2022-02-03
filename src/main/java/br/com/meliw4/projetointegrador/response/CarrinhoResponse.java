package br.com.meliw4.projetointegrador.response;

import br.com.meliw4.projetointegrador.entity.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoResponse {

	private Long id;
	private LocalDate data;
	private Long compradorId;
	private StatusPedido statusPedido;
}
