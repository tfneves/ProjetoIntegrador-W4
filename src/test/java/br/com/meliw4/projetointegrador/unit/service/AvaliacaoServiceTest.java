package br.com.meliw4.projetointegrador.unit.service;

import br.com.meliw4.projetointegrador.dto.AvaliacaoDTO;
import br.com.meliw4.projetointegrador.dto.AvaliacaoUpdateDTO;
import br.com.meliw4.projetointegrador.entity.*;
import br.com.meliw4.projetointegrador.entity.enumeration.Estrelas;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.AvaliacaoRepository;
import br.com.meliw4.projetointegrador.service.AvaliacaoService;
import br.com.meliw4.projetointegrador.service.CompradorService;
import br.com.meliw4.projetointegrador.service.PedidoService;
import br.com.meliw4.projetointegrador.service.ProdutoVendedorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AvaliacaoServiceTest {

	private static AvaliacaoService avaliacaoService;
	private static AvaliacaoRepository avaliacaoRepository = Mockito.mock(AvaliacaoRepository.class);
	private static CompradorService compradorService = Mockito.mock(CompradorService.class);
	private static PedidoService pedidoService = Mockito.mock(PedidoService.class);
	private static ProdutoVendedorService produtoVendedorService = Mockito.mock(ProdutoVendedorService.class);

	@BeforeAll
	public static void setUP() {
		avaliacaoService = new AvaliacaoService(
			avaliacaoRepository,
			compradorService,
			pedidoService,
			produtoVendedorService
		);
	}

	@Test
	public void shouldCreateAvaliacao() {
		LocalDate today = LocalDate.now();
		List<ProdutoCarrinho> produtosCarrinho = new ArrayList<>();
		produtosCarrinho.add(ProdutoCarrinho.builder()
			.produto(
				ProdutoVendedor.builder().id(1l).build()
			)
			.build()
		);
		AvaliacaoDTO dto = AvaliacaoDTO.builder()
			.anuncioId(1l)
			.pedidoId(1l)
			.compradorId(1l)
			.comentario("TOP")
			.estrelas(Estrelas.CINCO)
			.build();
		Mockito.when(compradorService.findCompradorById(1l)).thenReturn(Comprador.builder().id(1l).build());
		Mockito.when(pedidoService.validatePedido(1l)).thenReturn(
			Carrinho.builder()
				.id(1l)
				.comprador(Comprador.builder().id(1l).build())
				.produtosCarrinho(produtosCarrinho)
				.build()
		);
		Mockito.when(produtoVendedorService.getProdutoById(1l)).thenReturn(ProdutoVendedor.builder().id(1l).build());
		Mockito.when(avaliacaoRepository.save(Mockito.any(Avaliacao.class))).thenReturn(
			Avaliacao.builder().id(1l).dataAvaliacao(today).build()
		);
		AvaliacaoDTO response = avaliacaoService.createAvaliacao(dto);
		assertEquals(response.getAvaliacaoId(), 1l);
		assertEquals(response.getDataAvaliacao(), today);
	}

	@Test
	public void shouldThrowAvaliacaoAlreadyExistsException() {
		AvaliacaoDTO dto = AvaliacaoDTO.builder()
			.anuncioId(1l)
			.pedidoId(1l)
			.compradorId(1l)
			.build();
		Mockito.when(avaliacaoRepository
			.findByCompradorIdAndPedidoIdAndAnuncioId(1l, 1l, 1l))
			.thenReturn(Avaliacao.builder().build());
		BusinessValidationException ex =
			assertThrows(
				BusinessValidationException.class,
				() -> avaliacaoService.createAvaliacao(dto)
			);
		assertEquals(ex.getMessage(), "Já existe uma avaliação feita.");
	}

	@Test
	public void shouldUpdateAvaliacao() {
		AvaliacaoUpdateDTO dto = AvaliacaoUpdateDTO.builder()
			.avaliacaoId(1l)
			.comentario("WOW")
			.build();
		Avaliacao avaliacao = Avaliacao.builder()
			.comentario("TOP")
			.comprador(Comprador.builder().build())
			.pedido(Carrinho.builder().build())
			.estrelas(ClassificacaoAvaliacao.builder().build())
			.anuncio(ProdutoVendedor.builder().build())
			.build();
		Mockito.when(avaliacaoRepository.findById(1l)).thenReturn(Optional.of(avaliacao));
		assertEquals(dto.getComentario(), avaliacaoService.updateAvaliacao(dto).getComentario());
	}

	@Test
	public void shouldDeleteAvaliacao() {
		Avaliacao avaliacao = Avaliacao.builder().id(1l).build();
		Mockito.when(avaliacaoRepository.findById(1l)).thenReturn(Optional.of(avaliacao));
		assertDoesNotThrow(
			() -> avaliacaoService.deleteAvaliacao(1l)
		);
	}

	@Test
	public void shouldReturnAvaliacao() {
		Avaliacao avaliacao = Avaliacao.builder()
			.id(1l)
			.comentario("TOP")
			.comprador(Comprador.builder().build())
			.pedido(Carrinho.builder().build())
			.estrelas(ClassificacaoAvaliacao.builder().build())
			.anuncio(ProdutoVendedor.builder().build())
			.build();
		Mockito.when(avaliacaoRepository.findById(1l)).thenReturn(Optional.of(avaliacao));
		assertDoesNotThrow(
			() -> avaliacaoService.getAvaliacao(1l)
		);
	}

	@Test
	public void shouldReturnAvaliacoesAnuncio() {
		Avaliacao avaliacaoTop = Avaliacao.builder()
			.id(1l)
			.comentario("TOP")
			.comprador(Comprador.builder().build())
			.pedido(Carrinho.builder().build())
			.estrelas(ClassificacaoAvaliacao.builder().build())
			.anuncio(ProdutoVendedor.builder().build())
			.build();
		Avaliacao avaliacaoWow = Avaliacao.builder()
			.id(1l)
			.comentario("WOW")
			.comprador(Comprador.builder().build())
			.pedido(Carrinho.builder().build())
			.estrelas(ClassificacaoAvaliacao.builder().build())
			.anuncio(ProdutoVendedor.builder().build())
			.build();
		List<Avaliacao> avaliacoes = new ArrayList<>();
		avaliacoes.add(avaliacaoTop);
		avaliacoes.add(avaliacaoWow);
		Mockito.when(avaliacaoRepository.findByAnuncioId(1l)).thenReturn(avaliacoes);
		List<AvaliacaoDTO> response = avaliacaoService.getAvaliacoesAnuncio(1l);
		assertEquals(response.size(), 2);
	}

	@Test
	public void shouldThrowInvalidCompradorPedidoAssociationException() {
		Comprador comprador = Comprador.builder().id(1l).build();
		Carrinho pedido = Carrinho.builder().comprador(Comprador.builder().id(2l).build()).build();
		ProdutoVendedor anuncio = ProdutoVendedor.builder().build();
		BusinessValidationException ex =
			assertThrows(
				BusinessValidationException.class,
				() -> avaliacaoService.validateAssociation(comprador, pedido, anuncio)
			);
		assertEquals(ex.getMessage(), "O comprador não está associado a esse pedido.");
	}

	@Test
	public void shouldThrowInvalidAnuncioPedidoAssociationException() {
		List<ProdutoCarrinho> produtoscarrinho = new ArrayList<>();
		produtoscarrinho.add(
			ProdutoCarrinho.builder()
				.produto(
					ProdutoVendedor.builder().id(2l).build()
				)
				.build()
		);
		Comprador comprador = Comprador.builder().id(1l).build();
		Carrinho pedido = Carrinho.builder()
			.produtosCarrinho(produtoscarrinho)
			.comprador(Comprador.builder().id(1l).build()).build();
		ProdutoVendedor anuncio = ProdutoVendedor.builder().id(1l).build();
		BusinessValidationException ex =
			assertThrows(
				BusinessValidationException.class,
				() -> avaliacaoService.validateAssociation(comprador, pedido, anuncio)
			);
		assertEquals(ex.getMessage(), "O anúncio não está vinculado a esse pedido.");
	}

	@Test
	public void shouldThrowInvalidAvaliacaoException() {
		Mockito.when(avaliacaoRepository.findById(1l)).thenReturn(Optional.empty());
		assertThrows(
			BusinessValidationException.class,
			() -> avaliacaoService.findAvaliacaoById(1l)
		);
	}
}
