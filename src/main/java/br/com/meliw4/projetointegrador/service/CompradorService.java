package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.CompradorDTO;
import br.com.meliw4.projetointegrador.dto.RepresentanteDTO;
import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Comprador;
import br.com.meliw4.projetointegrador.entity.Endereco;
import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
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
	UsuarioService usuarioService;


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
		Endereco endereco = enderecoRepository.findById(compradorDTO.getEndereco_id()).orElseThrow(
			() -> new BusinessValidationException("O endereço informado não existe"));
		Comprador comprador = CompradorDTO.convert(compradorDTO,endereco);
		if(!usuarioService.usuarioCadastrado(compradorDTO.getLogin()))
			throw new BusinessValidationException("Login já existente na base de dados");
		compradorRepository.save(comprador);
		return comprador;
	}
}
