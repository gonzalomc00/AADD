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
public class PedidoRider implements Serializable {

	@Inject
	private FacesContext facesContext;
	
	private List<PedidoDTO> pedidosSinRider;
	private List<PedidoDTO> pedidosRider;
	private ServicioGestionPedido servicio;
	
	@Inject
	private UserSessionWeb sesion;
	private Integer id_repartidor;
	
	
	public PedidoRider() {
		servicio= ServicioGestionPedido.getServicioGestionPedido();
	}

	@PostConstruct
	public void obtenerIdUsuario() {
		if (sesion.isLogin()) {
			id_repartidor = sesion.getUsuario().getId();
		}
	}
	
	public void loadPedidos() {
		pedidosSinRider=servicio.findPedidoSinRider();

		if(sesion.isLogin()) {
		pedidosRider=servicio.findPedidosByRider(id_repartidor);
		}
		
	}
	
	public void asignarPedido(ObjectId id) {
		servicio.asignarRepartidor(id, id_repartidor);
		loadPedidos();
	}
	
	public void entregarPedido(ObjectId id) {
		servicio.editarEstado(id, TipoEstado.ENTREGADO);
		loadPedidos();
	}
	
	
	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public List<PedidoDTO> getPedidosSinRider() {
		return pedidosSinRider;
	}

	public void setPedidosSinRider(List<PedidoDTO> pedidosSinRider) {
		this.pedidosSinRider = pedidosSinRider;
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

	public Integer getIdRepartidor() {
		return id_repartidor;
	}

	public void setIdRepartidor(Integer id_repartidor) {
		this.id_repartidor = id_repartidor;
	}

	public List<PedidoDTO> getPedidosRider() {
		return pedidosRider;
	}

	public void setPedidosRider(List<PedidoDTO> pedidosRider) {
		this.pedidosRider = pedidosRider;
	}
	
	
	
	
}
