package br.com.meliw4.projetointegrador.response;

import br.com.meliw4.projetointegrador.entity.Carrinho;
import br.com.meliw4.projetointegrador.entity.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoResponse {

	private Long id;
	private LocalDate data;
	private Long compradorId;
	private StatusPedido statusPedido;

	public static CarrinhoResponse converte(Carrinho carrinho) {
			return CarrinhoResponse.builder()
				.id(carrinho.getId())
				.data(carrinho.getData())
				.compradorId(carrinho.getComprador().getId())
				.statusPedido(carrinho.getStatusPedido()).build();
	}

	public static List<CarrinhoResponse> converte(List<Carrinho> carrinhos) {
		List<CarrinhoResponse> carrinhoResponses = new ArrayList<>();
		for (Carrinho carrinho : carrinhos) {
			carrinhoResponses.add(CarrinhoResponse.builder()
				.id(carrinho.getId())
				.data(carrinho.getData())
				.compradorId(carrinho.getComprador().getId())
				.statusPedido(carrinho.getStatusPedido()).build());
		}
		return carrinhoResponses;
	}
}
