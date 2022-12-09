package aadd.persistencia.dto;

import java.time.LocalDateTime;

import aadd.persistencia.mongo.bean.TipoEstado;

public class EstadoPedidoDTO {
	
	private TipoEstado nombreEstado;
	private LocalDateTime hora;
	
	public EstadoPedidoDTO(TipoEstado tipoEstado, LocalDateTime localDateTime) {
		this.nombreEstado=tipoEstado;
		this.hora=localDateTime;
	}

	public TipoEstado getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(TipoEstado nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public LocalDateTime getHora() {
		return hora;
	}

	public void setHora(LocalDateTime hora) {
		this.hora = hora;
	}
	
	
	

}
