package br.com.meliw4.projetointegrador.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseLatLonDTO implements Serializable {
	@JsonProperty("place_id")
	private Long placeId;
	private String lat;
	private String lon;
	@JsonProperty("display_name")
	private String endereco;
}
