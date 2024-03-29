package aadd.spring.zeppelium;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.client.model.geojson.Position;

import aadd.persistencia.dto.CategoriaRestauranteDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.jpa.bean.CategoriaRestaurante;
import aadd.persistencia.jpa.bean.Restaurante;
import aadd.persistencia.jpa.dao.CategoriaRestauranteDAO;
import aadd.persistencia.jpa.dao.RestauranteDAO;
import aadd.persistencia.mongo.bean.Direccion;
import aadd.persistencia.mongo.dao.DireccionDAO;

@Service
public class ServicioGestionSpring {

    public List<RestauranteDTO> buscarRestaurantesLazy(String keyword, boolean verNovedades, boolean ordernarByValoracion,boolean ceroIncidencias, Double latitud, Double longitud, int start, int max, List<Integer> categorias) {
        RestauranteDAO restauranteDAO = RestauranteDAO.getRestauranteDAO();
        if(keyword != null && keyword.isBlank()) {
            keyword = null;
        }
        LocalDate fecha = null;
        if(verNovedades) { // filtramos por aquellos dados de alta la última semana
            fecha = LocalDate.now();
            fecha = fecha.minusWeeks(1);
        }
        // si hemos filtrado por algo, buscamos según filtros
        if ((keyword != null && !keyword.isBlank()) || fecha != null || ordernarByValoracion || ceroIncidencias || categorias.size()>0) {
            return restauranteDAO.findRestauranteByFiltrosLazy(keyword, fecha, ordernarByValoracion, ceroIncidencias, start, max, categorias);
        }

        // si no hemos filtrado, búscamos ordenados por localización
        else {
            List<Direccion> direcciones = DireccionDAO.getDireccionDAO().findOrdenadoPorCercania(latitud, longitud, max,start);
            List<RestauranteDTO> restaurantes = new ArrayList<>();
            for (Direccion d : direcciones) {
                Restaurante r = restauranteDAO.findById(d.getRestaurante());
                Position coordenadas = d.getCoordenadas().getCoordinates();
                RestauranteDTO restauranteDTO = new RestauranteDTO(r.getId(), r.getNombre(), r.getValoracionGlobal(), coordenadas.getValues().get(0),
                		coordenadas.getValues().get(1), d.getCalle(),d.getCodigoPostal(), d.getCiudad(), d.getNumero());
                restaurantes.add(restauranteDTO);
            }
            return restaurantes;
        }
        

	}

    public int countRestaurantes(String keyword, boolean verNovedades,boolean ceroIncidencias,boolean ordernarByValoracion, List<Integer> categorias) {
        if(keyword != null && keyword.isBlank()) {
            keyword = null;
        }
        LocalDate fecha = null;
        if(verNovedades) { // filtramos por aquellos dados de alta la última semana
            fecha = LocalDate.now();
            fecha = fecha.minusWeeks(1);
        }
        
        if ((keyword != null && !keyword.isBlank()) || fecha != null || ordernarByValoracion || ceroIncidencias || categorias.size()>0) {
        	 return RestauranteDAO.getRestauranteDAO().countRestaurantesByFiltros(keyword, fecha, ceroIncidencias,categorias).intValue();
        }

        // si no hemos filtrado, obtenemos todos los restaurantes que hay en la base de datos. Para ello nos bastamos de ver
        // cuantas direcciones hay almacenadas
        else {
            return DireccionDAO.getDireccionDAO().countAllDirecciones();
        }
        
       
    }

	public List<CategoriaRestauranteDTO> findAllCategorias() {
		return CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findAllCategoriasRestaurante();
	}
}
