package br.com.meliw4.projetointegrador.controller;

import java.util.List;

import javax.validation.Valid;

import br.com.meliw4.projetointegrador.service.impl.ProdutoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.meliw4.projetointegrador.dto.response.ArmazemProdutoResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.LoteResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.ProdutoResponseDTO;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import br.com.meliw4.projetointegrador.entity.enumeration.Ordenamento;
import br.com.meliw4.projetointegrador.service.ProdutoService;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class ProdutoController {

	@Autowired
	ProdutoServiceImpl produtoServiceimpl;

	private final ProdutoService produtoService;

	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	/**
	 *
	 * Listagem de todos produtos
	 *
	 * @see ProdutoResponseDTO
	 *
	 * @return ResponseEntity List<ProdutoResponseDTO>
	 */
	@GetMapping
	public ResponseEntity<List<ProdutoResponseDTO>> findAllProdutos() {
		return ResponseEntity.ok(this.produtoService.findAllProdutos());
	}

	/**
	 * Rota para pesquisa em query de produtos por categoria
	 *
	 * @param categoria Filtro de categorias ENUM
	 *                  [{'FS': 'FRESCO','RR': 'REFRIGERADO','FF': 'CONGELADO'}],
	 *                  default param categoria 'FS'
	 * @see ProdutoResponseDTO
	 *
	 * @return ResponseEntity List<ProdutoResponseDTO>
	 */
	@GetMapping(path = "/list")
	public ResponseEntity<List<ProdutoResponseDTO>> findProdutoPorCategoria(
			@RequestParam() final Categoria categoria) {
		return ResponseEntity.ok(this.produtoService.findProdutoPorCategoria(categoria));
	}

	@GetMapping("/list/product")
	public ResponseEntity<?> listaTodosOsLotesOrdenadoPOrParametro(@RequestParam Long idProduto,
			@RequestParam(defaultValue = "L") final String type) {
		return ResponseEntity.ok(produtoServiceimpl.listaTodosOsLotes(idProduto, type));
	}

	/**
	 * Rota para pesquisa em query de armazens contendo Produto
	 *
	 * @param Long produtoId - Identificador ID de produto
	 * @see ArmazemProdutoResponseDTO
	 *
	 * @return ResponseEntity List<ArmazemProdutoResponseDTO>
	 */
	@GetMapping(path = "/warehouse") // TODO: rota auth representante
	public ResponseEntity<ArmazemProdutoResponseDTO> findArmazemPorProduto(
			@RequestParam() final Long produtoId) {
		return ResponseEntity.ok(this.produtoService.findArmazemPorProduto(produtoId));
	}
}
