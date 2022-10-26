package aadd.persistencia.mongo.bean;

import java.io.Serializable;

public class ItemPedido implements Serializable {
	//TODO: Declarar ID?
	private Integer cantidad;
	private Double precioTotal;
	private Integer plato;

	// getters y setters

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public Integer getPlato() {
		return plato;
	}

	public void setPlato(Integer plato) {
		this.plato = plato;
	}

}
