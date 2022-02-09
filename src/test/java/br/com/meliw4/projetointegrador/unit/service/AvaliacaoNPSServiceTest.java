package br.com.meliw4.projetointegrador.unit.service;

import static  org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import br.com.meliw4.projetointegrador.entity.Comprador;
import org.junit.jupiter.api.Test;
import br.com.meliw4.projetointegrador.dto.AvaliacaoNPSDTO;
import br.com.meliw4.projetointegrador.dto.CompradorDTO;
import br.com.meliw4.projetointegrador.entity.AvaliacaoNPS;
import br.com.meliw4.projetointegrador.repository.AvaliacaoNPSRepository;
import br.com.meliw4.projetointegrador.repository.CompradorRepository;
import br.com.meliw4.projetointegrador.service.AvaliacaoNPSService;
import br.com.meliw4.projetointegrador.service.CompradorService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class AvaliacaoNPSServiceTest {
	private static CompradorService compradorService = mock(CompradorService.class);
	private static CompradorRepository compradorRepository = mock(CompradorRepository.class);
	private static AvaliacaoNPSRepository rep = mock(AvaliacaoNPSRepository.class);
    private static AvaliacaoNPSService avaliacaoNPSService = new AvaliacaoNPSService(compradorService,compradorRepository,rep);

@Test
	void testSave(){
		AvaliacaoNPSDTO avaliacaoNPSDTO =AvaliacaoNPSDTO.builder()
			.nota(8)
			.dataAvaliacao(LocalDate.now())
			.comprador_id(1L)
			.build();
			when(compradorRepository.getById(1L)).thenReturn(new Comprador());
		AvaliacaoNPS response = avaliacaoNPSService.save(avaliacaoNPSDTO);
		assertEquals(8,response.getNota());
	}
}
