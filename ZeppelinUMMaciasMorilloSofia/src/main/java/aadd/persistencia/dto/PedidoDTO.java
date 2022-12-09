package aadd.persistencia.dto;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class PedidoDTO {
	
	private String nombreCliente;
	private String nombreRestaurante;
	private LocalDate fechaHora;
	private LocalDate fechaEsperado;
	private String comentario;
	private String datosDireccion;
	private Double importe;
	private String nombreRepartidor;
	private List<ItemPedidoDTO> items;
	
	
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getNombreRestaurante() {
		return nombreRestaurante;
	}
	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}
	public LocalDate getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDate fechaHora) {
		this.fechaHora = fechaHora;
	}
	public LocalDate getFechaEsperado() {
		return fechaEsperado;
	}
	public void setFechaEsperado(LocalDate fechaEsperado) {
		this.fechaEsperado = fechaEsperado;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getDatosDireccion() {
		return datosDireccion;
	}
	public void setDatosDireccion(String datosDireccion) {
		this.datosDireccion = datosDireccion;
	}
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	public String getNombreRepartidor() {
		return nombreRepartidor;
	}
	public void setNombreRepartidor(String nombreRepartidor) {
		this.nombreRepartidor = nombreRepartidor;
	}
	public List<ItemPedidoDTO> getItems() {
		return items;
	}
	public void setItems(List<ItemPedidoDTO> items) {
		this.items = items;
	}
	
	public void addItem(ItemPedidoDTO item) {
		if(items==null) {
			items=new LinkedList<ItemPedidoDTO>();
		}
		items.add(item);
	}
	
	

}
