package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.CompradorDTO;
import br.com.meliw4.projetointegrador.dto.RepresentanteDTO;
import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Comprador;
import br.com.meliw4.projetointegrador.entity.Endereco;
import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.repository.CompradorRepository;
import br.com.meliw4.projetointegrador.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompradorService {

	@Autowired
	CompradorRepository compradorRepository;
	@Autowired
	EnderecoService enderecoService;
	@Autowired
	EnderecoRepository enderecoRepository;

	public Comprador register(CompradorDTO compradorDTO){
		Endereco endereco = enderecoRepository.getById(compradorDTO.getEndereco_id());
		Comprador comprador = CompradorDTO.convert(compradorDTO,endereco);
		compradorRepository.save(comprador);
		return comprador;
	}
}
