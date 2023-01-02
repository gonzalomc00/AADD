package aadd.web.usuario;

import java.io.Serializable;
import java.time.LocalDate;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.zeppelinum.ServicioGestionPlataforma;

//TomCat se va a encargar de crear y destruir estos objetos. Un bean de respaldo dura lo que dura la sesion 
@Named
@ViewScoped
public class RegistroWeb implements Serializable {
	private String nombre;
	private String apellidos;
	private String correo;
	private String clave;
	private String clave2;
	private LocalDate fechaNacimiento;
	private String tipo;
	@Inject
	protected FacesContext facesContext;

	public void registro() {
		
		// Comprobacion parametros
		if (nombre == null || nombre.trim().equals("")) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe indicar su nombre"));
			return;
		}
		if (apellidos == null || apellidos.trim().equals("")) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe indicar sus apellidos"));
			return;
		}
		if (clave == null || clave.trim().equals("")) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe poner una contraseña válida"));
			return;
		}
		if (clave2.equals(clave)) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las contraseñas deben ser iguales"));
			return;
		}
		if (tipo == null || tipo.trim().equals("")) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Seleccione un tipo"));
			return;
		}
		if (fechaNacimiento == null) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar su fecha de nacimiento"));
			return;
		}
		if (ServicioGestionPlataforma.getServicioGestionPlataforma().isUsuarioRegistrado(correo)) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ya existe un usuario con el email " + correo));
			return;
		}
		Integer idUser = ServicioGestionPlataforma.getServicioGestionPlataforma().registrarUsuario(nombre, apellidos,
				fechaNacimiento, correo, clave, TipoUsuario.valueOf(tipo));
		if (idUser != null) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Usuario registrado correctamente"));
		} else {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El usurio no ha podido ser registrado"));
		}
	}
	// getters y setters

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getClave2() {
		return clave2;
	}

	public void setClave2(String clave2) {
		this.clave2 = clave2;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}