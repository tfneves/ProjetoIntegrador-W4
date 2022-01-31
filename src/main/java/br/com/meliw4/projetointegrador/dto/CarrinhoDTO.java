package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.Carrinho;
import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.entity.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarrinhoDTO {

	private LocalDate data;
	private Long idComprador;
	private StatusPedidoDTO statusPedido;
	private List<ProdutoPedidoDTO> produtos;


	public static Carrinho parseToEntity(CarrinhoDTO dto) {
		return Carrinho.builder()
				.data(dto.getData())
				.statusPedido(StatusPedidoDTO.parseToEntity(dto.getStatusPedido()))
				.build();
	}
}
