package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.ProdutoCarrinhoDTO;
import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.exception.OrderCheckoutException;
import br.com.meliw4.projetointegrador.repository.ProdutoVendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoVendedorService {

	@Autowired
	private ProdutoVendedorRepository produtoVendedorRepository;

	/**
	 * Busca ProdutoVendedor pelo id e retorna o obejto
	 * @author Thomaz Ferreira
	 * @param id
	 * @return ProdutoVendedor
	 */
	public ProdutoVendedor getProdutoById(Long id) {
		Optional<ProdutoVendedor> produto = produtoVendedorRepository.findById(id);
		produto.orElseThrow(() -> new OrderCheckoutException("O produto de id " + id + " não existe ou foi deletado da base de dados", 400));
		return produto.get();
	}


	/**
	 * Atualiza (decrementa) quantidade do produto no estoque (tabela produto_vendedor)
	 * @author Thomaz Ferreira
	 * @param novaQuantidade
	 * @param idProdutoVendedor
	 * @return void
	 */
	public void updateEstoqueProduto(Integer novaQuantidade, Long idProdutoVendedor) {
		try{
			ProdutoVendedor produtoVendedor = this.getProdutoById(idProdutoVendedor);
			produtoVendedor.setQuantidadeAtual(novaQuantidade);
			produtoVendedorRepository.save(produtoVendedor);
		}catch (RuntimeException e){
			throw new OrderCheckoutException("Erro ao atualizar estoque de produtos - " + e.getMessage(), 500);
		}
	}


	/**
	 * Devolve produto no estoque caso algum erro ocorra no processo de compra
	 * @author Thomaz Ferreira
	 * @param produtosCarrinhoDTO
	 * @return boolean
	 */
	public boolean devolveProdutoEstoque(List<ProdutoCarrinhoDTO> produtosCarrinhoDTO) {
		for(ProdutoCarrinhoDTO produtoCarrinhoDTO : produtosCarrinhoDTO) {
			ProdutoVendedor produtoVendedor = this.getProdutoById(produtoCarrinhoDTO.getAnuncioId());
			Integer qtdAtual = produtoVendedor.getQuantidadeAtual();
			Integer qtdSolicitada = produtoCarrinhoDTO.getQuantidade();
			this.updateEstoqueProduto((qtdAtual+qtdSolicitada), produtoVendedor.getId());
		}
		return true;
	}
}
