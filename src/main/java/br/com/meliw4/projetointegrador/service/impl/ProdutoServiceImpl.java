package br.com.meliw4.projetointegrador.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import br.com.meliw4.projetointegrador.response.ProdutoSetorResponse;
import br.com.meliw4.projetointegrador.response.ProdutoVendedorResponse;
import br.com.meliw4.projetointegrador.entity.Produto;
import org.springframework.stereotype.Service;

import br.com.meliw4.projetointegrador.dto.response.ArmazemProdutoResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.ArmazemSelecaoResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.ProdutoResponseDTO;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import br.com.meliw4.projetointegrador.exception.NotFoundException;
import br.com.meliw4.projetointegrador.repository.ProdutoRepository;
import br.com.meliw4.projetointegrador.service.ProdutoService;
import br.com.meliw4.projetointegrador.service.ProdutoVendedorService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	private ProdutoRepository produtoRepository;
	private ProdutoVendedorService produtoVendedorService;

	private static final NotFoundException NOT_FOUND_EXCEPTION = new NotFoundException(
			"Não há produtos para a seleção");

	public ProdutoServiceImpl(
			ProdutoRepository produtoRepository, ProdutoVendedorService produtoVendedorService) {
		this.produtoRepository = produtoRepository;
		this.produtoVendedorService = produtoVendedorService;
	}

	@Override
	public Produto findById(Long produtoId) {
		return produtoRepository.findById(produtoId)
				.orElseThrow(() -> NOT_FOUND_EXCEPTION);
	}

	@Override
	public void save(Produto produto) {
		produtoRepository.save(produto);
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
		return produtoRepository.findProdutoPorCategoria(categoria.name())
				.orElseThrow(() -> NOT_FOUND_EXCEPTION)
				.stream()
				.map(ProdutoResponseDTO::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	public boolean validateProdutoExists(Long id) {
		boolean teste = produtoRepository.existsById(id);
		if (!teste) {
			return false;
		}
		return true;
	}

	@Override
	public Map<ProdutoSetorResponse, List<ProdutoVendedorResponse>> listaTodosOsLotes(Long id, String type) {
		List<ProdutoVendedor> pv = this.produtoVendedorService.findProdutoVendedorByProduto_Id(id);
		if (pv.size() == 0)
			throw new NotFoundException(String.format("O Id %d não foi localziado", id));
		type = type.toUpperCase(Locale.ROOT);
		Map<ProdutoSetorResponse, List<ProdutoVendedorResponse>> response = sanetizaORetorno(pv, type);
		return response;
	}

	private Map<ProdutoSetorResponse, List<ProdutoVendedorResponse>> sanetizaORetorno(
			List<ProdutoVendedor> produtoVendedor, String type) {
		Map<ProdutoSetorResponse, List<ProdutoVendedorResponse>> response = new LinkedHashMap<>();
		for (ProdutoVendedor pv : produtoVendedor) {
			response.put(ProdutoSetorResponse.retornaOSetor(pv),
					ordenaLista(ProdutoVendedorResponse.converte(produtoVendedor
							.stream()
							.filter(p -> p.getLote().getSetor().getId() == pv.getLote().getSetor().getId())
							.collect(Collectors.toList())),
							type));

		}
		return response;
	}

	private List<ProdutoVendedorResponse> ordenaLista(List<ProdutoVendedorResponse> pv, String type) {
		switch (type) {
			case "L":
				return pv.stream().sorted(Comparator.comparing(ProdutoVendedorResponse::getLote_id).reversed())
						.collect(Collectors.toList());
			case "C":
				return pv.stream().sorted(Comparator.comparing(ProdutoVendedorResponse::getQuantidadeAtual).reversed())
						.collect(Collectors.toList());
			case "F":
				return pv.stream().sorted(Comparator.comparing(ProdutoVendedorResponse::getDataVencimento).reversed())
						.collect(Collectors.toList());
			default:
				return pv;
		}
	}

	@Override
	public ArmazemProdutoResponseDTO findArmazemPorProduto(Long produtoId) {
		List<ProdutoVendedor> produtoVendedores = produtoVendedorService.findProdutoVendedorByProduto_Id(produtoId);

		Map<Long, Integer> mapper = produtoVendedores.stream()
				.collect(Collectors.toMap(
						pv -> pv.getLote().getRepresentante().getArmazem().getId(),
						ProdutoVendedor::getQuantidadeAtual,
						(e1, e2) -> e1 + e2,
						HashMap::new));

		List<ArmazemSelecaoResponseDTO> armazemSelecaoDTOs = new ArrayList<>();
		mapper.forEach((k, v) -> {
			armazemSelecaoDTOs.add(
					ArmazemSelecaoResponseDTO.builder()
							.armazemId(k)
							.quantidadeTotal(v)
							.build());
		});

		return ArmazemProdutoResponseDTO.builder()
				.produtoId(produtoId)
				.armazem(armazemSelecaoDTOs)
				.build();

	}
}
