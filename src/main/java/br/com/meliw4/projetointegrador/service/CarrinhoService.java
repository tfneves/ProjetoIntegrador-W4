package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.Carrinho;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarrinhoService {

	private CarrinhoRepository carrinhoRepository;

	public CarrinhoService(CarrinhoRepository carrinhoRepository) {
		this.carrinhoRepository = carrinhoRepository;
	}

	public Carrinho atualizaCarrinho(Long id, Carrinho novoCarrinho){
		this.carrinhoRepository.findById(id).orElseThrow(() -> new BusinessValidationException("Id não localizado"));
		novoCarrinho.setId(id);
		return carrinhoRepository.save(novoCarrinho);
	}
}
