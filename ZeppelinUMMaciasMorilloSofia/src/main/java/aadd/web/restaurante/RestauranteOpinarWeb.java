package aadd.web.restaurante;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RateEvent;

import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPedido;

@Named
@ViewScoped
public class RestauranteOpinarWeb implements Serializable {
	@Inject
	protected FacesContext facesContext;

	@Inject
	private UserSessionWeb sesion;

	private Integer restauranteId;
	private Integer userId;

	private Double valoracion;
	private String opinion;

	// SERVICIO
	private ServicioGestionPedido servicioPedido;

	public RestauranteOpinarWeb() {
		servicioPedido = ServicioGestionPedido.getServicioGestionPedido();
	}

	@PostConstruct
	public void init() {
		// TODO: PREGUNTAR POR QUE ESTA PASANDO ESTO
		if (sesion.isLogin()) {
			userId = sesion.getUsuario().getId();
		}
	}

	public void crearOpinion() {
		
		 if (opinion == null || opinion.trim().equals("")) {
	            facesContext.addMessage(null, 
	               new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe escribir una opinión"));
	            return;
	        }
		if ( valoracion == null) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe dar una nota al restaurante entre 0 y 10"));
			return;
		}
		boolean done = servicioPedido.opinar(userId, restauranteId, opinion, valoracion);
		if (done == false) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La opinión no ha podido realizarse"));
		} else {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Opinión realizada correctamente"));
		}
	}

	// getters y setters

	public Double getValoracion() {
		return valoracion;
	}

	public void setValoracion(Double valoracion) {
		this.valoracion = valoracion;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public ServicioGestionPedido getServicioPedido() {
		return servicioPedido;
	}

	public void setServicioPedido(ServicioGestionPedido servicioPedido) {
		this.servicioPedido = servicioPedido;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public UserSessionWeb getSesion() {
		return sesion;
	}

	public void setSesion(UserSessionWeb sesion) {
		this.sesion = sesion;
	}

	public Integer getRestauranteId() {
		return restauranteId;
	}

	public void setRestauranteId(Integer restauranteId) {
		this.restauranteId = restauranteId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
