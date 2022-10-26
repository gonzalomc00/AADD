package aadd.persistencia.mongo.bean;

import java.io.Serializable;
import java.time.LocalDate;

public class EstadoPedido implements Serializable {
	private TipoEstado estado;
	private LocalDate fechaEstado;
	
	//getters y setters

	public TipoEstado getEstado() {
		return estado;
	}
	public void setEstado(TipoEstado estado) {
		this.estado = estado;
	}
	public LocalDate getFechaEstado() {
		return fechaEstado;
	}
	public void setFechaEstado(LocalDate fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
	
	
	
	
	

}
