package br.com.meliw4.projetointegrador.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.meliw4.projetointegrador.dto.response.ProdutoResponseDTO;
import br.com.meliw4.projetointegrador.entity.Produto;
import br.com.meliw4.projetointegrador.entity.ProdutoCategoria;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import br.com.meliw4.projetointegrador.repository.ProdutoRepository;

public class ProdutoServiceImplTest {
	ProdutoRepository produtoRepository = Mockito.mock(ProdutoRepository.class);

	ProdutoServiceImpl produtoService;

	@BeforeEach
	void setup() {
		this.produtoService = new ProdutoServiceImpl(produtoRepository);
	}

	@Test
	void shouldFindProductByCategory() {
		List<Produto> produtos = new ArrayList<>();
		Produto produto1 = Produto.builder()
				.volume(11d)
				.produtoCategoria(
						ProdutoCategoria.builder().categoria(Categoria.FF).build())
				.quantidadeInicial(1)
				.quantidadeAtual(11)
				.dataVencimento(LocalDate.now())
				.build();
		Produto produto2 = Produto.builder()
				.volume(22d)
				.produtoCategoria(
						ProdutoCategoria.builder().categoria(Categoria.FS).build())
				.quantidadeInicial(2)
				.quantidadeAtual(22)
				.dataVencimento(LocalDate.now())
				.build();
		produtos.add(produto1);
		produtos.add(produto2);

		when(produtoRepository.findProdutoPorCategoria("FF")).thenReturn(produtos);
		List<ProdutoResponseDTO> testeProduto = produtoService.findProdutoPorCategoria(Categoria.FF);
		assertEquals(2, testeProduto.size());
		assertEquals(11, testeProduto.get(0).getVolume());
		assertEquals("FF", testeProduto.get(0).getProdutoCategoria().getCategoria().name());
	}

	@Test
	void shouldFindAllProducts() {
		List<Produto> produtos = new ArrayList<>();
		Produto produto1 = Produto.builder()
				.volume(11d)
				.produtoCategoria(
						ProdutoCategoria.builder().categoria(Categoria.FF).build())
				.quantidadeInicial(1)
				.quantidadeAtual(11)
				.dataVencimento(LocalDate.now())
				.build();
		Produto produto2 = Produto.builder()
				.volume(22d)
				.produtoCategoria(
						ProdutoCategoria.builder().categoria(Categoria.FS).build())
				.quantidadeInicial(2)
				.quantidadeAtual(22)
				.dataVencimento(LocalDate.now())
				.build();
		Produto produto3 = Produto.builder()
				.volume(33d)
				.produtoCategoria(
						ProdutoCategoria.builder().categoria(Categoria.RR).build())
				.quantidadeInicial(3)
				.quantidadeAtual(33)
				.dataVencimento(LocalDate.now())
				.build();
		produtos.add(produto1);
		produtos.add(produto2);
		produtos.add(produto3);

		when(produtoRepository.findAll()).thenReturn(produtos);
		List<ProdutoResponseDTO> testeProduto = produtoService.findAllProdutos();
		assertEquals(3, testeProduto.size());
		assertEquals(11, testeProduto.get(0).getVolume());
		assertEquals("FF", testeProduto.get(0).getProdutoCategoria().getCategoria().name());
	}
}
