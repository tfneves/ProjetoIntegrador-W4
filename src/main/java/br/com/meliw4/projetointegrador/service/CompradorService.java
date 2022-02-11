package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.CompradorDTO;
import br.com.meliw4.projetointegrador.entity.Comprador;
import br.com.meliw4.projetointegrador.entity.Endereco;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.exception.NotFoundException;
import br.com.meliw4.projetointegrador.repository.CompradorRepository;
import org.springframework.stereotype.Service;

@Service
public class CompradorService {

	private final CompradorRepository compradorRepository;
	private final EnderecoService enderecoService;

	public CompradorService(CompradorRepository compradorRepository, EnderecoService enderecoService) {
		this.compradorRepository = compradorRepository;
		this.enderecoService = enderecoService;
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
		Endereco endereco = enderecoService.getById(compradorDTO.getEndereco_id());
		Comprador comprador = CompradorDTO.convert(compradorDTO, endereco);
		compradorRepository.save(comprador);
		return comprador;
	}
}
