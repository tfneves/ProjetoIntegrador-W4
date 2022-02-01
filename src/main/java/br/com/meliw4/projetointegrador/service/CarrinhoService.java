package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.Carrinho;
import br.com.meliw4.projetointegrador.repository.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrinhoService {

	private CarrinhoRepository carrinhoRepository;

	@Autowired
	public CarrinhoService(CarrinhoRepository carrinhoRepository) {
		this.carrinhoRepository = carrinhoRepository;
	}

	public List<Carrinho> atualizaCarrinho(Long id){
		return this.carrinhoRepository.findAll();
	}
}
