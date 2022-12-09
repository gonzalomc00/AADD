package aadd.web.pedido;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.bson.types.ObjectId;

import aadd.persistencia.dto.PedidoDTO;
import aadd.persistencia.mongo.bean.TipoEstado;
import aadd.zeppelinum.ServicioGestionPedido;

@Named
@ViewScoped
public class PedidoRestauranteList implements Serializable {

	@Inject
	private FacesContext facesContext;
	
	private List<PedidoDTO> pedidos;
	private Integer restauranteId;
	private ServicioGestionPedido servicio;
	
	
	public PedidoRestauranteList() {
		setServicio(ServicioGestionPedido.getServicioGestionPedido());
	}

	
	public void loadPedidos() {
		pedidos=servicio.findPedidoByRestaurante(restauranteId);
		
	}
	
	public void cambiarEstadoPedido(ObjectId id, String estado) {
		switch(estado) {
			case "ACEPTADO":
				servicio.editarEstado(id, TipoEstado.ACEPTADO);
				break;
			case "CANCELADO":
				servicio.editarEstado(id, TipoEstado.CANCELADO);
				break;
			case "PREPARADO":
				servicio.editarEstado(id, TipoEstado.PREPARADO);
				break;
			case "RECOGIDO":
				servicio.editarEstado(id, TipoEstado.RECOGIDO);
				break;
			
			
		}
		loadPedidos();
	}
	
	
	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public List<PedidoDTO> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<PedidoDTO> pedidos) {
		this.pedidos = pedidos;
	}

	public Integer getRestauranteId() {
		return restauranteId;
	}

	public void setRestauranteId(Integer restauranteId) {
		this.restauranteId = restauranteId;
	}


	public ServicioGestionPedido getServicio() {
		return servicio;
	}


	public void setServicio(ServicioGestionPedido servicio) {
		this.servicio = servicio;
	}
	
	
	
	
}
