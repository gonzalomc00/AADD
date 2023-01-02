package aadd.web.usuario;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import aadd.persistencia.dto.UsuarioDTO;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class ValidarUsuarioWeb implements Serializable {

	private ServicioGestionPlataforma servicio;
	
	private List<UsuarioDTO> usuarios;
	
	@Inject
	protected FacesContext facesContext;
	
	public ValidarUsuarioWeb() {
		servicio=ServicioGestionPlataforma.getServicioGestionPlataforma();
	}
	
	
	public void loadUsuarios() {
		usuarios= servicio.getUsuarioTipoRestauranteNoValidados();
	}
	
	public void validarUsuario(Integer usuario) {
		boolean validado = servicio.validarUsuario(usuario);
		
		if (validado == true) {
			facesContext.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Usuario validado correctamente"));

	        } else{
	        	facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El usuario no se ha podido validar"));
	        }
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
