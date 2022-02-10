package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.EnderecoDTO;
import br.com.meliw4.projetointegrador.entity.Endereco;
import br.com.meliw4.projetointegrador.exception.NotFoundException;
import br.com.meliw4.projetointegrador.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {
	private final EnderecoRepository enderecoRepository;

	public EnderecoService(EnderecoRepository enderecoRepository) {
		this.enderecoRepository = enderecoRepository;
	}

	public Endereco register(EnderecoDTO enderecoDTO) {
		Endereco endereco = EnderecoDTO.convert(enderecoDTO);
		enderecoRepository.save(endereco);
		return endereco;
	}

	public Endereco getById(Long enderecoId) {
		return enderecoRepository.findById(enderecoId).orElseThrow(() -> new NotFoundException("Id não localizado"));
	}
}
