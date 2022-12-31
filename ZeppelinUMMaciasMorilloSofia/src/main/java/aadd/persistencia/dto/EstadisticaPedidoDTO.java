package aadd.persistencia.dto;

import java.io.Serializable;

import aadd.persistencia.jpa.bean.Restaurante;

public class EstadisticaPedidoDTO implements Serializable{ 
    private String res; //nombre del restaurante
    private Integer totalPedidos;
    private Integer numIncidencias;
    public EstadisticaPedidoDTO( String r, Integer total, Integer in) {
        super();
        this.res = r;
        this.totalPedidos = total;
        this.numIncidencias = in;
    }

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public Integer getTotalPedidos() {
		return totalPedidos;
	}

	public void setTotalPedidos(Integer totalPedidos) {
		this.totalPedidos = totalPedidos;
	}

	public Integer getNumIncidencias() {
		return numIncidencias;
	}

	public void setNumIncidencias(Integer numIncidencias) {
		this.numIncidencias = numIncidencias;
	}

    
    
}
