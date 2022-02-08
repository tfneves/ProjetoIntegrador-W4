package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.CompradorDTO;
import br.com.meliw4.projetointegrador.dto.RepresentanteDTO;
import br.com.meliw4.projetointegrador.entity.*;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.AvaliacaoNPSRepository;
import br.com.meliw4.projetointegrador.repository.CompradorRepository;
import br.com.meliw4.projetointegrador.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompradorService {

	@Autowired
	CompradorRepository compradorRepository;
	@Autowired
	EnderecoService enderecoService;
	@Autowired
	EnderecoRepository enderecoRepository;
	@Autowired
	AvaliacaoNPSRepository avaliacaoNPSRepository;


	/**
	 *
	 * Busca Comprador por Id
	 * @param id
	 * @return
	 */
	public Comprador findCompradorById(Long id) {
		return compradorRepository.findById(id).
			orElseThrow(() -> new BusinessValidationException("O comprador informado não existe no sistema"));
	}


	public Comprador register(CompradorDTO compradorDTO){
		Endereco endereco = enderecoRepository.getById(compradorDTO.getEndereco_id());
		Comprador comprador = CompradorDTO.convert(compradorDTO,endereco);
		compradorRepository.save(comprador);
		return comprador;
	}
}
