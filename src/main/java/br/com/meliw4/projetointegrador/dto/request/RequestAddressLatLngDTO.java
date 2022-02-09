package br.com.meliw4.projetointegrador.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class RequestAddressLatLngDTO {
	@JsonProperty("place_id")
	private Long placeId;
	private String lat;
	private String lon;
	@JsonProperty("type")
	private String tipoEndereco;
	private String importance;

	@Override
	public String toString() {
		return "RequestAddressLatLngDTO [importance=" + importance + ", lat=" + lat + ", lon=" + lon + ", placeId="
				+ placeId + ", tipoEndereco=" + tipoEndereco + "]";
	}
}
