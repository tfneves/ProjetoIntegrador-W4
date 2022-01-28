package br.com.meliw4.projetointegrador.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @GetMapping("/ping")
    public String Ping(){
        return "Pong";
    }
}
