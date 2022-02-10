package br.com.meliw4.projetointegrador.service;

import java.util.List;
import java.util.Map;

import br.com.meliw4.projetointegrador.dto.response.ArmazemProdutoResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.ProdutoResponseDTO;
import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import br.com.meliw4.projetointegrador.response.ProdutoSetorResponse;
import br.com.meliw4.projetointegrador.response.ProdutoVendedorResponse;

public interface ProdutoService {
	void save(Produto produto);

	Produto findById(Long id);

	List<ProdutoResponseDTO> findAllProdutos();

	List<ProdutoResponseDTO> findProdutoPorCategoria(Categoria categoria);

	ArmazemProdutoResponseDTO findArmazemPorProduto(Long produtoId);

	boolean validateProdutoExists(Long id);

	Map<ProdutoSetorResponse, List<ProdutoVendedorResponse>> listaTodosOsLotes(Long idProduto, String type);
}
