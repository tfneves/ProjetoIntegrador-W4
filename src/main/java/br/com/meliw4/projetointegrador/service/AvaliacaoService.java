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

import java.util.ArrayList;
import java.util.List;

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
		Avaliacao avaliacao = getAnuncioByAssociations(dto.getCompradorId(), dto.getPedidoId(), dto.getAnuncioId());
		if (avaliacao != null) {
			throw new BusinessValidationException("Já existe uma avaliação feita.");
		}
		validateAssociation(comprador, pedido, anuncio);
		Avaliacao avalicao = avaliacaoRepository.save(AvaliacaoDTO.convert(dto, anuncio, comprador, pedido));
		dto.setAvaliacaoId(avalicao.getId());
		dto.setDataAvaliacao(avalicao.getDataAvaliacao());
		return dto;
	}

	public void deleteAvaliacao(Long id) {
		Avaliacao avaliacao = findAvaliacaoById(id);
		avaliacaoRepository.delete(avaliacao);
	}

	public AvaliacaoDTO getAvaliacao(Long id) {
		Avaliacao avaliacao = findAvaliacaoById(id);
		return AvaliacaoDTO.convert(avaliacao);
	}

	public List<AvaliacaoDTO> getAvaliacoesAnuncio(Long anuncioId) {
		produtoVendedorService.getProdutoById(anuncioId);
		List<AvaliacaoDTO> avaliacoesDTO = new ArrayList<>();
		List<Avaliacao> avaliacoes = avaliacaoRepository.findByAnuncioId(anuncioId);
		for (Avaliacao avaliacao : avaliacoes) {
			avaliacoesDTO.add(AvaliacaoDTO.convert(avaliacao));
		}
		return avaliacoesDTO;
	}

	public Avaliacao getAnuncioByAssociations(Long compradorId, Long pedidoId, Long anuncioId) {
		return avaliacaoRepository.findByCompradorIdAndPedidoIdAndAnuncioId(compradorId, pedidoId, anuncioId);
	}

	public void validateAssociation(Comprador comprador, Carrinho pedido, ProdutoVendedor anuncio) {
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


	private Avaliacao findAvaliacaoById(Long id) {
		return avaliacaoRepository.findById(id).orElseThrow(() -> new BusinessValidationException("Não existe " +
			"avaliação com o id " + id + ".")
		);
	}

}
