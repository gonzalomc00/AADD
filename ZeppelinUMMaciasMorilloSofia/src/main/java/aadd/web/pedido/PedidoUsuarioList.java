package aadd.web.pedido;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.bson.types.ObjectId;

import aadd.persistencia.dto.IncidenciaDTO;
import aadd.persistencia.dto.PedidoDTO;
import aadd.persistencia.mongo.bean.TipoEstado;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPedido;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class PedidoUsuarioList implements Serializable {

	@Inject
	private FacesContext facesContext;

	private List<PedidoDTO> pedidos;
	private List<IncidenciaDTO> incidencias;
	private ServicioGestionPedido servicio;
	private ServicioGestionPlataforma servicioPlataforma;

	@Inject
	private UserSessionWeb sesion;
	private Integer id;

	private Integer restauranteIdIncidencia;
	private ObjectId pedidoIdIncidencia;
	private String textoIncidencia;

	public PedidoUsuarioList() {
		servicio = ServicioGestionPedido.getServicioGestionPedido();
		servicioPlataforma = ServicioGestionPlataforma.getServicioGestionPlataforma();
	}

	@PostConstruct
	public void loadPedidosIncidencias() {
		id = sesion.getUsuario().getId();

		pedidos = servicio.findPedidoByCliente(id);
		incidencias = servicioPlataforma.getIncidenciaByUsuario(id);

	}

	

	public void cambiarEstadoPedido(ObjectId id, String estado) {
		servicio.editarEstado(id, TipoEstado.ERROR);
		loadPedidosIncidencias();
	}

	public void setDatosIncidencia(Integer id_restaurante, ObjectId id_pedido) {
		restauranteIdIncidencia = id_restaurante;
		pedidoIdIncidencia = id_pedido;
	}

	public void crearIncidencia() {
		Integer i = servicioPlataforma.registrarIncidencia(textoIncidencia, id, restauranteIdIncidencia);
		if (i == null) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"La incidencia no se ha podido creado correctamente", ""));
		} else {
			servicio.editarEstado(pedidoIdIncidencia, TipoEstado.ERROR);
			loadPedidosIncidencias();
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "La incidencia se ha creado correctatmente", ""));
		}

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

	public Integer getRestauranteIdIncidencia() {
		return restauranteIdIncidencia;
	}

	public void setRestauranteIdIncidencia(Integer restauranteIdIncidencia) {
		this.restauranteIdIncidencia = restauranteIdIncidencia;
	}

	public String getTextoIncidencia() {
		return textoIncidencia;
	}

	public void setTextoIncidencia(String textoIncidencia) {
		this.textoIncidencia = textoIncidencia;
	}

	public List<IncidenciaDTO> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<IncidenciaDTO> incidencias) {
		this.incidencias = incidencias;
	}

}
