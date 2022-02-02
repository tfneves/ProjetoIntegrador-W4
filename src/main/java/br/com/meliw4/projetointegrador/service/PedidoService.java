package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.CarrinhoDTO;
import br.com.meliw4.projetointegrador.dto.ProdutoCarrinhoDTO;
import br.com.meliw4.projetointegrador.entity.*;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.exception.InternalServerErrorException;
import br.com.meliw4.projetointegrador.repository.CarrinhoRepository;
import br.com.meliw4.projetointegrador.repository.ProdutoCarrinhoRepository;
import br.com.meliw4.projetointegrador.repository.ProdutoRepository;
import br.com.meliw4.projetointegrador.response.PedidoResponse;
import br.com.meliw4.projetointegrador.response.ProdutoPedidoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class PedidoService {

	@Autowired
	CarrinhoRepository carrinhoRepository;
	@Autowired
	ProdutoCarrinhoRepository produtoCarrinhoRepository;
	@Autowired
	StatusPedidoService statusPedidoService;
	@Autowired
	CompradorService compradorService;
	@Autowired
	ProdutoServiceOrder produtoServiceOrder;
	@Autowired
	ProdutoVendedorService produtoVendedorService;


	public PedidoService(CarrinhoRepository carrinhoRepository, ProdutoCarrinhoRepository produtoCarrinhoRepository) {
		this.carrinhoRepository = carrinhoRepository;
		this.produtoCarrinhoRepository = produtoCarrinhoRepository;
	}


	/**
	 * Salva pedido nas tabelas pertinentes
	 * @author Thomaz Ferreira
	 * @param dto
	 * @return boolean
	 */
	public Long salvarPedido(CarrinhoDTO dto) {
		List<ProdutoCarrinho> produtosCarrinho = new ArrayList<>();
		StatusPedido statusPedido = statusPedidoService.findStatusCodeWithName("CHECKOUT");
		Comprador comprador = compradorService.findCompradorById(dto.getIdComprador());
		Carrinho carrinho = CarrinhoDTO.parseToEntityCarrinho(statusPedido, comprador);

		for(ProdutoCarrinhoDTO produtoCarrinhoDTO : dto.getProdutos()) {
			ProdutoVendedor produto = produtoVendedorService.getProdutoById(produtoCarrinhoDTO.getAnuncioId());
			if(verificaValidadeProduto(produto) && verificaDisponibilidadeEstoque(produto, produtoCarrinhoDTO))
				atualizaQuantidadeEstoque(produto, produtoCarrinhoDTO);
				produtosCarrinho.add(CarrinhoDTO.parseToEntityProdutoCarrinho(produtoCarrinhoDTO, produto, carrinho));
		}

		carrinhoRepository.save(carrinho);
		for(ProdutoCarrinho p : produtosCarrinho){
			produtoCarrinhoRepository.save(p);
		}
		return carrinho.getId();
	}


	/**
	 * Atualiza quantidade do produto no estoque (tabela produto_vendedor)
	 * @author Thomaz Ferreira
	 * @param produtoVendedor
	 * @return boolean
	 */
	private boolean atualizaQuantidadeEstoque(ProdutoVendedor produtoVendedor, ProdutoCarrinhoDTO produtoCarrinhoDTO){
		Integer estoqueDisponivel = produtoVendedor.getQuantidadeAtual();
		if(estoqueDisponivel == null)
			throw new InternalServerErrorException(
				"Houve um erro ao pegar a quantidade disponível em estoque do produto de ID " + produtoVendedor.getId()
			);

		Integer quantidadeSolicitada = produtoCarrinhoDTO.getQuantidade();
		if(quantidadeSolicitada == null)
			throw new InternalServerErrorException(
				"Houve um erro ao pegar a quantidade solicitada na compra do produto de ID " + produtoCarrinhoDTO.getAnuncioId()
			);

		Integer novaQuantidade = estoqueDisponivel-quantidadeSolicitada;
		produtoVendedor.setQuantidadeAtual(novaQuantidade);
		produtoVendedorService.updateEstoqueProduto(produtoVendedor);
		return true;
	}


	/**
	 * Verifica e valida disponibilidade do produto solicitado no estoque
	 * @author Thomaz Ferreira
	 * @param produtoVendedor
	 * @return boolean
	 */
	private boolean verificaDisponibilidadeEstoque(ProdutoVendedor produtoVendedor, ProdutoCarrinhoDTO produtoCarrinhoDTO) {
		Integer estoqueDisponivel = produtoVendedor.getQuantidadeAtual();
		Integer quantidadeSolicitada = produtoCarrinhoDTO.getQuantidade();

		if(quantidadeSolicitada<=0)
			throw new BusinessValidationException(
				"A quantidade solicitada do produto de ID " + produtoVendedor.getId() + " deve ser igual ou superior a 1 volume"
			);

		if(estoqueDisponivel>0 && estoqueDisponivel<quantidadeSolicitada)
			throw new BusinessValidationException(
				"A quantidade solicitada do produto de ID " + produtoVendedor.getId() + " é superior a existente em estoque. \n "
			+ "Quantidade disponivel: " + estoqueDisponivel);
		return true;
	}


	/**
	 * Verifica se o prazo de validade do produto possui validade superior a 3 semanas
	 * @author Thomaz Ferreira
	 * @param produtoVendedor
	 * @return boolean
	 */
	private boolean verificaValidadeProduto(ProdutoVendedor produtoVendedor) {
		final int DIAS_MINIMOS_VALIDADE = 22;
		LocalDate validadeProduto = produtoVendedor.getDataVencimento();

		if(validadeProduto == null)
			throw new InternalServerErrorException("Houve um erro ao pegar a validade do produto de ID " + produtoVendedor.getId());

		long dias = DAYS.between(LocalDate.now(), validadeProduto);
		if(dias < DIAS_MINIMOS_VALIDADE)
			throw new BusinessValidationException(
				"O produto de ID " + produtoVendedor.getId() + " possui um prazo de validade inferior a " + DIAS_MINIMOS_VALIDADE + " dias"
			);
		return true;
	}


	/**
	 * Calcula valor total do carrinho
	 * @author Thomaz Ferreira
	 * @param idCarrinho
	 * @return BigDecimal
	 */
	public BigDecimal calculaValorTotalCarrinho(Long idCarrinho) {
		BigDecimal valorTotal = new BigDecimal("0.0");
		List<ProdutoCarrinho> produtosCarrinho = produtoCarrinhoRepository.findByCarrinho_Id(idCarrinho);
		if(produtosCarrinho.isEmpty())
			throw new BusinessValidationException("O carrinho informado não possui nenhum produto");

		for(ProdutoCarrinho produtoCarrinho : produtosCarrinho){
			ProdutoVendedor produto = produtoVendedorService.getProdutoById(produtoCarrinho.getProduto().getId());
			BigDecimal precoUnitario = produto.getPreco();
			BigDecimal qtdCarrinho = new BigDecimal(produtoCarrinho.getQuantidade());
			valorTotal = valorTotal.add(precoUnitario.multiply(qtdCarrinho));
		}
		return valorTotal;
	}


	public PedidoResponse getPedido(Long id) {
		PedidoResponse pedidoResponse = new PedidoResponse();
		Carrinho pedido = validatePedido(id);
		List<ProdutoCarrinho> produtosCarrinho = produtoCarrinhoRepository.findByCarrinho_Id(id);
		for (ProdutoCarrinho produtoCarrinho : produtosCarrinho) {
			pedidoResponse.getProdutosPedido()
					.add(
							ProdutoPedidoResponse.builder()
									.id(produtoCarrinho.getId())
									.quantidade(produtoCarrinho.getQuantidade())
									.build());
		}
		return pedidoResponse;
	}

	private Carrinho validatePedido(Long id) {
		if (!carrinhoRepository.existsById(id)) {
			throw new BusinessValidationException("O pedido näo existe.");
		}
		Carrinho pedido = carrinhoRepository.getById(id);
		if (!pedido.getStatusPedido().getStatusCode().equals("Finzalizado")) {
			throw new BusinessValidationException("O pedido não foi finalizado.");
		}
		return pedido;
	}
}
