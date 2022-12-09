package aadd.persistencia.dto;

public class ItemPedidoDTO {
	
	private String nombrePlato;
	private Integer cantidad;
	private Double precioPlato;
	private Double costeTotal;
	
	public ItemPedidoDTO(String nombrePlato, Integer cantidad, Double costeTotal, Double precioPlato) {
		this.nombrePlato=nombrePlato;
		this.cantidad=cantidad;
		this.costeTotal=costeTotal;
		this.setPrecioPlato(precioPlato);
		
	}
	
	
	public String getNombrePlato() {
		return nombrePlato;
	}
	public void setNombrePlato(String nombrePlato) {
		this.nombrePlato = nombrePlato;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Double getCosteTotal() {
		return costeTotal;
	}
	public void setCosteTotal(Double costeTotal) {
		this.costeTotal = costeTotal;
	}


	public Double getPrecioPlato() {
		return precioPlato;
	}


	public void setPrecioPlato(Double precioPlato) {
		this.precioPlato = precioPlato;
	}
	
	
}
