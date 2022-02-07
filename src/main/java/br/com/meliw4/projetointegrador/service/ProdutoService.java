package br.com.meliw4.projetointegrador.service;

import java.util.List;

import br.com.meliw4.projetointegrador.dto.response.ArmazemProdutoResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.LoteResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.ProdutoResponseDTO;
import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import br.com.meliw4.projetointegrador.entity.enumeration.Ordenamento;

public interface ProdutoService {
	List<ProdutoResponseDTO> findAllProdutos();

	List<ProdutoResponseDTO> findProdutoPorCategoria(Categoria categoria);

	List<LoteResponseDTO> findLoteFiltroVencimento(Integer validadeDias, Categoria categoria, Ordenamento order);

	boolean validateProdutoExists(Long id);

	void save(Produto produto);

	Produto getProdutoById(Long id);

	ArmazemProdutoResponseDTO findArmazemPorProduto(Long produtoId);
}
