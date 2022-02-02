package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.dto.ProdutoDTO;
import br.com.meliw4.projetointegrador.dto.SetorDTO;
import br.com.meliw4.projetointegrador.entity.Lote;
import br.com.meliw4.projetointegrador.entity.ProdutoVendedor;
import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.ArmazemRepository;
import br.com.meliw4.projetointegrador.repository.SetorRepository;
import br.com.meliw4.projetointegrador.response.SetorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetorService {

	private SetorRepository setorRepository;

	public SetorService(SetorRepository setorRepository) {
		this.setorRepository = setorRepository;
	}

	@Autowired
	private ArmazemRepository armazemRepository;
	@Autowired
	SetorDTO setorDTO;

	public Setor salva(Setor payload) {
		if (possuiEspaco(payload)) {
			Setor setor = setorRepository.save(payload);
			return setor;
		} else
			throw new IllegalArgumentException("Espaço não disponível no armazem: " + payload.getArmazem().getNome());
	}

	public List<Setor> retornaTodosOsSetores() {
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
}

