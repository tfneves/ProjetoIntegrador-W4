package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.AvaliacaoDTO;
import br.com.meliw4.projetointegrador.dto.AvaliacaoUpdateDTO;
import br.com.meliw4.projetointegrador.entity.Avaliacao;
import br.com.meliw4.projetointegrador.entity.Carrinho;
import br.com.meliw4.projetointegrador.entity.Comprador;
import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.AvaliacaoRepository;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {

	private AvaliacaoRepository avaliacaoRepository;
	private CompradorService compradorService;
	private PedidoService pedidoService;
	private ProdutoVendedorService produtoVendedorService;

	public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, CompradorService compradorService,
							PedidoService pedidoService, ProdutoVendedorService produtoVendedorService) {
		this.avaliacaoRepository = avaliacaoRepository;
		this.compradorService = compradorService;
		this.pedidoService = pedidoService;
		this.produtoVendedorService = produtoVendedorService;
	}

	public AvaliacaoDTO createAvaliacao(AvaliacaoDTO dto) {
		Comprador comprador = compradorService.findCompradorById(dto.getCompradorId());
		Carrinho pedido = pedidoService.validatePedido(dto.getPedidoId());
		ProdutoVendedor anuncio = produtoVendedorService.getProdutoById(dto.getAnuncioId());
		validateAssociation(comprador, pedido, anuncio);
		Avaliacao avalicao = avaliacaoRepository.save(AvaliacaoDTO.convert(dto, anuncio, comprador));
		dto.setAvaliacaoId(avalicao.getId());
		dto.setDataAvaliacao(avalicao.getDataAvaliacao());
		return dto;
	}

	private void validateAssociation(Comprador comprador, Carrinho pedido, ProdutoVendedor anuncio) {
		if (!comprador.getId().equals(pedido.getComprador().getId())) {
			throw new BusinessValidationException("O comprador não está associado a esse pedido.");
		}
		pedido.getProdutosCarrinho().stream()
			.filter(p -> p.getProduto().getId().equals(anuncio.getId()))
			.findAny()
			.orElseThrow(() -> new BusinessValidationException("O anúncio não está vinculado a esse pedido."));
	}

	public Object updateAvaliacao(AvaliacaoUpdateDTO dto) {
		return null;
	}

	public Object deleteAvaliacao(Long id) {
		return null;
	}

	public Object getAvaliacao(Long id) {
		return null;
	}

	public Object getAvaliacoesAnuncio(Long id) {
		return null;
	}
}
