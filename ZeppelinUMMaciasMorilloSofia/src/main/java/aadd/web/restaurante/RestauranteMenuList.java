package aadd.web.restaurante;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import aadd.persistencia.dto.PlatoDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class RestauranteMenuList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;
	@Inject
	private UserSessionWeb sesion;

	private Integer idRestaurante;
	private List<PlatoDTO> menu;
	private String titulo;
	private String descripcion;
	private Double precio;
	private ServicioGestionPlataforma servicio;
	private RestauranteDTO restauranteSeleccionado;

	private boolean disponibilidad;

	private Integer userId;
	private boolean responsable;
	
	//PEDIDO
	private HashMap<Integer,Integer> pedido;
	private Integer cantidad;

	public RestauranteMenuList() {
		servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		cantidad=0;
		pedido= new HashMap<Integer,Integer>();
	}

	@PostConstruct
	public void init() {
		// PREGUNTAR POR QUE ESTA PASANDO ESTO
		if (sesion.isLogin()) {
			userId = sesion.getUsuario().getId();
		}
	}

	public void loadMenu() {
		menu = servicio.getMenuByRestaurante(idRestaurante, responsable);
	}

	public void setIdRestaurante(Integer idRestaurante) {
		this.idRestaurante = idRestaurante;
		restauranteSeleccionado = servicio.getRestaurante(idRestaurante);

		if (restauranteSeleccionado.getResposable() == userId) {
			responsable = true;
		} else {
			responsable = false;
		}

	}

	public Integer getidRestaurante() {
		return idRestaurante;
	}

	public List<PlatoDTO> getMenu() {
		return menu;
	}

	public void crearPlato() {
		boolean exito = servicio.nuevoPlato(titulo, descripcion, precio, idRestaurante);
		if (exito) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Plato creado", ""));
			loadMenu();
		}
	}

	public void cambiarDisponible(Integer plato) {
		
		servicio.changeDisponibilidadPlato(plato, true);

	}

	public void cambiarNoDisponible(Integer plato) {
		servicio.changeDisponibilidadPlato(plato, false);
	}

	// getters y setters necesarios (hecho)
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public RestauranteDTO getRestauranteSeleccionado() {
		return restauranteSeleccionado;
	}

	public void setMenu(List<PlatoDTO> menu) {
		this.menu = menu;
	}

	public boolean isDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public UserSessionWeb getSesion() {
		return sesion;
	}

	public void setSesion(UserSessionWeb sesion) {
		this.sesion = sesion;
	}

	public boolean isResponsable() {
		return responsable;
	}

	public void setResponsable(boolean responsable) {
		this.responsable = responsable;
	}

	public HashMap<Integer,Integer> getPedido() {
		return pedido;
	}

	public void setPedido(HashMap<Integer,Integer> pedido) {
		this.pedido = pedido;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public void sumarCantidad(Integer plato) {
		Integer cantidad=pedido.get(plato);
		if(cantidad==null) {
			pedido.put(plato, 1);
		}
		else {
			pedido.put(plato, cantidad++);
		}
	}
	
	public void restarCantidad(Integer plato) {
		Integer cantidad=pedido.get(plato);
		if(cantidad!=null) {
			if(cantidad--==0) {
				pedido.remove(plato);
			} else {
			pedido.put(plato, cantidad--);
			}
		}
	
	}

}