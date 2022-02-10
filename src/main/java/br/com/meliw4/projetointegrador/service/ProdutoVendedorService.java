package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.ProdutoCarrinhoDTO;
import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import br.com.meliw4.projetointegrador.exception.NotFoundException;
import br.com.meliw4.projetointegrador.exception.OrderCheckoutException;
import br.com.meliw4.projetointegrador.repository.ProdutoVendedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoVendedorService {

	private ProdutoVendedorRepository produtoVendedorRepository;

	public ProdutoVendedorService(ProdutoVendedorRepository produtoVendedorRepository) {
		this.produtoVendedorRepository = produtoVendedorRepository;
	}

	public void save(ProdutoVendedor produtoVendedor) {
		produtoVendedorRepository.save(produtoVendedor);
	}

	public List<ProdutoVendedor> findAll() {
		List<ProdutoVendedor> produtoVendedores = this.produtoVendedorRepository.findAll();

		if (produtoVendedores.isEmpty()) {
			throw new NotFoundException("Não há produtos para a seleção");
		}
		return produtoVendedores;
	}

	public ProdutoVendedor findByLoteIdAndProdutoId(Long loteId, Long produtoId) {
		return produtoVendedorRepository.findByLoteIdAndProdutoId(loteId, produtoId);
	}

	/**
	 * Busca ProdutoVendedor pelo id e retorna o obejto
	 *
	 * @param id
	 * @return ProdutoVendedor
	 * @author Thomaz Ferreira
	 */
	public ProdutoVendedor getProdutoById(Long id) {
		Optional<ProdutoVendedor> produto = produtoVendedorRepository.findById(id);
		produto.orElseThrow(() -> new OrderCheckoutException("O anúncio de id " + id + " não existe ou foi deletado " +
			"da" +
			" base de dados", 400));
		return produto.get();
	}

	/**
	 * Atualiza (decrementa) quantidade do produto no estoque (tabela
	 * produto_vendedor)
	 *
	 * @param novaQuantidade
	 * @param idProdutoVendedor
	 * @return void
	 * @author Thomaz Ferreira
	 */
	public void updateEstoqueProduto(Integer novaQuantidade, Long idProdutoVendedor) {
		try {
			ProdutoVendedor produtoVendedor = this.getProdutoById(idProdutoVendedor);
			produtoVendedor.setQuantidadeAtual(novaQuantidade);
			produtoVendedorRepository.save(produtoVendedor);
		} catch (RuntimeException e) {
			throw new OrderCheckoutException("Erro ao atualizar estoque de produtos - " + e.getMessage(), 500);
		}
	}

	/**
	 * Devolve produto no estoque caso algum erro ocorra no processo de compra
	 *
	 * @param produtosCarrinhoDTO
	 * @return boolean
	 * @author Thomaz Ferreira
	 */
	public boolean devolveProdutoEstoque(List<ProdutoCarrinhoDTO> produtosCarrinhoDTO) {
		for (ProdutoCarrinhoDTO produtoCarrinhoDTO : produtosCarrinhoDTO) {
			ProdutoVendedor produtoVendedor = this.getProdutoById(produtoCarrinhoDTO.getAnuncioId());
			Integer qtdAtual = produtoVendedor.getQuantidadeAtual();
			Integer qtdSolicitada = produtoCarrinhoDTO.getQuantidade();
			this.updateEstoqueProduto((qtdAtual + qtdSolicitada), produtoVendedor.getId());
		}
		return true;
	}

	public List<ProdutoVendedor> findProdutoVendedorByProduto_Id(Long id) {
		return produtoVendedorRepository.findByProduto_Id(id)
			.orElseThrow(() -> new NotFoundException(
				"Não há produtos para a seleção"));
	}
}
