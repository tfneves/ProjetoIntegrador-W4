package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.CompradorDTO;
import br.com.meliw4.projetointegrador.entity.Comprador;
import br.com.meliw4.projetointegrador.entity.Endereco;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.CompradorRepository;
import org.springframework.stereotype.Service;

@Service
public class CompradorService {

	private final CompradorRepository compradorRepository;
	private final EnderecoService enderecoService;
	private final UsuarioService usuarioService;


	public CompradorService(CompradorRepository compradorRepository, EnderecoService enderecoService, UsuarioService usuarioService) {
		this.compradorRepository = compradorRepository;
		this.enderecoService = enderecoService;
		this.usuarioService = usuarioService;
	}

	/**
	 *
	 * Busca Comprador por Id
	 *
	 * @param id
	 * @return
	 */
	public Comprador findCompradorById(Long id) {
		return compradorRepository.findById(id)
				.orElseThrow(() -> new BusinessValidationException("O comprador informado não existe no sistema"));
	}


	public Comprador register(CompradorDTO compradorDTO) {
		Endereco endereco = enderecoService.findById(compradorDTO.getEndereco_id());
		Comprador comprador = CompradorDTO.convert(compradorDTO, endereco);
		if(!usuarioService.usuarioCadastrado(compradorDTO.getLogin()))
			throw new BusinessValidationException("Login já existente na base de dados");
		compradorRepository.save(comprador);
		return comprador;
	}
}
