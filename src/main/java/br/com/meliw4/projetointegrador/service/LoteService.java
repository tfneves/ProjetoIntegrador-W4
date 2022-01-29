package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.LoteDTO;
import br.com.meliw4.projetointegrador.dto.ProdutoDTO;
import br.com.meliw4.projetointegrador.entity.*;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class LoteService {

	private ArmazemRepository armazemRepository;
	private VendedorRepository vendedorRepository;
	private SetorRepository setorRepository;
	private RepresentanteRepository representanteRepository;
	private ProdutoRepository produtoRepository;
	private LoteRepository loteRepository;
	private RegistroLoteRepository registroLoteRepository;

	public LoteService(ArmazemRepository armazemRepository, VendedorRepository vendedorRepository, SetorRepository setorRepository, RepresentanteRepository representanteRepository, ProdutoRepository produtoRepository, LoteRepository loteRepository, RegistroLoteRepository registroLoteRepository) {
		this.armazemRepository = armazemRepository;
		this.vendedorRepository = vendedorRepository;
		this.setorRepository = setorRepository;
		this.representanteRepository = representanteRepository;
		this.produtoRepository = produtoRepository;
		this.loteRepository = loteRepository;
		this.registroLoteRepository = registroLoteRepository;
	}


	public void registerLote(LoteDTO loteDTO) {
		validateArmazem(loteDTO.getArmazemId());
		Vendedor vendedor = validateVendedor(loteDTO.getVendedorId());
		Representante representante = validateRepresentante(loteDTO.getRepresentanteId(), loteDTO.getArmazemId());
		Setor setor = validateSetor(loteDTO.getSetorId(), loteDTO.getProdutosDTO());
		Lote lote = LoteDTO.convert(loteDTO, setor, representante);
		saveLote(lote);
		saveProdutos(lote, vendedor, loteDTO.getProdutosDTO());
		createRegister(lote, representante, vendedor);
	}

	public void updateValidate(LoteDTO loteDTO) {
	}

	private void validateArmazem(Long armazemId) {
		if (!armazemRepository.existsById(armazemId)) {
			throw new BusinessValidationException("O armazém não existe.");
		}
	}

	private Vendedor validateVendedor(Long id) {
		if (!vendedorRepository.existsById(id)) {
			throw new BusinessValidationException("O vendedor não existe.");
		}
		return vendedorRepository.getById(id);
	}

	private Representante validateRepresentante(Long representanteId, Long armazemId) {
		if (!representanteRepository.existsById(representanteId)) {
			throw new BusinessValidationException("O representante não existe.");
		}
		Representante representante = representanteRepository.getById(representanteId);
		if (representante.getArmazem().getId() != armazemId) {
			throw new BusinessValidationException("O representante não está associado a esse armazém.");
		}
		return representante;
	}

	private Setor validateSetor(Long setorId, List<ProdutoDTO> produtosDTO) {
		if (!setorRepository.existsById(setorId)) {
			throw new BusinessValidationException("O setor não existe.");
		}
		Setor setor = setorRepository.getById(setorId);
		Double totalVolume = 0.0;
		for (ProdutoDTO produtoDTO : produtosDTO) {
			totalVolume += produtoDTO.getVolume();
			if (produtoDTO.getTipo() != setor.getCategoria()) {
				throw new BusinessValidationException("O setor não é adequado para o tipo de produto do lote.");
			}
		}
		if (totalVolume >= this.calculateRemainingSetorArea(setor)) {
			throw new BusinessValidationException("O volume restante do setor não comporta o volume lote.");
		}
		return setor;
	}

	private Double calculateRemainingSetorArea(Setor setor) {
		Double totalVolume = 0.0;
		// TODO Usar stream
		List<Lote> lotes = setor.getLotes();
		for (Lote lote : lotes) {
			for (Produto produto : lote.getProdutos()) {
				totalVolume += produto.getVolume() * produto.getQuantidadeAtual();
			}
		}
		return setor.getVolume() - totalVolume;
	}

	private void saveLote(Lote lote) {
		loteRepository.save(lote);
	}

	private void saveProdutos(Lote lote, Vendedor vendedor, List<ProdutoDTO> produtosDTO) {
		for (ProdutoDTO produtoDTO : produtosDTO) {
			Produto produto = ProdutoDTO.convert(produtoDTO, vendedor, lote);
			produtoRepository.save(produto);
			produtoDTO.setId(produto.getId());
		}
	}

	private void createRegister(Lote lote, Representante representante, Vendedor vendedor) {
		RegistroLote registroLote = RegistroLote.builder()
			.lote(lote)
			.representante(representante)
			.vendedor(vendedor)
			.build();
		registroLoteRepository.save(registroLote);
	}

}
