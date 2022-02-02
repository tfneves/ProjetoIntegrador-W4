package br.com.meliw4.projetointegrador.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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
