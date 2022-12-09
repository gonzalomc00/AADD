package aadd.persistencia.mongo.bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EstadoPedido implements Serializable {
	private TipoEstado estado;
	private LocalDateTime fechaEstado;
	
	//getters y setters

	public TipoEstado getEstado() {
		return estado;
	}
	public void setEstado(TipoEstado estado) {
		this.estado = estado;
	}
	public LocalDateTime getFechaEstado() {
		return fechaEstado;
	}
	public void setFechaEstado(LocalDateTime fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
	
	
	
	
	

}
