package br.com.meliw4.projetointegrador.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
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
	@JoinColumn(name = "comprador_id", referencedColumnName = "id")
	private Comprador comprador;
	@OneToOne
	@JoinColumn(name = "status_pedido_id", referencedColumnName = "id")
	private StatusPedido statusPedido;
	@OneToMany(mappedBy = "carrinho", fetch = FetchType.LAZY)
	List<ProdutoCarrinho> produtosCarrinho;

}
