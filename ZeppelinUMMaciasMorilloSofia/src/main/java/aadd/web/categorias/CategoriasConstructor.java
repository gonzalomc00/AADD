package aadd.web.categorias;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import aadd.persistencia.dto.CategoriaRestauranteDTO;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class CategoriasConstructor implements Serializable {

	
	private String nombre;
    private ServicioGestionPlataforma servicio;
    private List<CategoriaRestauranteDTO> categorias;
	
    @Inject
    private FacesContext facesContext;
	
	public CategoriasConstructor() {
		servicio= ServicioGestionPlataforma.getServicioGestionPlataforma();
	}
	
	public void crearCategoria() {
		 Integer categoriaId = servicio.crearCategoria(nombre);
		 if (categoriaId != null) {
	    	   facesContext.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Categoría registrada correctamente"));
	        } else{
	            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "La categoría no se ha podido crear"));

	        }
	}
	

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public ServicioGestionPlataforma getServicio() {
		return servicio;
	}

	public void setServicio(ServicioGestionPlataforma servicio) {
		this.servicio = servicio;
	}

	public List<CategoriaRestauranteDTO> getCategorias() {
		return servicio.getAllCategorias();
	}

	public void setCategorias(List<CategoriaRestauranteDTO> categorias) {
		this.categorias = categorias;
	}
	
	
	
	
	
	
}
