package br.com.meliw4.projetointegrador.service;

import br.com.meliw4.projetointegrador.entity.StatusPedido;
import br.com.meliw4.projetointegrador.entity.Vendedor;
import br.com.meliw4.projetointegrador.exception.BusinessValidationException;
import br.com.meliw4.projetointegrador.repository.StatusPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusPedidoService {

	StatusPedidoRepository statusPedidoRepository;

	public StatusPedidoService(StatusPedidoRepository statusPedidoRepository) {
		this.statusPedidoRepository = statusPedidoRepository;
	}


	public StatusPedido findStatusCodeWithName(String statusCodeName) {
		if (statusPedidoRepository.findByStatusCode(statusCodeName) == null) {
			throw new BusinessValidationException("StatusCode informado não está cadastrado");
		}
		return statusPedidoRepository.findByStatusCode(statusCodeName);
	}

	public StatusPedido findStatusPedidoById(Long id) {
		return statusPedidoRepository
			.findById(id)
			.orElseThrow(() -> new BusinessValidationException("O statusPedido com id " + id + " não existe."));
	}
}
