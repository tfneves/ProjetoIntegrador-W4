package br.com.meliw4.projetointegrador.entity;

import br.com.meliw4.projetointegrador.dto.StatusPedidoDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Carrinho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate data;
	@OneToOne
	@JoinColumn(name = "status_pedido_id", referencedColumnName = "id")
	private StatusPedido statusPedido;
}
