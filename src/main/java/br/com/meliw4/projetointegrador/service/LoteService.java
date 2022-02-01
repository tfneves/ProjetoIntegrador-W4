package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.Lote;


import br.com.meliw4.projetointegrador.dto.LoteDTO;
import br.com.meliw4.projetointegrador.dto.LoteUpdateDTO;
import br.com.meliw4.projetointegrador.dto.ProdutoDTO;
import br.com.meliw4.projetointegrador.dto.ProdutoUpdateDTO;
import br.com.meliw4.projetointegrador.entity.*;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.*;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
	private ProdutoCategoriaRepository produtoCategoriaRepository;
	private ProdutoVendedorRepository produtoVendedorRepository;

	public LoteService(ArmazemRepository armazemRepository, VendedorRepository vendedorRepository,
					   SetorRepository setorRepository, RepresentanteRepository representanteRepository,
					   ProdutoRepository produtoRepository, LoteRepository loteRepository,
					   RegistroLoteRepository registroLoteRepository, ProdutoCategoriaRepository produtoCategoriaRepository,
					   ProdutoVendedorRepository produtoVendedorRepository) {
		this.armazemRepository = armazemRepository;
		this.vendedorRepository = vendedorRepository;
		this.setorRepository = setorRepository;
		this.representanteRepository = representanteRepository;
		this.produtoRepository = produtoRepository;
		this.loteRepository = loteRepository;
		this.registroLoteRepository = registroLoteRepository;
		this.produtoCategoriaRepository = produtoCategoriaRepository;
		this.produtoVendedorRepository = produtoVendedorRepository;
	}

	public void registerLote(LoteDTO loteDTO) {
		validateArmazem(loteDTO.getArmazemId());
		Vendedor vendedor = validateVendedor(loteDTO.getVendedorId());
		Representante representante = validateRepresentante(loteDTO.getRepresentanteId(), loteDTO.getArmazemId());
		validateProdutosDTO(loteDTO.getProdutosDTO());
		Setor setor = validateSetor(loteDTO.getSetorId(), loteDTO.getProdutosDTO());
		Lote lote = LoteDTO.convert(loteDTO, setor, representante);
		saveLote(lote);
		createRegister(lote, representante, vendedor);
		saveProdutos(lote, loteDTO.getProdutosDTO(), vendedor);
	}

	private List<Produto> validateProdutosDTO(List<ProdutoDTO> produtosDTO) {
		List<Produto> produtos = new ArrayList<>();
		// TODO Usar stream
		for (ProdutoDTO produtoDTO : produtosDTO) {
			if (!produtoRepository.existsById(produtoDTO.getId())) {
				Produto produto = ProdutoDTO.convert(produtoDTO);
				produtoRepository.save(produto);
				produtos.add(produto);
				produtoDTO.setId(produto.getId());
			} else {
				produtos.add(produtoRepository.getById(produtoDTO.getId()));
			}
		}
		return produtos;
	}

	public List<ProdutoDTO> updateLote(LoteUpdateDTO loteUpdateDTO) {
		validateLote(loteUpdateDTO.getLoteId());
		validateProdutos(loteUpdateDTO.getProdutosUpdateDTO());
		return updateLoteProdutos(loteUpdateDTO.getProdutosUpdateDTO());
	}

	private List<ProdutoDTO> updateLoteProdutos(List<ProdutoUpdateDTO> produtosUpdateDTO) {
		// TODO Usar stream
		List<ProdutoDTO> produtosDTO = new ArrayList<>();
		List<Produto> produtos = new ArrayList<>();
		for (ProdutoUpdateDTO produtoUpdateDTO : produtosUpdateDTO) {
			Produto produto = produtoRepository.getById(produtoUpdateDTO.getId());
			// Integer quantidadeAtual = produto.getQuantidadeAtual();
			// Integer quantidadeAtual = produtoVendedor.getQuantidadeAtual();
			Integer quantidadeRetira = produtoUpdateDTO.getQuantidadeRetira();
			// if (quantidadeAtual < quantidadeRetira) {
			// throw new BusinessValidationException(
			// "A quantidade a retirar não deve exceder a quantidade atual de um produto.");
			// }
			// produto.setQuantidadeAtual(quantidadeAtual - quantidadeRetira);
			produtos.add(produto);
		}
		for (Produto produto : produtos) {
			produtoRepository.save(produto);
			// produtosDTO.add(ProdutoDTO.convert(produto, produtoVendedor));
		}
		return produtosDTO;
	}

	private void validateProdutos(List<ProdutoUpdateDTO> produtosUpdateDTO) {
		// TODO Usar stream
		for (ProdutoUpdateDTO produtoUpdateDTO : produtosUpdateDTO) {
			if (!produtoRepository.existsById(produtoUpdateDTO.getId())) {
				throw new BusinessValidationException("O produto de id " + produtoUpdateDTO.getId() + " não existe.");
			}
		}
	}

	private void validateLote(Long loteId) {
		if (!loteRepository.existsById(loteId)) {
			throw new BusinessValidationException("O lote não existe.");
		}
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
			if (produtoDTO.getProdutoCategoria().getCategoria() != setor.getCategoria()) {
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
			for (ProdutoVendedor produtoVendedor : lote.getProdutoVendedores()) {
				totalVolume += produtoVendedor.getProduto().getVolume() * produtoVendedor.getQuantidadeAtual();
			}
		}
		return setor.getVolume() - totalVolume;
	}

	private void saveLote(Lote lote) {
		loteRepository.save(lote);
	}

	private void saveProdutos(Lote lote, List<ProdutoDTO> produtosDTO, Vendedor vendedor) {
		for (ProdutoDTO produtoDTO : produtosDTO) {
			validatePreco(produtoDTO.getPreco());
			ProdutoVendedor produtoVendedor = ProdutoDTO
				.convert(produtoDTO, vendedor, produtoRepository.getById(produtoDTO.getId()), lote);
			produtoVendedorRepository.save(produtoVendedor);
		}
	}

	private void validatePreco(BigDecimal preco) {
		if (BigDecimal.ZERO.compareTo(preco) >= 0) {
			throw new BusinessValidationException("Preço deve ser positivo.");
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


