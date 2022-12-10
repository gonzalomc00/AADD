package aadd.web.pedido;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.bson.types.ObjectId;

import aadd.persistencia.dto.PedidoDTO;
import aadd.persistencia.mongo.bean.TipoEstado;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPedido;

@Named
@ViewScoped
public class PedidoUsuarioList implements Serializable {

	@Inject
	private FacesContext facesContext;
	
	private List<PedidoDTO> pedidos;
	private ServicioGestionPedido servicio;
	
	@Inject
	private UserSessionWeb sesion;
	private Integer id;
	
	
	public PedidoUsuarioList() {
		servicio= ServicioGestionPedido.getServicioGestionPedido();
	}

	@PostConstruct
	public void init() {
		id=sesion.getUsuario().getId();
	}
	
	public void loadPedidos() {
		pedidos=servicio.findPedidoByCliente(id);
		
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



	public ServicioGestionPedido getServicio() {
		return servicio;
	}


	public void setServicio(ServicioGestionPedido servicio) {
		this.servicio = servicio;
	}

	public UserSessionWeb getSesion() {
		return sesion;
	}

	public void setSesion(UserSessionWeb sesion) {
		this.sesion = sesion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	
}
