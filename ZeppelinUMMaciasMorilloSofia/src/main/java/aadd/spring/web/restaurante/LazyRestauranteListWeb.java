package aadd.spring.web.restaurante;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import aadd.persistencia.dto.CategoriaRestauranteDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.spring.zeppelium.ServicioGestionSpring;

@Component
@Scope("view")
public class LazyRestauranteListWeb extends LazyDataModel<RestauranteDTO> {
    private Integer total;

    @Inject
    @Autowired
    private ServicioGestionSpring servicioGestion;

    private boolean verNovedades;
    private boolean sinPenalizacion;
    private String keyword;
    private boolean mejorValorados;
    private List<Integer> categoriasSel;


    private Double latitud;
    private Double longitud;
    
    
    public LazyRestauranteListWeb() {
    
        latitud = 38.02398012353915;
        longitud = -1.1740098866576436;
        categoriasSel= new LinkedList<Integer>();
    }

    protected int findTotalResults() {
        if (total == null) {
            total = servicioGestion.countRestaurantes(keyword, verNovedades, sinPenalizacion,mejorValorados,categoriasSel);
        }
        return total;
    }

    public void buscar() {
        total = servicioGestion.countRestaurantes(keyword, verNovedades, sinPenalizacion,mejorValorados,categoriasSel);
    }

    public List<RestauranteDTO> buscarRestaurante(int inicio, int size) {

        return servicioGestion.buscarRestaurantesLazy(keyword, verNovedades, mejorValorados, sinPenalizacion, latitud, longitud, inicio, size, categoriasSel);

    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return findTotalResults();
    }

    @Override
    public List<RestauranteDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy,
            Map<String, FilterMeta> filterBy) {
        return buscarRestaurante(first, pageSize);
    }

    public Integer getTotal() {
        return total;
    }

	public ServicioGestionSpring getServicioGestion() {
		return servicioGestion;
	}

	public void setServicioGestion(ServicioGestionSpring servicioGestion) {
		this.servicioGestion = servicioGestion;
	}

	public boolean isVerNovedades() {
		return verNovedades;
	}

	public void setVerNovedades(boolean verNovedades) {
		this.verNovedades = verNovedades;
	}

	public boolean isSinPenalizacion() {
		return sinPenalizacion;
	}

	public void setSinPenalizacion(boolean sinPenalizacion) {
		this.sinPenalizacion = sinPenalizacion;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public boolean isMejorValorados() {
		return mejorValorados;
	}

	public void setMejorValorados(boolean mejorValorados) {
		this.mejorValorados = mejorValorados;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<Integer> getCategoriasSel() {
		return categoriasSel;
	}

	public void setCategoriasSel(List<Integer> categoriasSel) {
		this.categoriasSel = categoriasSel;
	}
    
	  public List<CategoriaRestauranteDTO> getCategorias(){
	    	return servicioGestion.findAllCategorias();
	    }
    
    
    
    
    
}