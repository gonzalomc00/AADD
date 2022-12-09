package aadd.persistencia.dto;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;

public class PedidoDTO {
	
	
	//ID del pedido
	private ObjectId id;
	
	private String nombreCliente;
	private String nombreRestaurante;
	private LocalDateTime fechaHora;
	private LocalDateTime fechaEsperado;
	private String comentario;
	private String datosDireccion;
	private Double importe;
	private String nombreRepartidor;
	private List<ItemPedidoDTO> items;
	private List<EstadoPedidoDTO> estados;

	
	
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
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}
	public LocalDateTime getFechaEsperado() {
		return fechaEsperado;
	}
	public void setFechaEsperado(LocalDateTime fechaEsperado) {
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
	public List<EstadoPedidoDTO> getEstados() {
		return estados;
	}
	public void setEstado(List<EstadoPedidoDTO> estados) {
		this.estados = estados;
	}
	public void addEstado(EstadoPedidoDTO estado) {

		if(estados==null) {
			estados= new LinkedList<EstadoPedidoDTO>();
		}
		estados.add(estado);
		
		
	}
	
	public EstadoPedidoDTO getUltimoEstado() {
		
		return estados.get(estados.size()-1);
		
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	

}
