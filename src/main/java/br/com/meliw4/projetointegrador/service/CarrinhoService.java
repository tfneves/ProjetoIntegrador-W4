package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.Carrinho;
import br.com.meliw4.projetointegrador.entity.StatusPedido;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.exception.OrderCheckoutException;
import br.com.meliw4.projetointegrador.repository.CarrinhoRepository;
import br.com.meliw4.projetointegrador.response.CarrinhoResponse;
import org.springframework.stereotype.Service;

@Service
public class CarrinhoService {

	private CarrinhoRepository carrinhoRepository;
	private StatusPedidoService statusPedidoService;

	public CarrinhoService(CarrinhoRepository carrinhoRepository, StatusPedidoService statusPedidoService) {
		this.statusPedidoService = statusPedidoService;
		this.carrinhoRepository = carrinhoRepository;
	}

	public CarrinhoResponse atualizaCarrinho(Long id, StatusPedido statusPedido) {
		Carrinho carrinho = this.carrinhoRepository.findById(id)
				.orElseThrow(() -> new BusinessValidationException("Id não localizado"));
		StatusPedido newStatusPedido = statusPedidoService.findStatusPedidoById(statusPedido.getId());
		carrinho.setStatusPedido(newStatusPedido);
		carrinhoRepository.save(carrinho);
		return CarrinhoResponse.builder()
				.id(carrinho.getId())
				.data(carrinho.getData())
				.compradorId(carrinho.getComprador().getId())
				.statusPedido(newStatusPedido).build();
	}

	/**
	 * Faz persistencia do carrinho
	 *
	 * @author Thomaz Ferreira
	 * @param carrinho
	 */
	public void salvaCarrinho(Carrinho carrinho) {
		try {
			this.carrinhoRepository.save(carrinho);
		} catch (RuntimeException e) {
			throw new OrderCheckoutException("Erro ao salvar carrinho - " + e.getMessage(), 500);
		}
	}
}
