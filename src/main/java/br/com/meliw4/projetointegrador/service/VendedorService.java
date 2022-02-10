package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.VendedorDTO;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.VendedorRepository;
import org.springframework.stereotype.Service;

@Service
public class VendedorService {

	private VendedorRepository vendedorRepository;
	private UsuarioService usuarioService;

	public VendedorService(VendedorRepository vendedorRepository, UsuarioService usuarioService) {
		this.vendedorRepository = vendedorRepository;
		this.usuarioService = usuarioService;
	}

	public Vendedor register(VendedorDTO vendedorDTO) {
		Vendedor vendedor = vendedorDTO.convert(vendedorDTO);
		if(!usuarioService.usuarioCadastrado(vendedorDTO.getLogin()))
			throw new BusinessValidationException("Login já existente na base de dados");
		vendedorRepository.save(vendedor);
		return vendedor;
	}

	public Vendedor findVendedorById(Long id) {
		return vendedorRepository
				.findById(id)
				.orElseThrow(() -> new BusinessValidationException("O vendedor com id " + id + " não existe."));
	}
}
