package aadd.web.pedido;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.bson.types.ObjectId;

import aadd.persistencia.dto.IncidenciaDTO;
import aadd.persistencia.dto.PedidoDTO;
import aadd.persistencia.mongo.bean.TipoEstado;
import aadd.zeppelinum.ServicioGestionPedido;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class PedidoRestauranteList implements Serializable {

	@Inject
	private FacesContext facesContext;
	
	private List<PedidoDTO> pedidos;
	private List<IncidenciaDTO> incidencias;
	private Integer restauranteId;
	private ServicioGestionPedido servicio;

	private ServicioGestionPlataforma servicioPlataforma;
	
	private String comentarioIncidencia;
	private Integer incidenciaSeleccionada;
	
	
	public PedidoRestauranteList() {
		servicio= ServicioGestionPedido.getServicioGestionPedido();
		servicioPlataforma= ServicioGestionPlataforma.getServicioGestionPlataforma();
	}

	
	public void loadPedidos() {
		pedidos=servicio.findPedidoByRestaurante(restauranteId);
		
	}
	
	public void loadIncidencias() {
		incidencias=servicioPlataforma.getIncidenciasByRestauranteSinCerrar(restauranteId);
	}
	
	public void seleccionarIncidencia(Integer id_incidencia) {
		incidenciaSeleccionada=id_incidencia;
	}
	
	public void cerrarIncidencia()
	{
		if(servicioPlataforma.cerrarIncidencia(incidenciaSeleccionada, comentarioIncidencia)) {
			 facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La incidencia se ha cerrado correctatmente", ""));
			 loadIncidencias();
		} else {
			  facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La incidencia no se ha cerrado correctamente", ""));

		}
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


	public List<IncidenciaDTO> getIncidencias() {
		return incidencias;
	}


	public void setIncidencias(List<IncidenciaDTO> incidencias) {
		this.incidencias = incidencias;
	}


	public ServicioGestionPlataforma getServicioPlataforma() {
		return servicioPlataforma;
	}


	public void setServicioPlataforma(ServicioGestionPlataforma servicioPlataforma) {
		this.servicioPlataforma = servicioPlataforma;
	}


	public String getComentarioIncidencia() {
		return comentarioIncidencia;
	}


	public void setComentarioIncidencia(String comentarioIncidencia) {
		this.comentarioIncidencia = comentarioIncidencia;
	}


	public Integer getIncidenciaSeleccionada() {
		return incidenciaSeleccionada;
	}


	public void setIncidenciaSeleccionada(Integer incidenciaSeleccionada) {
		this.incidenciaSeleccionada = incidenciaSeleccionada;
	}
	
	
	
	
}
