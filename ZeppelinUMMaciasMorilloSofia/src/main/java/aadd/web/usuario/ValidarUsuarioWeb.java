package aadd.web.usuario;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import aadd.persistencia.dto.UsuarioDTO;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class ValidarUsuarioWeb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private FacesContext facesContext;
	private ServicioGestionPlataforma servicio;
	
	private List<UsuarioDTO> usuarios;
	
	public ValidarUsuarioWeb() {
		servicio=ServicioGestionPlataforma.getServicioGestionPlataforma();
	}
	
	
	public void loadUsuarios() {
		usuarios= servicio.getUsuarioTipoRestauranteNoValidados();
	}
	
	public void validarUsuario(Integer usuario) {
		servicio.validarUsuario(usuario);
		loadUsuarios();
	}

	public List<UsuarioDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}


	public FacesContext getFacesContext() {
		return facesContext;
	}


	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}
	
	
	
	
	
}
