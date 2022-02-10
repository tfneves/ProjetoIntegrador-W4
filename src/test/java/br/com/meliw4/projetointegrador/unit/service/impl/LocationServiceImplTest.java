package br.com.meliw4.projetointegrador.unit.service.impl;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.com.meliw4.projetointegrador.dto.response.ArmazemResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.ResponseAddressLatLonDTO;
import br.com.meliw4.projetointegrador.dto.response.ResponseLatLonDTO;
import br.com.meliw4.projetointegrador.entity.Armazem;
import br.com.meliw4.projetointegrador.exception.InternalServerErrorException;
import br.com.meliw4.projetointegrador.exception.NotFoundException;
import br.com.meliw4.projetointegrador.service.ArmazemService;
import br.com.meliw4.projetointegrador.service.LocationService;
import br.com.meliw4.projetointegrador.service.impl.LocationServiceImpl;

public class LocationServiceImplTest {
	private static ArmazemService armazemService = mock(ArmazemService.class);
	private static LocationService locationService;
	private static List<Armazem> armazens = new ArrayList<>();

	@BeforeAll
	static void setUp() {
		locationService = new LocationServiceImpl(armazemService);
		armazens = makeArmazens();
	}

	private static List<Armazem> makeArmazens() {
		Armazem armazem1 = Armazem.builder()
				.nome("Armazem 1").volume(100d)
				.id(1L).lat(-23.3496144d).lon(-46.8760845d)
				.build();
		Armazem armazem2 = Armazem.builder()
				.nome("Armazem 2").volume(200d)
				.id(1L).lat(-23.5582481d).lon(-46.709784d)
				.build();
		Armazem armazem3 = Armazem.builder()
				.nome("Armazem 3").volume(200d)
				.id(1L).lat(-23.7380938d).lon(-46.496416d)
				.build();
		armazens.add(armazem1);
		armazens.add(armazem2);
		armazens.add(armazem3);

		return armazens;
	}

	@Test
	void shouldFindArmazemMaisProximo() {
		LocationService spyLocationService = spy(locationService);

		when(armazemService.findAll()).thenReturn(armazens);
		doReturn(ResponseAddressLatLonDTO.builder()
				.placeId(1L).lat("-23.5649267").lon("-46.6519566")
				.endereco("endereco").importance("importance").build())
						.when(spyLocationService).findLocation(anyString());
		doReturn(ResponseLatLonDTO.builder()
				.placeId(127064L).lat("-23.3496144").lon("-46.8760845")
				.endereco("Endereco Lat Lon").build())
						.when(spyLocationService).findLocation(anyString(), anyString());

		ArmazemResponseDTO response = spyLocationService.findArmazemMaisProximo("userEndereco");
		assertEquals(armazens.get(1).getVolume(), response.getVolume());

	}

	@Test
	void shouldResolveMinDistance() {
		LocationService spyLocationService = spy(locationService);

		when(armazemService.findAll()).thenReturn(armazens);
		doReturn(ResponseAddressLatLonDTO.builder()
				.placeId(1L).lat("-23.5649267").lon("-46.6519566")
				.endereco("endereco").importance("importance").build())
						.when(spyLocationService).findLocation(anyString());
		doReturn(ResponseLatLonDTO.builder()
				.placeId(127064L).lat("-23.3496144").lon("-46.8760845")
				.endereco("Endereco Lat Lon").build())
						.when(spyLocationService).findLocation(anyString(), anyString());

		List<ArmazemResponseDTO> response = spyLocationService.resolveMinDistance("userEndereco");
		assertEquals("Endereco Lat Lon", response.get(0).getEndereco());
	}

	@Test
	void shouldThrowNotFoundExceptionWhenEmptyListArmazens() {
		LocationService spyLocationService = spy(locationService);
		List<Armazem> emptyListArmazens = new ArrayList<>();

		when(armazemService.findAll()).thenReturn(emptyListArmazens);
		doReturn(ResponseAddressLatLonDTO.builder()
				.placeId(1L).lat("-23.5649267").lon("-46.6519566")
				.endereco("userEndereco").importance("importance").build())
						.when(spyLocationService).findLocation(anyString());
		doReturn(ResponseLatLonDTO.builder()
				.placeId(127064L).lat("-23.3496144").lon("-46.8760845")
				.endereco("Endereco Lat Lon").build())
						.when(spyLocationService).findLocation(anyString(), anyString());

		assertThrows(NotFoundException.class, () -> spyLocationService.resolveMinDistance("userEndereco"),
				"Não localizado armazém para a região.");
	}

	@Test
	void shouldThrowBusinessValidationExceptionWhenEmptyArmazemLatLon() {
		LocationService spyLocationService = spy(locationService);
		List<Armazem> nullListArmazens = new ArrayList<>();
		nullListArmazens.add(new Armazem());
		nullListArmazens.add(new Armazem());
		nullListArmazens.add(new Armazem());

		when(armazemService.findAll()).thenReturn(nullListArmazens);
		doReturn(ResponseAddressLatLonDTO.builder()
				.placeId(1L).lat("-23.5649267").lon("-46.6519566")
				.endereco("userEndereco").importance("importance").build())
						.when(spyLocationService).findLocation(anyString());
		doReturn(ResponseLatLonDTO.builder()
				.placeId(127064L).lat("-23.3496144").lon("-46.8760845")
				.endereco("Endereco Lat Lon").build())
						.when(spyLocationService).findLocation(anyString(), anyString());

		assertThrows(InternalServerErrorException.class, () -> spyLocationService.resolveMinDistance("userEndereco"),
				"Erro ao executar serviço de localização.");
	}
}
