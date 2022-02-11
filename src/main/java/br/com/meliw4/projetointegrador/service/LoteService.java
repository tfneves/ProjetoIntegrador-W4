package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.LoteDTO;
import br.com.meliw4.projetointegrador.dto.LoteUpdateDTO;
import br.com.meliw4.projetointegrador.dto.ProdutoDTO;
import br.com.meliw4.projetointegrador.dto.ProdutoUpdateDTO;
import br.com.meliw4.projetointegrador.entity.*;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import br.com.meliw4.projetointegrador.entity.enumeration.Ordenamento;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.LoteRepository;
import br.com.meliw4.projetointegrador.response.LoteProdutosVencimentoResponse;
import br.com.meliw4.projetointegrador.response.LotesSetorVencimentoResponse;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoteService {

	private LoteRepository loteRepository;
	private ArmazemService armazemService;
	private VendedorService vendedorService;
	private SetorService setorService;
	private RepresentanteService representanteService;
	private ProdutoService produtoService;
	private RegistroLoteService registroLoteService;
	private ProdutoVendedorService produtoVendedorService;

	public LoteService(LoteRepository loteRepository, ArmazemService armazemService, VendedorService vendedorService,
			SetorService setorService, RepresentanteService representanteService,
			ProdutoService produtoService, RegistroLoteService registroLoteService,
			ProdutoVendedorService produtoVendedorService) {
		this.loteRepository = loteRepository;
		this.armazemService = armazemService;
		this.vendedorService = vendedorService;
		this.setorService = setorService;
		this.representanteService = representanteService;
		this.produtoService = produtoService;
		this.registroLoteService = registroLoteService;
		this.produtoVendedorService = produtoVendedorService;
	}

	public void registerLote(LoteDTO loteDTO) {
		Armazem armazem = armazemService.findArmazemById(loteDTO.getArmazemId());
		Vendedor vendedor = vendedorService.findVendedorById(loteDTO.getVendedorId());
		Representante representante = representanteService.findRepresentanteById(loteDTO.getRepresentanteId());
		representanteService.validateRepresentanteArmazem(representante, loteDTO.getArmazemId());
		Setor setor = setorService.findSetorById(loteDTO.getSetorId());
		setorService.validateSetorArmazem(setor, loteDTO.getArmazemId());
		validateProdutosDTOCategoria(setor, loteDTO.getProdutosDTO());
		Double setorRemainingVolume = setorService.calculateRemainingSetorArea(setor);
		Double produtosDTOTotalVolume = calculateProdutosDTOTotalVolume(loteDTO.getProdutosDTO());
		setorService.validateEnoughRemainingVolume(setorRemainingVolume, produtosDTOTotalVolume);
		checkProdutosDTO(loteDTO.getProdutosDTO());
		Lote lote = LoteDTO.convert(loteDTO, setor, representante);
		save(lote);
		createRegister(lote, representante, vendedor);
		saveAnuncios(lote, loteDTO.getProdutosDTO(), vendedor);
	}

	public List<ProdutoDTO> updateLote(LoteUpdateDTO loteUpdateDTO) {
		validateLoteExists(loteUpdateDTO.getLoteId());
		validateProdutosUpdate(loteUpdateDTO.getProdutosUpdateDTO());
		return updateLoteProdutos(loteUpdateDTO.getLoteId(), loteUpdateDTO.getProdutosUpdateDTO());
	}

	public LotesSetorVencimentoResponse getLotesBySetorFilterProdutosByDays(Long setorId, Integer days) {
		Setor setor = setorService.findSetorById(setorId);
		List<Lote> lotes = setor.getLotes();
		List<LoteProdutosVencimentoResponse> responseList = new ArrayList<>();
		LocalDate today = LocalDate.now();
		LocalDate limitDate = today.plusDays(days);
		for (Lote lote : lotes) {
			getFilteredProdutosByLote(lote, today, limitDate, responseList);
		}
		responseList.sort(Comparator.comparing(LoteProdutosVencimentoResponse::getDataVencimento));
		return LotesSetorVencimentoResponse.builder()
				.estoque(responseList)
				.build();
	}

	public void getFilteredProdutosByLote(Lote lote, LocalDate today, LocalDate limitDate,
			List<LoteProdutosVencimentoResponse> responseList) {
		List<ProdutoVendedor> anuncios = lote.getProdutoVendedores();
		LocalDate dueDate;
		for (ProdutoVendedor anuncio : anuncios) {
			dueDate = anuncio.getDataVencimento();
			if ((dueDate.isAfter(today) && dueDate.isBefore(limitDate)) || dueDate.isEqual(today)
					|| dueDate.isEqual(limitDate))
				responseList.add(
						LoteProdutosVencimentoResponse.builder()
								.setorId(lote.getSetor().getId())
								.loteId(lote.getId())
								.anuncioId(anuncio.getId())
								.produtoId(anuncio.getProduto().getId())
								.categoriaProduto(anuncio.getProduto().getProdutoCategoria().getCategoria())
								.dataVencimento(dueDate)
								.quantidade(anuncio.getQuantidadeAtual())
								.build());
		}
	}

	public List<LoteProdutosVencimentoResponse> getProdutosInSetorsOrderedAndFilteredByDueDate(
			Categoria categoria, Ordenamento ordenamento, Integer days) {
		// Considerando armazém do representante
		// TODO: auth representante
		Long armazemId = 1L;
		List<Setor> setores = this.setorService.findSetorByArmazem_Id(armazemId);
		// Filtra setor por categoria
		if (categoria != null) {
			// TODO: Passagem por referência
			setores = filterByCategory(setores, categoria);
		}

		List<LoteProdutosVencimentoResponse> responseList = new ArrayList<>();
		for (Setor setor : setores) {
			responseList.addAll(getLotesBySetorFilterProdutosByDays(setor.getId(), days).getEstoque());
		}

		// Filtra Produtos por período estipulado em dias
		// TODO: Passagem por referência
		responseList = filterDueDateUntilDate(responseList, days);

		// Ordena Produtos por data
		if (ordenamento != null) {
			// TODO: Passagem por referência
			responseList = orderByDate(responseList, ordenamento);
		}

		return responseList;
	}

	public List<Setor> filterByCategory(List<Setor> setores, Categoria categoria) {
		return setores.stream()
				.filter(s -> s.getCategoria().equals(categoria))
				.collect(Collectors.toList());
	}

	public List<LoteProdutosVencimentoResponse> orderByDate(List<LoteProdutosVencimentoResponse> responseList,
			Ordenamento ordenador) {
		if (ordenador.equals(Ordenamento.asc)) {
			// Ordenado ascendente
			return responseList.stream()
					.sorted(Comparator.comparing(LoteProdutosVencimentoResponse::getDataVencimento))
					.collect(Collectors.toList());
		} else {
			// Ordenado decrescente
			return responseList.stream()
					.sorted(Comparator.comparing(LoteProdutosVencimentoResponse::getDataVencimento).reversed())
					.collect(Collectors.toList());
		}
	}

	public List<LoteProdutosVencimentoResponse> filterDueDateUntilDate(
			List<LoteProdutosVencimentoResponse> loteProdutosVencimentoResponses, Integer days) {
		return loteProdutosVencimentoResponses.stream()
				.filter(l -> ChronoUnit.DAYS.between(LocalDate.now(), l.getDataVencimento()) <= days)
				.collect(Collectors.toList());
	}

	public void validateLoteExists(Long id) {
		if (!loteRepository.existsById(id)) {
			throw new BusinessValidationException("O lote com id " + id + " não existe.");
		}
	}

	public void validateProdutosDTOCategoria(Setor setor, List<ProdutoDTO> produtosDTO) {

		for (ProdutoDTO produtoDTO : produtosDTO) {
			if (produtoDTO.getProdutoCategoria().getCategoria() != setor.getCategoria()) {
				throw new BusinessValidationException(
						"A categoria do setor não é adequada para todos os produtos do lote.");
			}
		}
	}

	public List<Produto> checkProdutosDTO(List<ProdutoDTO> produtosDTO) {
		List<Produto> produtos = new ArrayList<>();
		try {
			for (ProdutoDTO produtoDTO : produtosDTO) {
				if (!produtoService.validateProdutoExists(produtoDTO.getId())) {
					Produto produto = ProdutoDTO.convert(produtoDTO);
					produtoService.save(produto);
					produtos.add(produto);
					produtoDTO.setId(produto.getId());
				} else {
					produtos.add(produtoService.findById(produtoDTO.getId()));
				}
			}
			return produtos;
		}catch (Exception e){
			throw new BusinessValidationException("Erro ao cadastrar o lote");
		}
	}

	public Double calculateProdutosDTOTotalVolume(List<ProdutoDTO> produtosDTO) {
		Double totalVolume = 0.0;
		for (ProdutoDTO produtoDTO : produtosDTO) {
			totalVolume += produtoDTO.getVolume() * produtoDTO.getQuantidadeAtual();
		}
		return totalVolume;
	}

	public void save(Lote lote) {
		loteRepository.save(lote);
	}

	public void createRegister(Lote lote, Representante representante, Vendedor vendedor) {
		RegistroLote registroLote = RegistroLote.builder()
				.lote(lote)
				.representante(representante)
				.vendedor(vendedor)
				.build();
		registroLoteService.save(registroLote);
	}

	public void saveAnuncios(Lote lote, List<ProdutoDTO> produtosDTO, Vendedor vendedor) {
		for (ProdutoDTO produtoDTO : produtosDTO) {
			validatePreco(produtoDTO.getPreco());
			ProdutoVendedor produtoVendedor = ProdutoDTO
					.convert(produtoDTO, vendedor, produtoService.findById(produtoDTO.getId()), lote);
			produtoVendedorService.save(produtoVendedor);
		}
	}

	public List<ProdutoDTO> updateLoteProdutos(Long loteId, List<ProdutoUpdateDTO> produtosUpdateDTO) {
		// TODO Usar stream
		List<ProdutoDTO> produtosDTO = new ArrayList<>();
		List<ProdutoVendedor> produtosVendedor = new ArrayList<>();
		Integer quantidadeAtual = 0;
		Integer quantidadeRetira = 0;
		for (ProdutoUpdateDTO produtoUpdateDTO : produtosUpdateDTO) {
			ProdutoVendedor produtoVendedor = produtoVendedorService.findByLoteIdAndProdutoId(
					loteId, produtoUpdateDTO.getId());
			if (produtoVendedor == null) {
				throw new BusinessValidationException(
						"Produto não cadastrado pelo vendedor no lote solicitado.");
			}
			quantidadeAtual = produtoVendedor.getQuantidadeAtual();
			quantidadeRetira = produtoUpdateDTO.getQuantidadeRetira();
			if (quantidadeAtual < quantidadeRetira) {
				throw new BusinessValidationException(
						"A quantidade a retirar não deve exceder a quantidade atual de um produto.");
			}
			produtoVendedor.setQuantidadeAtual(quantidadeAtual - quantidadeRetira);
			produtosVendedor.add(produtoVendedor);
		}
		for (ProdutoVendedor produtoVendedor : produtosVendedor) {
			produtoVendedorService.save(produtoVendedor);
			produtosDTO.add(ProdutoDTO.convert(produtoVendedor));
		}
		return produtosDTO;
	}

	public void validateProdutosUpdate(List<ProdutoUpdateDTO> produtosUpdateDTO) {
		// TODO Usar stream
		for (ProdutoUpdateDTO produtoUpdateDTO : produtosUpdateDTO) {
			if (!produtoService.validateProdutoExists(produtoUpdateDTO.getId())) {
				throw new BusinessValidationException("O produto de id " + produtoUpdateDTO.getId() + " não existe.");
			}
		}
	}

	public void validatePreco(BigDecimal preco) {
		if (BigDecimal.ZERO.compareTo(preco) >= 0) {
			throw new BusinessValidationException("Preço deve ser positivo.");
		}
	}
}
