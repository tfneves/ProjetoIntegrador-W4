package br.com.meliw4.projetointegrador.service.impl;

import java.security.Signer;
import java.util.*;
import java.util.stream.Collectors;

import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.ProdutoVendedorRepository;
import br.com.meliw4.projetointegrador.response.ProdutoSetorResponse;
import br.com.meliw4.projetointegrador.response.ProdutoVendedorResponse;
import br.com.meliw4.projetointegrador.response.SetorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.meliw4.projetointegrador.entity.Produto;
import org.springframework.stereotype.Service;

import br.com.meliw4.projetointegrador.dto.response.ProdutoResponseDTO;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import br.com.meliw4.projetointegrador.exception.NotFoundException;
import br.com.meliw4.projetointegrador.repository.ProdutoRepository;
import br.com.meliw4.projetointegrador.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	private ProdutoRepository produtoRepository;
	private ProdutoVendedorRepository produtoVendedorRepository;

	ProdutoServiceImpl(ProdutoRepository produtoRepository, ProdutoVendedorRepository produtoVendedorRepository) {
		this.produtoRepository = produtoRepository;
		this.produtoVendedorRepository = produtoVendedorRepository;
	}

	@Override
	public List<ProdutoResponseDTO> findAllProdutos() {
		List<ProdutoResponseDTO> produtoResponseDTO = produtoRepository.findAll().stream()
			.map(ProdutoResponseDTO::toDTO)
			.collect(Collectors.toList());

		if (produtoResponseDTO.isEmpty()) {
			throw new NotFoundException("Não há produtos para a seleção");
		}

		return produtoResponseDTO;
	}

	@Override
	public List<ProdutoResponseDTO> findProdutoPorCategoria(Categoria categoria) {
		List<ProdutoResponseDTO> produtoResponseDTO = produtoRepository.findProdutoPorCategoria(categoria.name())
			.stream()
			.map(ProdutoResponseDTO::toDTO)
			.collect(Collectors.toList());

		if (produtoResponseDTO.isEmpty()) {
			throw new NotFoundException("Não há produtos para a seleção");
		}

		return produtoResponseDTO;
	}

	@Override
	public boolean validateProdutoExists(Long id) {
		if (!produtoRepository.existsById(id)) {
			return false;
		}
		return true;
	}

	@Override
	public void save(Produto produto) {
		produtoRepository.save(produto);
	}

	@Override
	public Produto getProdutoById(Long id) {
		return produtoRepository.findById(id).orElse(null);
	}

	public Map<ProdutoSetorResponse, ProdutoVendedorResponse> listaTodosOsLotes(Long id) {
		//List<ProdutoVendedor> produtoVendedors = this.produtoVendedorRepository.findProdutoVendedorByProduto_Id(id);
		//List<ProdutoSetorResponse> response = new ArrayList<ProdutoSetorResponse>();
		//List<ProdutoVendedorResponse> response = sanetizaORetorno(this.produtoVendedorRepository.findProdutoVendedorByProduto_Id(id));
		Map<ProdutoSetorResponse, ProdutoVendedorResponse> response = sanetizaORetorno(this.produtoVendedorRepository.findProdutoVendedorByProduto_Id(id));
		return response;
	}

	private Map<ProdutoSetorResponse, ProdutoVendedorResponse> sanetizaORetorno(List<ProdutoVendedor> produtoVendedor) {
		//List<ProdutoVendedorResponse> response = new ArrayList<>();
		Map<ProdutoSetorResponse, ProdutoVendedorResponse> response = new LinkedHashMap<>();
		for (ProdutoVendedor pv : produtoVendedor) {
			response.put(ProdutoSetorResponse.builder()
					.armazem_id(pv.getLote().getSetor().getArmazem().getId())
					.lote_id(pv.getLote().getSetor().getId())
					.build(),
				ProdutoVendedorResponse.builder()
					//.setorResponse(ProdutoSetorResponse.retornaOSetor(pv))
					.produto_id(pv.getProduto().getId())
					.produto_nome(pv.getProduto().getNome())
					.dataVencimento(pv.getDataVencimento())
					.quantidadeAtual(pv.getQuantidadeAtual())
					.nome_vendedor(pv.getVendedor().getNome())
					.build());
		}
		return response;
	}
}
