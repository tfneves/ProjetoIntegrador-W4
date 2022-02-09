package br.com.meliw4.projetointegrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.meliw4.projetointegrador.service.Dummy1;

@SpringBootApplication
public class ProjetoIntegradorApplication {

	public static void main(String[] args) {
		// SpringApplication.run(ProjetoIntegradorApplication.class, args);
		Dummy1.connect();
	}

}
