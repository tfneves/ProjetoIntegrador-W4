package br.com.meliw4.projetointegrador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.meliw4.projetointegrador.dto.response.ArmazemResponseDTO;
import br.com.meliw4.projetointegrador.dto.response.ResponseAddressLatLonDTO;
import br.com.meliw4.projetointegrador.service.LocationService;

@RestController
@RequestMapping("/api/v1")
public class LocationController {

	@Autowired
	LocationService locationService;

	/**
	 * Rota para seleção de armazém mais próximo ao endereço solicitado
	 *
	 * @param userEndereco
	 * @return Armazém
	 */
	@GetMapping(path = "/warehouse/closest")
	public ResponseEntity<ArmazemResponseDTO> findClosestArmazemToUser(
			@RequestParam(value = "address") final String userEndereco) {
		return ResponseEntity.ok(locationService.findArmazemMaisProximo(userEndereco));
	}

	/**
	 * Rota para seleção de armazéns por critério de proximidade ao endereço
	 * solicitado
	 *
	 * @param userEndereco
	 * @return Lista de Armazéns
	 */
	@GetMapping(path = "/warehouse/closestlist")
	public ResponseEntity<List<ArmazemResponseDTO>> findListClosestArmazensToUser(
			@RequestParam(value = "address") final String userEndereco) {
		return ResponseEntity.ok(locationService.resolveMinDistance(userEndereco));
	}

	/**
	 * Rota para listagem de informações relevantes ao endereço solicitado
	 *
	 * @param userEndereco
	 * @return Informações de API externa para o endereço solicitado
	 */
	@GetMapping(path = "/warehouse/location")
	public ResponseEntity<ResponseAddressLatLonDTO> findLocationBasedOnUserAddress(
			@RequestParam(value = "address") final String userEndereco) {
		return ResponseEntity.ok(locationService.findLocation(userEndereco));
	}
}
