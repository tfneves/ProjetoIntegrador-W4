package br.com.meliw4.projetointegrador.controller;
import br.com.meliw4.projetointegrador.dto.RepresentanteDTO;
import br.com.meliw4.projetointegrador.entity.Representante;
import br.com.meliw4.projetointegrador.service.RepresentanteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class RepresentanteController {

    private RepresentanteService representanteService;

    @PostMapping(path = "/representante")
    public Representante registerRepresentante(@RequestBody @Valid RepresentanteDTO representanteDTO, UriComponentsBuilder uriBuilder) {
        URI uri = uriBuilder.path("").build().toUri();
        Representante representante = RepresentanteDTO.convert(representanteDTO);
        return representanteService.register(representante);
    }
}
