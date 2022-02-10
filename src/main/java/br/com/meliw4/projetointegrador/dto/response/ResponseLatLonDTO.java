package br.com.meliw4.projetointegrador.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseLatLonDTO {
	@JsonProperty("place_id")
	private Long placeId;
	private String lat;
	private String lon;
	@JsonProperty("display_name")
	private String endereco;
}
