package br.com.meliw4.projetointegrador.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.meliw4.projetointegrador.dto.response.ProdutoResponseDTO;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import br.com.meliw4.projetointegrador.service.ProdutoService;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class ProdutoController {

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
	@ResponseBody
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
	@ResponseBody
	public ResponseEntity<List<ProdutoResponseDTO>> findProdutoPorCategoria(
			@RequestParam() Categoria categoria) {
		return ResponseEntity.ok(this.produtoService.findProdutoPorCategoria(categoria));
	}
}
