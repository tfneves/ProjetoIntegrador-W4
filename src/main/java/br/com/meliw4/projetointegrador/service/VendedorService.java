package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.VendedorDTO;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.VendedorRepository;
import org.springframework.stereotype.Service;

@Service
public class VendedorService {


	private VendedorRepository vendedorRepository;

	public VendedorService(VendedorRepository vendedorRepository) {
		this.vendedorRepository = vendedorRepository;
	}

	public Vendedor register(VendedorDTO vendedorDTO) {
		Vendedor vendedor = vendedorDTO.convert(vendedorDTO);
		vendedorRepository.save(vendedor);
		return vendedor;
	}

	public Vendedor findVendedorById(Long id) {
		return vendedorRepository
			.findById(id)
			.orElseThrow(() -> new BusinessValidationException("O vendedor com id " + id + " não existe."));
	}
}
