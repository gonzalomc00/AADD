package aadd.web.restaurante;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import aadd.persistencia.dto.CategoriaRestauranteDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class RestauranteWeb implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double latitudSelected;
    private Double longitudSelected;
    private String nombreRestaurante;
    private String calle;
    private String codigoPostal;
    private Integer numero;
    private String ciudad;
    private List<Integer> categoriasSel;
    private MapModel<Integer> simpleModel;
    @Inject
    private FacesContext facesContext;
    @Inject
    private UserSessionWeb usuarioSesion; //bean con ambito de sesion donde se guarda el inicio de sesion del usuario 
    private Integer responsableId;
    private ServicioGestionPlataforma servicio;

    public RestauranteWeb() {
        simpleModel = new DefaultMapModel<Integer>(); //atributo que gestiona el modelo del mapa
        servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
    }
    @PostConstruct
    public void init() {
        responsableId = usuarioSesion.getUsuario().getId();
      
        	List<RestauranteDTO> restaurantes_user= servicio.getRestaurantesByResponsable(responsableId,false);

    		for(RestauranteDTO r: restaurantes_user) {
    			servicio.getDatosRestaurante(r);
    			LatLng coord= new LatLng(r.getLatitud(),r.getLongitud());
    			simpleModel.addOverlay(new Marker<Integer>(coord,r.getNombre(),r.getId()));
    		}
    		
    		   
    }
    
  

    public void onPointSelect(PointSelectEvent event) {
        LatLng latlng = event.getLatLng();
        latitudSelected = latlng.getLat();
        longitudSelected = latlng.getLng();
    }

    public void onMarkerRestauranteSelect(OverlaySelectEvent<Integer> event) { // a través de un redirect navego a un nuevo hmtl, ? pasandole como parametro el id del restaurante
        Marker<Integer> marker = (Marker<Integer>) event.getOverlay();
        Integer restauranteSelectedId = (Integer) marker.getData();
        try {
            String contextoURL = facesContext.getExternalContext().getApplicationContextPath();
            facesContext.getExternalContext().redirect(contextoURL + "/restaurante/formPlatos.xhtml?id=" + restauranteSelectedId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crearRestaurante() {
   
        Integer restauranteId = servicio.registrarRestaurante(nombreRestaurante, responsableId, calle, codigoPostal, numero, ciudad, latitudSelected, longitudSelected, categoriasSel);
       if (restauranteId == null) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El restaurante no se ha podido crear", ""));
        } else {
            LatLng coord = new LatLng(latitudSelected, longitudSelected); //si se ha creado se actualiza la vista y se añade un marcador en el mapa para marcar el restaurante
            simpleModel.addOverlay(new Marker<Integer>(coord, nombreRestaurante, restauranteId));
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Restaurante creado correctamente", ""));
        }
        
    }
    
    public List<CategoriaRestauranteDTO> getCategorias(){
    	return servicio.getAllCategorias();
    }
    
    //getters y setters -- filtrar por los necesarios
    
	public Double getLatitudSelected() {
		return latitudSelected;
	}
	public void setLatitudSelected(Double latitudSelected) {
		this.latitudSelected = latitudSelected;
	}
	public Double getLongitudSelected() {
		return longitudSelected;
	}
	public void setLongitudSelected(Double longitudSelected) {
		this.longitudSelected = longitudSelected;
	}
	public String getNombreRestaurante() {
		return nombreRestaurante;
	}
	public void setNombreRestaurante(String nombreRestaurante) {
		this.nombreRestaurante = nombreRestaurante;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public MapModel<Integer> getSimpleModel() {
		return simpleModel;
	}
	public void setSimpleModel(MapModel<Integer> simpleModel) {
		this.simpleModel = simpleModel;
	}
	public FacesContext getFacesContext() {
		return facesContext;
	}
	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}
	public UserSessionWeb getUsuarioSesion() {
		return usuarioSesion;
	}
	public void setUsuarioSesion(UserSessionWeb usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}
	public Integer getResponsableId() {
		return responsableId;
	}
	public void setResponsableId(Integer responsableId) {
		this.responsableId = responsableId;
	}
	public ServicioGestionPlataforma getServicio() {
		return servicio;
	}
	public void setServicio(ServicioGestionPlataforma servicio) {
		this.servicio = servicio;
	}
	public List<Integer> getCategoriasSel() {
		return categoriasSel;
	}
	public void setCategoriasSel(List<Integer> categoriasSel) {
		this.categoriasSel = categoriasSel;
	}
	
	

}