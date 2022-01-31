package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.EnderecoDTO;
import br.com.meliw4.projetointegrador.entity.Endereco;
import br.com.meliw4.projetointegrador.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {
	@Autowired
	EnderecoRepository enderecoRepository;

	public Endereco register(EnderecoDTO enderecoDTO){
		Endereco endereco = EnderecoDTO.convert(enderecoDTO);
		enderecoRepository.save(endereco);
		return endereco;
	}

}
