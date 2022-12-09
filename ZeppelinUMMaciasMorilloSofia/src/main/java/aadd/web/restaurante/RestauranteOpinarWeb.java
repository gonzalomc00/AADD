package aadd.web.restaurante;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPedido;

@Named
@ViewScoped
public class RestauranteOpinarWeb implements Serializable {
	@Inject
	protected FacesContext facesContext;

	@Inject
	private UserSessionWeb sesion;

	protected Integer restauranteId;
	protected Integer userId;

	protected Double valoracion;
	protected String opinion;

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
		System.out.println("RESTAURANTE " + restauranteId);
		System.out.println("USUARIO " + userId);
		System.out.println("OPINION " + opinion);
		System.out.println("VALORACION " + valoracion);

		boolean done = servicioPedido.opinar(userId, restauranteId, opinion, valoracion);
		if (done == false) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha podido enviar la opinion", ""));
		} else {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Opinion realizado correctamente", ""));
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
