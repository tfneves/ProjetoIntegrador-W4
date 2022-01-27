package br.com.meliw4.projetointegrador.controller;

import br.com.meliw4.projetointegrador.dto.SetorDTO;
import java.net.URI;
import java.util.List;

import br.com.meliw4.projetointegrador.entity.Setor;
import br.com.meliw4.projetointegrador.service.SetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping
public class SetorController {

    @Autowired
    SetorService setorService;

    @GetMapping("setor")
    public ResponseEntity<List<Setor>> devolveTodosOsSetores(){
        return ResponseEntity.ok(setorService.retornaTodosOsSetores());
    }

    @PostMapping("/setor/novo")
    public ResponseEntity<SetorDTO> cadastraSetor(SetorDTO payload, UriComponentsBuilder uriBuilder){
        URI uri = uriBuilder.path("/setor").build().toUri();
        return ResponseEntity.created(uri).body(setorService.salva(payload));
    }
}
