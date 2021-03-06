package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.CarrinhoDTO;
import br.com.meliw4.projetointegrador.dto.ProdutoCarrinhoDTO;
import br.com.meliw4.projetointegrador.entity.*;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.exception.OrderCheckoutException;
import br.com.meliw4.projetointegrador.repository.CarrinhoRepository;
import br.com.meliw4.projetointegrador.repository.ProdutoCarrinhoRepository;
import br.com.meliw4.projetointegrador.response.PedidoResponse;
import br.com.meliw4.projetointegrador.response.ProdutoPedidoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class PedidoService {

	public static List<ProdutoCarrinhoDTO> previousStateProdutoVendedor;
	private final CarrinhoService carrinhoService;
	private final ProdutoCarrinhoService produtoCarrinhoService;
	private final StatusPedidoService statusPedidoService;
	private final CompradorService compradorService;
	private final ProdutoServiceOrder produtoServiceOrder;
	private final ProdutoVendedorService produtoVendedorService;
	private final CarrinhoRepository carrinhoRepository;
	private final ProdutoCarrinhoRepository produtoCarrinhoRepository;

	public PedidoService(CarrinhoService carrinhoService, ProdutoCarrinhoService produtoCarrinhoService,
						 StatusPedidoService statusPedidoService, CompradorService compradorService,
						 ProdutoServiceOrder produtoServiceOrder, ProdutoVendedorService produtoVendedorService,
						 CarrinhoRepository carrinhoRepository, ProdutoCarrinhoRepository produtoCarrinhoRepository) {
		previousStateProdutoVendedor = new ArrayList<>();
		this.carrinhoService = carrinhoService;
		this.produtoCarrinhoService = produtoCarrinhoService;
		this.statusPedidoService = statusPedidoService;
		this.compradorService = compradorService;
		this.produtoServiceOrder = produtoServiceOrder;
		this.produtoVendedorService = produtoVendedorService;
		this.carrinhoRepository = carrinhoRepository;
		this.produtoCarrinhoRepository = produtoCarrinhoRepository;
	}


	/**
	 * Salva pedido nas tabelas pertinentes
	 *
	 * @param dto
	 * @return boolean
	 * @author Thomaz Ferreira
	 */
	public Long salvaPedido(CarrinhoDTO dto) {
		List<ProdutoCarrinho> produtosCarrinho = new ArrayList<>();
		StatusPedido statusPedido = statusPedidoService.findStatusCodeWithName("FINALIZADO");
		Comprador comprador = compradorService.findCompradorById(dto.getIdComprador());
		Carrinho carrinho = CarrinhoDTO.parseToEntityCarrinho(statusPedido, comprador);

		for (ProdutoCarrinhoDTO produtoCarrinhoDTO : dto.getProdutos()) {
			ProdutoVendedor produto = produtoVendedorService.getProdutoById(produtoCarrinhoDTO.getAnuncioId());
			if (verificaValidadeProduto(produto) && verificaDisponibilidadeEstoque(produto, produtoCarrinhoDTO)) {
				atualizaQuantidadeEstoque(produto, produtoCarrinhoDTO);
				previousStateProdutoVendedor.add(produtoCarrinhoDTO);
				produtosCarrinho.add(CarrinhoDTO.parseToEntityProdutoCarrinho(produtoCarrinhoDTO, produto, carrinho));
			}
		}

		carrinhoService.salvaCarrinho(carrinho);
		for (ProdutoCarrinho p : produtosCarrinho) {
			produtoCarrinhoService.salvaProdutoCarrinho(p);
		}
		clearQueueProdutoVendedor();
		return carrinho.getId();
	}


	/**
	 * Atualiza quantidade do produto no estoque (tabela produto_vendedor)
	 *
	 * @param produtoVendedor
	 * @return boolean
	 * @author Thomaz Ferreira
	 */
	public boolean atualizaQuantidadeEstoque(ProdutoVendedor produtoVendedor, ProdutoCarrinhoDTO produtoCarrinhoDTO) {
		Integer estoqueDisponivel = produtoVendedor.getQuantidadeAtual();
		if (estoqueDisponivel == 0)
			throw new OrderCheckoutException(
				"Houve um erro ao pegar a quantidade dispon??vel em estoque do produto de ID " + produtoVendedor.getId(), 500
			);
		Integer quantidadeSolicitada = produtoCarrinhoDTO.getQuantidade();
		if (quantidadeSolicitada == null)
			throw new OrderCheckoutException(
				"Houve um erro ao pegar a quantidade solicitada na compra do produto de ID " + produtoCarrinhoDTO.getAnuncioId(), 500
			);
		Integer novaQuantidade = estoqueDisponivel - quantidadeSolicitada;
		produtoVendedorService.updateEstoqueProduto(novaQuantidade, produtoVendedor.getId());
		return true;
	}


	/**
	 * Verifica e valida disponibilidade do produto solicitado no estoque
	 *
	 * @param produtoVendedor
	 * @return boolean
	 * @author Thomaz Ferreira
	 */
	public boolean verificaDisponibilidadeEstoque(ProdutoVendedor produtoVendedor,
												   ProdutoCarrinhoDTO produtoCarrinhoDTO) {
		Integer estoqueDisponivel = produtoVendedor.getQuantidadeAtual();
		Integer quantidadeSolicitada = produtoCarrinhoDTO.getQuantidade();
		if (quantidadeSolicitada <= 0)
			throw new OrderCheckoutException(
				"A quantidade solicitada do produto de ID " + produtoVendedor.getId() + " deve ser igual ou superior a" +
					" 1 volume", 400
			);
		if (estoqueDisponivel == 0)
			throw new OrderCheckoutException(
				"O produto de ID " + produtoVendedor.getId() + " n??o se encontra dispon??vel em estoque no momento", 400
			);
		if (estoqueDisponivel < quantidadeSolicitada)
			throw new OrderCheckoutException(
				"A quantidade solicitada do produto de ID " + produtoVendedor.getId() + " ?? superior a existente em " +
					"estoque. \n "
					+ "Quantidade disponivel: " + estoqueDisponivel, 400);
		return true;
	}


	/**
	 * Verifica se o prazo de validade do produto possui validade superior a 3 semanas
	 *
	 * @param produtoVendedor
	 * @return boolean
	 * @author Thomaz Ferreira
	 */
	public boolean verificaValidadeProduto(ProdutoVendedor produtoVendedor) {
		final int DIAS_MINIMOS_VALIDADE = 22;
		LocalDate validadeProduto = produtoVendedor.getDataVencimento();
		if (validadeProduto == null)
			throw new OrderCheckoutException("Houve um erro ao pegar a validade do produto de ID " + produtoVendedor.getId(), 500);
		long dias = DAYS.between(LocalDate.now(), validadeProduto);
		if (dias < DIAS_MINIMOS_VALIDADE)
			throw new OrderCheckoutException(
				"O produto de ID " + produtoVendedor.getId() + " possui um prazo de validade inferior a " + DIAS_MINIMOS_VALIDADE + " dias", 400
			);
		return true;
	}


	/**
	 * Calcula valor total do carrinho
	 *
	 * @param idCarrinho
	 * @return BigDecimal
	 * @author Thomaz Ferreira
	 */
	public BigDecimal calculaValorTotalCarrinho(Long idCarrinho) {
		BigDecimal valorTotal = new BigDecimal("0.0");
		List<ProdutoCarrinho> produtosCarrinho = produtoCarrinhoService.buscaProdutosCarrinhoById(idCarrinho);
		for (ProdutoCarrinho produtoCarrinho : produtosCarrinho) {
			ProdutoVendedor produto = produtoVendedorService.getProdutoById(produtoCarrinho.getProduto().getId());
			BigDecimal precoUnitario = produto.getPreco();
			BigDecimal qtdCarrinho = new BigDecimal(produtoCarrinho.getQuantidade());
			valorTotal = valorTotal.add(precoUnitario.multiply(qtdCarrinho));
		}
		return valorTotal;
	}


	/**
	 * Limpa fila de produtos
	 *
	 * @author Thomaz Ferreira
	 */
	public static void clearQueueProdutoVendedor() {
		previousStateProdutoVendedor.clear();
	}


	public PedidoResponse getPedido(Long id) {
		PedidoResponse pedidoResponse = new PedidoResponse();
		Carrinho pedido = validatePedido(id);
		List<ProdutoCarrinho> produtosCarrinho = produtoCarrinhoRepository.findByCarrinho_Id(id);
		for (ProdutoCarrinho produtoCarrinho : produtosCarrinho) {
			pedidoResponse.getProdutosPedido()
				.add(
					ProdutoPedidoResponse.builder()
						.produtoCarrinhoId(produtoCarrinho.getId())
						.anuncioId(produtoCarrinho.getProduto().getId())
						.produtoId(produtoCarrinho.getProduto().getProduto().getId())
						.vendedorId(produtoCarrinho.getProduto().getVendedor().getId())
						.quantidade(produtoCarrinho.getQuantidade())
						.build());
		}
		return pedidoResponse;
	}

	public Carrinho validatePedido(Long id) {
		if (!carrinhoRepository.existsById(id)) {
			throw new BusinessValidationException("O pedido n??o existe.");
		}
			Carrinho pedido = carrinhoRepository.findById(id).orElse(new Carrinho());
			if (!pedido.getStatusPedido().getStatusCode().equalsIgnoreCase("FINALIZADO")) {
				throw new BusinessValidationException("O pedido n??o foi finalizado.");
			}
			return pedido;
		}
	}
