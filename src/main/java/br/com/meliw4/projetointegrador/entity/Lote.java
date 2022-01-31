
package br.com.meliw4.projetointegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
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
	private List<Produto> produtos;
    private final LocalDate dataAquisicao = LocalDate.now();

}
<<<<<<< HEAD


=======
>>>>>>> add495e876b2e6abcc94c0f1d939486c118a8ceb
