package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.StatusPedido;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.StatusPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusPedidoService {

	@Autowired
	StatusPedidoRepository statusPedidoRepository;


	public StatusPedido findStatusCodeWithName(String statusCodeName) {
		if (statusPedidoRepository.findByStatusCode(statusCodeName) == null){
			throw new BusinessValidationException("StatusCode informado não está cadastrado");
		}
		return statusPedidoRepository.findByStatusCode(statusCodeName);
	}
}
