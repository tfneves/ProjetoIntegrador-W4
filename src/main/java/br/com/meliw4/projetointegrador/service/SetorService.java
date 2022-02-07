package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.SetorDTO;
import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.entity.Lote;
import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.exception.NotFoundException;
import br.com.meliw4.projetointegrador.exception.ArmazemException;
import br.com.meliw4.projetointegrador.repository.SetorRepository;
import br.com.meliw4.projetointegrador.response.SetorResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetorService {

	private SetorRepository setorRepository;
	private ArmazemService armazemService;

	public SetorService(SetorRepository setorRepository, ArmazemService armazemService) {
		this.setorRepository = setorRepository;
		this.armazemService = armazemService;
	}

	public SetorDTO salva(SetorDTO payload) {
		Armazem armazem = this.armazemService.findArmazemById(payload.getArmazemId());
		Setor setor = SetorDTO.converte(payload, armazem);
		if (possuiEspaco(setor)) {
			return SetorDTO.converte(setorRepository.save(setor));
		} else
			throw new ArmazemException("Espaço não disponível no armazem: " + setor.getArmazem().getNome());
	}

	public List<Setor> retornaTodosOsSetores() {
		List<Setor> setores = this.setorRepository.findAll();
		return this.setorRepository.findAll();
	}

	public List<SetorResponse> retonraSetores() {
		List<Setor> setores = setorRepository.findAll();
		List<SetorResponse> response = new ArrayList<>();
		for (Setor setor : setores) {
			response.add(SetorResponse.builder()
					.id(setor.getId())
					.categoria(setor.getCategoria())
					.armazem_id(setor.getArmazem().getId())
					.lote_id(setor.getLotes().stream().map(a -> a.getId()).collect(Collectors.toList()))
					.volume(setor.getVolume())
					.build());
		}
		return response;
	}

	private Boolean possuiEspaco(Setor setor) {
		Double volumeTotalSetores = volumeTotalDosSetores(setor);
		Double volumeTotalArmazem = setor.getArmazem().getVolume();
		return setor.getVolume() <= (volumeTotalArmazem - volumeTotalSetores);
	}

	private Double volumeResante(Setor setor) {
		return setor.getVolume() - volumeTotalDosSetores(setor);
	}

	private Double volumeTotalDosSetores(Setor setor) {
		return setorRepository.findAll()
				.stream()
				.filter(s -> s.getArmazem() == setor.getArmazem())
				.map(s -> s.getVolume())
				.reduce((n1, n2) -> n1 + n2)
				.orElse(0.0);
	}

	public Double calculateRemainingSetorArea(Setor setor) {
		Double totalVolume = 0.0;
		// TODO Usar stream
		List<Lote> lotes = setor.getLotes();
		for (Lote lote : lotes) {
			for (ProdutoVendedor produtoVendedor : lote.getProdutoVendedores()) {
				totalVolume += produtoVendedor.getProduto().getVolume() * produtoVendedor.getQuantidadeAtual();
			}
		}
		return setor.getVolume() - totalVolume;
	}

	public Setor findSetorById(Long id) {
		return setorRepository
				.findById(id)
				.orElseThrow(() -> new BusinessValidationException("O setor com id " + id + " não existe."));
	}

	public void validateEnoughRemainingVolume(Double setorRemainingVolume, Double produtosTotalVolume) {
		if (setorRemainingVolume < produtosTotalVolume) {
			throw new BusinessValidationException("O volume restante do setor não comporta o volume do lote.");
		}
	}

	public void validateSetorArmzem(Setor setor, Long armazemId) {
		if (!setor.getArmazem().getId().equals(armazemId)) {
			throw new BusinessValidationException("O setor não pertence a esse armazém.");
		}
	}

	public List<Setor> findSetorByArmazem_Id(Long armazemId) {
		return this.setorRepository.findSetorByArmazem_Id(armazemId)
				.orElseThrow(() -> new NotFoundException("Não há setores ocupados para o armazém selecionado."));
	}
}
