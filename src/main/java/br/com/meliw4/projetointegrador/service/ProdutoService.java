package br.com.meliw4.projetointegrador.service;

import java.util.List;

import br.com.meliw4.projetointegrador.dto.response.ArmazemProdutoResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.LoteResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.ProdutoResponseDTO;
import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import br.com.meliw4.projetointegrador.entity.enumeration.Ordenamento;

public interface ProdutoService {
	void save(Produto produto);

	Produto findById(Long id);

	List<ProdutoResponseDTO> findAllProdutos();

	List<ProdutoResponseDTO> findProdutoPorCategoria(Categoria categoria);

	List<LoteResponseDTO> findLoteFiltroVencimento(Integer validadeDias, Categoria categoria, Ordenamento order);

	ArmazemProdutoResponseDTO findArmazemPorProduto(Long produtoId);

	boolean validateProdutoExists(Long id);
}
