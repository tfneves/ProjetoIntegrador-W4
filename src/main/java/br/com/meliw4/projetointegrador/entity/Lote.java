
package br.com.meliw4.projetointegrador.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Lote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	private Setor setor;

	@ManyToOne
	private Representante representante;

	@OneToMany(mappedBy = "lote", fetch = FetchType.LAZY)
	private List<ProdutoVendedor> produtoVendedores;

	private final LocalDate dataAquisicao = LocalDate.now();
}
