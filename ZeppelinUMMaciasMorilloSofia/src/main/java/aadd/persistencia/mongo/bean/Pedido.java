package aadd.persistencia.mongo.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class Pedido implements Serializable {
	@BsonId
	private ObjectId id;
	private Integer cliente;
	private Integer restaurante;
	//TODO: fechas calculadas en el método de realizar pedido
	private LocalDateTime fechaHora;
	private LocalDateTime fechaEsperado;
	private String comentario;
	private String datosDireccion; // TODO: Preguntar si es direccion o String (seg String)
	//TODO: importe se calcula dentro??
	private Double importe;
	private Integer repartidor;
	private List<EstadoPedido> estados;
	private List<ItemPedido> items;
	
	//getters y setters

	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public Integer getCliente() {
		return cliente;
	}
	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}
	public Integer getRestaurante() {
		return restaurante;
	}
	public void setRestaurante(Integer restaurante) {
		this.restaurante = restaurante;
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
	public Integer getRepartidor() {
		return repartidor;
	}
	public void setRepartidor(Integer repartidor) {
		this.repartidor = repartidor;
	}
	public List<EstadoPedido> getEstados() {
		return estados;
	}
	public void setEstados(List<EstadoPedido> estados) {
		this.estados = estados;
	}
	public List<ItemPedido> getItems() {
		return items;
	}
	public void setItems(List<ItemPedido> items) {
		this.items = items;
	}
	//TODO: asi esta bien?
	public void addItem(ItemPedido ip) {
		if(items==null) {
			items=new LinkedList<ItemPedido>();
		}
		items.add(ip);
	}
	
	public void addEstado(EstadoPedido ep) {
		if(estados==null) {
			estados= new LinkedList<EstadoPedido>();
		}
		estados.add(ep);
	}
	
	
	
}
