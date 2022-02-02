package br.com.meliw4.projetointegrador.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.meliw4.projetointegrador.dto.response.ArmazemProdutoResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.ArmazemSelecaoResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.ProdutoResponseDTO;
import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import br.com.meliw4.projetointegrador.exception.NotFoundException;
import br.com.meliw4.projetointegrador.repository.ProdutoRepository;
import br.com.meliw4.projetointegrador.repository.VendedorProdutoRepository;
import br.com.meliw4.projetointegrador.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	private static final NotFoundException NOT_FOUND_EXCEPTION = new NotFoundException(
			"Não há produtos para a seleção");

	private final ProdutoRepository produtoRepository;
	private final VendedorProdutoRepository vendedorProdutoRepository;

	ProdutoServiceImpl(ProdutoRepository produtoRepository, VendedorProdutoRepository vendedorProdutoRepository) {
		this.produtoRepository = produtoRepository;
		this.vendedorProdutoRepository = vendedorProdutoRepository;
	}

	@Override
	public List<ProdutoResponseDTO> findAllProdutos() {
		List<ProdutoResponseDTO> produtoResponseDTO = produtoRepository.findAll().stream()
				.map(ProdutoResponseDTO::toDTO)
				.collect(Collectors.toList());

		if (produtoResponseDTO.isEmpty()) {
			throw NOT_FOUND_EXCEPTION;
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
			throw NOT_FOUND_EXCEPTION;
		}

		return produtoResponseDTO;

	}

	@Override
	public ArmazemProdutoResponseDTO findArmazemPorProduto(Long produtoId) {
		List<ProdutoVendedor> produtoVendedores = vendedorProdutoRepository.findByProdutoId(produtoId);

		if (produtoVendedores.isEmpty()) {
			throw NOT_FOUND_EXCEPTION;
		}

		TreeMap<Long, Integer> mapper = produtoVendedores.stream()
				.collect(Collectors.toMap(
						// p -> p.getLote().getRepresentante().getId(),
						p -> p.getLote().getRepresentante().getArmazem().getId(),
						ProdutoVendedor::getQuantidadeAtual,
						(e1, e2) -> e1 + e2,
						TreeMap::new));

		List<ArmazemSelecaoResponseDTO> armazemSelecaoDTOs = new ArrayList<>();
		mapper.forEach((k, v) -> {
			armazemSelecaoDTOs.add(
					ArmazemSelecaoResponseDTO.builder()
							.armazemId(k)
							.quantidadeTotal(v)
							.build());
		});

		ArmazemProdutoResponseDTO arm = ArmazemProdutoResponseDTO.builder()
				.produtoId(produtoId)
				.armazem(armazemSelecaoDTOs)
				.build();

		return arm;
	}
}
