package br.com.meliw4.projetointegrador.unit.service;

import br.com.meliw4.projetointegrador.entity.AvaliacaoNPS;
import br.com.meliw4.projetointegrador.repository.AvaliacaoNPSRepository;
import br.com.meliw4.projetointegrador.repository.CompradorRepository;
import br.com.meliw4.projetointegrador.repository.LoteRepository;
import br.com.meliw4.projetointegrador.service.AvaliacaoNPSService;
import br.com.meliw4.projetointegrador.service.CompradorService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AvaliacaoNPSServiceTest {


	CompradorService compradorService= Mockito.mock(CompradorService.class);
	CompradorRepository compradorRepository = Mockito.mock(CompradorRepository.class);
	AvaliacaoNPSRepository rep = Mockito.mock(AvaliacaoNPSRepository.class);

	@Test
	public void deveSalvarUmaAvaliacaoNPS(){
		rep.save(AvaliacaoNPS.builder().build());
	}

	@Test
	public void registrarAvaliacao(){
		AvaliacaoNPS av = new AvaliacaoNPS();
		av.setNota(10);
		av.setDataAvaliacao(LocalDate.of(2000,02,01));
		rep.save(av);
	}

	@Test
	public void listaTodasAvaliacoes(){
		rep.findAll();
	}


}
