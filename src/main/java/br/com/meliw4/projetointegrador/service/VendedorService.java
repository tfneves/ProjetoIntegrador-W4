package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.EnderecoDTO;
import br.com.meliw4.projetointegrador.dto.VendedorDTO;
import br.com.meliw4.projetointegrador.entity.Endereco;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import br.com.meliw4.projetointegrador.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendedorService {

	@Autowired
	VendedorRepository vendedorRepository;

	public Vendedor register(VendedorDTO vendedorDTO) {
		Vendedor vendedor = vendedorDTO.convert(vendedorDTO);
		vendedorRepository.save(vendedor);
		return vendedor;
	}
}
