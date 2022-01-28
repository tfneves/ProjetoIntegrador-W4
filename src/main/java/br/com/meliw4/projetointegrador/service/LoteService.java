package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.LoteDTO;
import br.com.meliw4.projetointegrador.dto.ProdutoDTO;
import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Lote;
import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LoteService {

	private ArmazemRepository armazemRepository;
	private VendedorRepository vendedorRepository;
	private SetorRepository setorRepository;
	private RepresentanteRepository representanteRepository;
	private ProdutoRepository produtoRepository;
	private LoteRepository loteRepository;

	public LoteService(ArmazemRepository armazemRepository, VendedorRepository vendedorRepository, SetorRepository setorRepository, RepresentanteRepository representanteRepository, ProdutoRepository produtoRepository, LoteRepository loteRepository) {
		this.armazemRepository = armazemRepository;
		this.vendedorRepository = vendedorRepository;
		this.setorRepository = setorRepository;
		this.representanteRepository = representanteRepository;
		this.produtoRepository = produtoRepository;
		this.loteRepository = loteRepository;
	}

	// Validar espaço disponível no setor

	// Registrar lote no setor
	// Criar registro de compra


	public void registerValidate(LoteDTO loteDTO) {
		validateArmazem(loteDTO.getArmazemId());
		validateVendedor(loteDTO.getVendedorId());
		validateRepresentante(loteDTO.getRepresentanteId(), loteDTO.getArmazemId());
		validateSetor(loteDTO.getSetorId(), loteDTO.getProdutosDTO());
	}

	public void updateValidate(LoteDTO loteDTO) {
		validateArmazem(loteDTO.getArmazemId());
		validateVendedor(loteDTO.getVendedorId());
		validateRepresentante(loteDTO.getRepresentanteId(), loteDTO.getArmazemId());
		validateSetor(loteDTO.getSetorId(), loteDTO.getProdutosDTO());
	}

	private void validateArmazem(Long armazemId) {
		if (!armazemRepository.existsById(armazemId)) {
			throw new BusinessValidationException("O armazém não existe.");
		}
	}

	private void validateVendedor(Long id) {
		if (!vendedorRepository.existsById(id)) {
			throw new BusinessValidationException("O vendedor não existe.");
		}
	}

	private void validateRepresentante(Long representanteId, Long armazemId) {
		if (!representanteRepository.existsById(representanteId)) {
			throw new BusinessValidationException("O representante não existe.");
		}
		Representante representante = representanteRepository.getById(representanteId);
		if (representante.getArmazem().getId() != armazemId) {
			throw new BusinessValidationException("O representante não está associado a esse armazém.");
		}
	}

	private void validateSetor(Long setorId, List<ProdutoDTO> produtosDTO) {
		if (!setorRepository.existsById(setorId)) {
			throw new BusinessValidationException("O setor não existe.");
		}
		Setor setor = setorRepository.getById(setorId);
		Double totalVolume = 0.0;
		for (ProdutoDTO produtoDTO : produtosDTO) {
			totalVolume += produtoDTO.getVolume();
			if (produtoDTO.getTipo().name() != setor.getCategoria()) {
				throw new BusinessValidationException("O setor não é adequado para o tipo de produto do lote.");
			}
		}
	}

	private void saveLote(Lote lote) {
	}

	private void createRegister(LoteDTO loteDTO) {
	}

}
