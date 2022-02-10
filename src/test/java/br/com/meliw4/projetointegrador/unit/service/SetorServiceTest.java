package br.com.meliw4.projetointegrador.unit.service;

import br.com.meliw4.projetointegrador.dto.SetorDTO;
import br.com.meliw4.projetointegrador.entity.*;
import br.com.meliw4.projetointegrador.entity.enumeration.Categoria;
import br.com.meliw4.projetointegrador.exception.ArmazemException;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.exception.NotFoundException;
import br.com.meliw4.projetointegrador.repository.SetorRepository;
import br.com.meliw4.projetointegrador.response.SetorResponse;
import br.com.meliw4.projetointegrador.service.ArmazemService;
import br.com.meliw4.projetointegrador.service.SetorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class SetorServiceTest {

	private static SetorService setorService;
	private static SetorRepository setorRepository = Mockito.mock(SetorRepository.class);
	private static ArmazemService armazemService = Mockito.mock(ArmazemService.class);

	@BeforeAll
	public static void setUp() {
		setorService = new SetorService(
			setorRepository,
			armazemService
		);
	}

	@Test
	public void shouldThrowNotRemainingArmazemSpaceException() {
		List<Setor> setores = new ArrayList<>();
		SetorDTO payload = SetorDTO.builder()
			.armazemId(1l)
			.categoria(Categoria.FS)
			.volume(200.0)
			.build();
		Mockito.when(armazemService.findArmazemById(1l)).thenReturn(
			Armazem.builder()
				.volume(100.0)
				.build()
		);
		Mockito.when(setorRepository.findAll()).thenReturn(setores);
		assertThrows(
			ArmazemException.class,
			() -> setorService.salva(payload)
		);
	}

	@Test
	public void shouldSaveSetor() {
		List<Setor> setores = new ArrayList<>();
		SetorDTO payload = SetorDTO.builder()
			.armazemId(1l)
			.categoria(Categoria.FS)
			.volume(50.0)
			.build();
		Mockito.when(armazemService.findArmazemById(1l)).thenReturn(
			Armazem.builder()
				.volume(100.0)
				.build()
		);
		Mockito.when(setorRepository.findAll()).thenReturn(setores);
		assertDoesNotThrow(
			() -> setorService.salva(payload)
		);
	}

	@Test
	public void shouldReturnSetoresTotalVolume() {
		Setor setor = Setor.builder()
			.armazem(
				Armazem.builder()
					.id(1l)
					.build()
			)
			.volume(10.0)
			.build();
		List<Setor> setores = new ArrayList<>();
		setores.add(
			setor
		);
		setores.add(
			Setor.builder()
				.armazem(
					Armazem.builder()
						.id(2l)
						.build()
				)
				.volume(50.0)
				.build()
		);
		setores.add(
			Setor.builder()
				.armazem(
					Armazem.builder()
						.id(1l)
						.build()
				)
				.volume(20.0)
				.build()
		);
		Mockito.when(setorRepository.findAll()).thenReturn(setores);
		assertEquals(setorService.volumeTotalDosSetores(setor), 30.0);
	}

	@Test
	public void shouldReturnListSetorResponse() {
		List<Setor> setores = new ArrayList<>();
		List<Lote> lotes = new ArrayList<>();
		lotes.add(
			Lote.builder()
				.id(1l)
				.build()
		);
		setores.add(
			Setor.builder()
				.id(1l)
				.categoria(Categoria.FF)
				.armazem(
					Armazem.builder()
						.id(1l)
						.build()
				)
				.lotes(lotes)
				.volume(30.0)
				.build()
		);
		setores.add(
			Setor.builder()
				.id(2l)
				.categoria(Categoria.FF)
				.armazem(
					Armazem.builder()
						.id(1l)
						.build()
				)
				.lotes(lotes)
				.volume(30.0)
				.build()
		);
		Mockito.when(setorRepository.findAll()).thenReturn(setores);
		List<SetorResponse> response = setorService.retornaSetores();
		assertEquals(response.size(), 2);
		assertEquals(response.get(0).getId(), 1l);
		assertEquals(response.get(1).getId(), 2l);
	}

	@Test
	public void shouldReturnRemainingSetorArea() {
		List<ProdutoVendedor> anuncios = new ArrayList<>();
		anuncios.add(
			ProdutoVendedor.builder()
				.produto(
					Produto.builder()
						.volume(25.0)
						.build()
				)
				.quantidadeAtual(1)
				.build()
		);
		anuncios.add(
			ProdutoVendedor.builder()
				.produto(
					Produto.builder()
						.volume(15.0)
						.build()
				)
				.quantidadeAtual(1)
				.build()
		);
		List<Lote> lotes = new ArrayList<>();
		lotes.add(
			Lote.builder()
				.produtoVendedores(anuncios)
				.build()
		);
		Setor setor = Setor.builder()
			.volume(50.0)
			.lotes(lotes)
			.build();
		assertEquals(setorService.calculateRemainingSetorArea(setor), 10.0);
	}

	@Test
	public void shouldThrowNotEnoughSetorSpaceExcpetion() {
		assertThrows(
			BusinessValidationException.class,
			() -> setorService.validateEnoughRemainingVolume(10.0, 20.0)
		);
	}

	@Test
	public void shouldNotThrowNotEnoughSetorSpaceExcpetion() {
		assertDoesNotThrow(
			() -> setorService.validateEnoughRemainingVolume(30.0, 20.0)
		);
	}

	@Test
	public void shouldThrowInvalidSetorForArmazemException() {
		Setor setor = Setor.builder()
			.armazem(
				Armazem.builder()
					.id(2l)
					.build()
			)
			.build();
		assertThrows(
			BusinessValidationException.class,
			() -> setorService.validateSetorArmazem(setor, 1l)
		);
	}

	@Test
	public void shouldNotThrowInvalidSetorForArmazemException() {
		Setor setor = Setor.builder()
			.armazem(
				Armazem.builder()
					.id(1l)
					.build()
			)
			.build();
		assertDoesNotThrow(
			() -> setorService.validateSetorArmazem(setor, 1l)
		);
	}

	@Test
	public void shouldThrowInvalidSetorException() {
		assertThrows(
			BusinessValidationException.class,
			() -> setorService.findSetorById(1l)
		);
	}

	@Test
	public void shouldNotThrowInvalidSetorException() {
		Mockito.when(setorRepository.findById(2l)).thenReturn(Optional.of((Setor) Setor.builder().build()));
		assertDoesNotThrow(
			() -> setorService.findSetorById(2l)
		);
	}

	@Test
	public void shouldThrowInvalidArmazemException() {
		assertThrows(
			NotFoundException.class,
			() -> setorService.findSetorByArmazem_Id(1l)
		);
	}

	@Test
	public void shouldNotThrowInvalidArmazemException() {
		List<Setor> setores = new ArrayList<>();
		setores.add(
			Setor.builder().build()
		);
		Mockito.when(setorRepository.findSetorByArmazem_Id(1l)).thenReturn(
			Optional.of((List<Setor>) setores)
		);
		assertDoesNotThrow(
			() -> setorService.findSetorByArmazem_Id(1l)
		);
	}
}
