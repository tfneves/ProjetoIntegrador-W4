package br.com.meliw4.projetointegrador.dto;

import br.com.meliw4.projetointegrador.entity.*;
import br.com.meliw4.projetointegrador.entity.enumeration.Estrelas;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvaliacaoDTO {

	private Long avaliacaoId;
	private LocalDate dataAvaliacao;
	@NotNull(message = "Id do comprador inválido.")
	private Long compradorId;
	@NotNull(message = "Id do pedido inválido.")
	private Long pedidoId;
	@NotNull(message = "Id do anuncio inválido.")
	private Long anuncioId;
	@NotNull(message = "Classificação inválida.")
	private Estrelas estrelas;
	@NotEmpty(message = "Comentário inválido.")
	private String comentario;

	public static Avaliacao convert(AvaliacaoDTO dto, ProdutoVendedor anuncio, Comprador comprador, Carrinho pedido) {
		return Avaliacao.builder()
			.comentario(dto.comentario)
			.estrelas(ClassificacaoAvaliacao.builder().classificacao(dto.estrelas).build())
			.dataAvaliacao(LocalDate.now())
			.anuncio(anuncio)
			.comprador(comprador)
			.pedido(pedido)
			.build();
	}

	public static AvaliacaoDTO convert(Avaliacao avaliacao) {
		return AvaliacaoDTO.builder()
			.avaliacaoId(avaliacao.getId())
			.dataAvaliacao(avaliacao.getDataAvaliacao())
			.compradorId(avaliacao.getComprador().getId())
			.pedidoId(avaliacao.getPedido().getId())
			.anuncioId(avaliacao.getAnuncio().getId())
			.estrelas(avaliacao.getEstrelas().getClassificacao())
			.comentario(avaliacao.getComentario())
			.build();
	}
}
