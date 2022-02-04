package br.com.meliw4.projetointegrador.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import br.com.meliw4.projetointegrador.repository.ProdutoVendedorRepository;
import br.com.meliw4.projetointegrador.response.ProdutoSetorResponse;
import br.com.meliw4.projetointegrador.response.ProdutoVendedorResponse;
import br.com.meliw4.projetointegrador.entity.Produto;
import org.springframework.stereotype.Service;

import br.com.meliw4.projetointegrador.dto.response.ProdutoResponseDTO;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import br.com.meliw4.projetointegrador.exception.NotFoundException;
import br.com.meliw4.projetointegrador.repository.ProdutoRepository;
import br.com.meliw4.projetointegrador.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	private ProdutoRepository produtoRepository;
	private ProdutoVendedorRepository produtoVendedorRepository;

	ProdutoServiceImpl(ProdutoRepository produtoRepository, ProdutoVendedorRepository produtoVendedorRepository) {
		this.produtoRepository = produtoRepository;
		this.produtoVendedorRepository = produtoVendedorRepository;
	}

	@Override
	public List<ProdutoResponseDTO> findAllProdutos() {
		List<ProdutoResponseDTO> produtoResponseDTO = produtoRepository.findAll().stream()
			.map(ProdutoResponseDTO::toDTO)
			.collect(Collectors.toList());

		if (produtoResponseDTO.isEmpty()) {
			throw new NotFoundException("Não há produtos para a seleção");
		}

		return produtoResponseDTO;
	}

	@Override
	public List<ProdutoResponseDTO> findProdutoPorCategoria(Categoria categoria) {
		List<ProdutoResponseDTO> produtoResponseDTO = produtoRepository.findProdutoPorCategoria(categoria.name())
			.stream()
			.map(ProdutoResponseDTO::toDTO)
			.collect(Collectors.toList());

		if (produtoResponseDTO.isEmpty()) {
			throw new NotFoundException("Não há produtos para a seleção");
		}

		return produtoResponseDTO;
	}

	@Override
	public boolean validateProdutoExists(Long id) {
		if (!produtoRepository.existsById(id)) {
			return false;
		}
		return true;
	}

	@Override
	public void save(Produto produto) {
		produtoRepository.save(produto);
	}

	@Override
	public Produto getProdutoById(Long id) {
		return produtoRepository.findById(id).orElse(null);
	}

	public Map<ProdutoSetorResponse, List<ProdutoVendedorResponse>> listaTodosOsLotes(Long id, String type) {
		Map<ProdutoSetorResponse, List<ProdutoVendedorResponse>> response = sanetizaORetorno(this.produtoVendedorRepository.findProdutoVendedorByProduto_Id(id), type);
		return response;
	}

	private Map<ProdutoSetorResponse, List<ProdutoVendedorResponse>> sanetizaORetorno(List<ProdutoVendedor> produtoVendedor, String type) {
		Map<ProdutoSetorResponse, List<ProdutoVendedorResponse>> response = new LinkedHashMap<>();
		for (ProdutoVendedor pv : produtoVendedor) {
			response.put(ProdutoSetorResponse.retornaOSetor(pv),
				ordenaLista(ProdutoVendedorResponse.converte(produtoVendedor
					.stream()
					.filter(p -> p.getLote().getSetor().getId()	== pv.getLote().getSetor().getId())
					.collect(Collectors.toList())),
					type));

		}
		return response;
	}

	private List<ProdutoVendedorResponse> ordenaLista(List<ProdutoVendedorResponse> pv, String type) {
		switch(type) {
			case "L":
				return pv.stream().sorted(Comparator.comparing(ProdutoVendedorResponse::getLote_id)).collect(Collectors.toList());
			case "C":
				return pv.stream().sorted(Comparator.comparing(ProdutoVendedorResponse::getQuantidadeAtual)).collect(Collectors.toList());
			case "F":
				return pv.stream().sorted(Comparator.comparing(ProdutoVendedorResponse::getDataVencimento)).collect(Collectors.toList());
			default:
				return pv;
		}
	}
}
