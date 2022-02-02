package br.com.meliw4.projetointegrador.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.ProdutoVendedorRepository;
import br.com.meliw4.projetointegrador.response.ProdutoVendedorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.meliw4.projetointegrador.entity.Produto;
import org.springframework.stereotype.Service;

import br.com.meliw4.projetointegrador.dto.response.ProdutoResponseDTO;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import br.com.meliw4.projetointegrador.exception.NotFoundException;
import br.com.meliw4.projetointegrador.repository.ProdutoRepository;
import br.com.meliw4.projetointegrador.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	ProdutoVendedorRepository vendedorProdutoRepository;

	private ProdutoRepository produtoRepository;

	ProdutoServiceImpl(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
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

	public List<ProdutoVendedorResponse> listaTodosOsLotes(Long id, String type) {
		if(tipoValido(type))
			throw new BusinessValidationException("Validador inválido");

		List<ProdutoVendedorResponse> produtoVendedor = sanitizaListaDeProdutoVendedor(this.vendedorProdutoRepository.findAll());
		List<ProdutoVendedorResponse> produtos = produtoVendedor.stream()
			.filter(produto -> id.equals(produto.getProduto().getId()))
			.collect(Collectors.toList());

		if (produtos.size() == 0)
			throw new NotFoundException("ID não localizado");

		return filtraAListaDeProdutos(produtos, type);
	}

	private boolean tipoValido(String tipo){
		List<String> validadores = Arrays.asList("L", "C", "F");
		List<String> tipoValido = validadores.stream()
			.filter(validador -> validador.equals(tipo.toUpperCase(Locale.ROOT)))
			.collect(Collectors.toList());
		return tipoValido.size() == 0;
	}

	private List<ProdutoVendedorResponse> sanitizaListaDeProdutoVendedor(List<ProdutoVendedor> produtos) {
		List<ProdutoVendedorResponse> response = new ArrayList<>();
		for (ProdutoVendedor produto : produtos) {
			response.add(ProdutoVendedorResponse.builder()
				.id(produto.getId())
				.vendedor(produto.getVendedor())
				.produto(produto.getProduto())
				.preco(produto.getPreco())
				.temperaturaAtual(produto.getTemperaturaAtual())
				.dataVencimento(produto.getDataVencimento())
				.dataManufatura(produto.getDataManufatura())
				.quantidadeInicial(produto.getQuantidadeInicial())
				.quantidadeAtual(produto.getQuantidadeAtual())
				.lote(produto.getLote().getId())
				.build());
		}
		return response;
	}

	private List<ProdutoVendedorResponse> filtraAListaDeProdutos(List<ProdutoVendedorResponse> lista, String tipo){
		String validador = tipo.toUpperCase(Locale.ROOT);
		switch (validador){
			case "L":
				return lista.stream()
					.sorted(Comparator.comparingLong(ProdutoVendedorResponse::getLote))
					.collect(Collectors.toList());
			case "C":
				return lista.stream()
					.sorted(Comparator.comparingLong(ProdutoVendedorResponse::getQuantidadeAtual))
					.collect(Collectors.toList());
			case "F":
				return lista.stream()
					.sorted(Comparator.comparing(ProdutoVendedorResponse::getDataVencimento))
					.collect(Collectors.toList());
			default:
				return lista;
		}
	}


	// @Override
	// public List<ProdutoResponseDTO> findAllProdutos() {
	// List<ProdutoResponseDTO> produtoResponseDTO =
	// produtoRepository.findAll().stream()
	// .map(ProdutoResponseDTO::toDTO)
	// .collect(Collectors.toList());
	//
	// if (produtoResponseDTO.isEmpty()) {
	// throw new NotFoundException("Não há produtos para a seleção");
	// }
	//
	// return produtoResponseDTO;
	// }
	//
	// @Override
	// public List<ProdutoResponseDTO> findProdutoPorCategoria(Categoria categoria)
	// {
	// List<ProdutoResponseDTO> produtoResponseDTO =
	// produtoRepository.findProdutoPorCategoria(categoria.name())
	// .stream()
	// .map(ProdutoResponseDTO::toDTO)
	// .collect(Collectors.toList());
	//
	// if (produtoResponseDTO.isEmpty()) {
	// throw new NotFoundException("Não há produtos para a seleção");
	// }
	//
	// return produtoResponseDTO;

	// }

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
}
