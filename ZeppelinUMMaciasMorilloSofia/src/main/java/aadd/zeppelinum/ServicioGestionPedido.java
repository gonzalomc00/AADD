package aadd.zeppelinum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.bson.types.ObjectId;

import aadd.persistencia.dto.OpinionDTO;
import aadd.persistencia.jpa.bean.Plato;
import aadd.persistencia.jpa.bean.Restaurante;
import aadd.persistencia.jpa.bean.Usuario;
import aadd.persistencia.jpa.dao.EntityManagerHelper;
import aadd.persistencia.jpa.dao.PlatoDAO;
import aadd.persistencia.jpa.dao.RestauranteDAO;
import aadd.persistencia.jpa.dao.UsuarioDAO;
import aadd.persistencia.mongo.bean.EstadoPedido;
import aadd.persistencia.mongo.bean.ItemPedido;
import aadd.persistencia.mongo.bean.Opinion;
import aadd.persistencia.mongo.bean.Pedido;
import aadd.persistencia.mongo.bean.TipoEstado;
import aadd.persistencia.mongo.dao.OpinionDAO;
import aadd.persistencia.mongo.dao.PedidoDAO;

public class ServicioGestionPedido {

	private static ServicioGestionPedido servicio;

	public static ServicioGestionPedido getServicioGestionPedido() {
		if (servicio == null) {
			servicio = new ServicioGestionPedido();
		}
		return servicio;
	}

	//// boletin MONGO
	public boolean opinar(Integer usuario, Integer restaurante, String comentario, Double valoracion) {
		OpinionDAO opinionDAO = OpinionDAO.getOpinionDAO();

		// creamos la opoinion e inicializamos los datos
		Opinion o = new Opinion();
		o.setOpinion(comentario);
		o.setRestaurante(restaurante);
		o.setUsuario(usuario);
		o.setValor(valoracion);

		// mongo db nos garantiza atomicidad a nivel de documentos

		ObjectId id = opinionDAO.save(o);
		if (id != null) {
			// si se ha creado tengo que modificar la nota del restaurante
			EntityManager em = EntityManagerHelper.getEntityManager();
			try {
				em.getTransaction().begin();
				Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante);
				Integer numeroValoraciones = r.getNumValoraciones();
				Integer nuevoNumValoraciones = numeroValoraciones + 1;
				r.setNumValoraciones(nuevoNumValoraciones);
				if (r.getNumValoraciones() == 0) { // si no hay mas valoraciones, esa es la media
					r.setValoracionGlobal(valoracion);
				} else { // si hay alguna valoracion recalcular la media
					Double nota = r.getValoracionGlobal();
					double nuevaGlobal = ((nota * numeroValoraciones) + valoracion) / nuevoNumValoraciones;
					r.setValoracionGlobal(nuevaGlobal);
				}
				em.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				em.close();
			}
			return true;
		} else
			return false;
	}

	public List<OpinionDTO> findByUsuario(Integer usuario) { //devuelve las opiniones de un usuario
		OpinionDAO opinionDAO = OpinionDAO.getOpinionDAO();
		List<Opinion> opiniones = opinionDAO.findByUsuario(usuario); //recuperamos las opiniones del dao
		List<OpinionDTO> opinionesDTO = new ArrayList<>();
	
		for (Opinion o : opiniones) {
			Restaurante r = RestauranteDAO.getRestauranteDAO().findById(o.getRestaurante()); //recupero el nombre del restaurante de esa opinion
			OpinionDTO opinionDTO = new OpinionDTO(); 
			opinionDTO.setNombreRestaurante(r.getNombre());
			opinionDTO.setValoracion(o.getValor());
			opinionDTO.setComentario(o.getOpinion());
			opinionesDTO.add(opinionDTO); //añadir a la lista de opiniones
		}
		return opinionesDTO;
	}

	public List<OpinionDTO> findByRestaurante(Integer restaurante) {
		OpinionDAO opinionDAO = OpinionDAO.getOpinionDAO();
		List<Opinion> opiniones = opinionDAO.findByRestaurante(restaurante);
		List<OpinionDTO> opinionesDTO = new ArrayList<>();

		for (Opinion o : opiniones) {
			Usuario u = UsuarioDAO.getUsuarioDAO().findById(o.getUsuario());
			OpinionDTO opinionDTO = new OpinionDTO();
			opinionDTO.setNombreUsuario(u.getNombre());
			opinionDTO.setValoracion(o.getValor());
			opinionDTO.setComentario(o.getOpinion());
			opinionesDTO.add(opinionDTO);
		}
		return opinionesDTO;
	}
	
	//EJERCICIOS BOLETIN MONGO
/*	public boolean realizarPedido(Integer cliente, Integer restaurante, String comentario, String direccion, Integer repartidor, HashMap<Integer,Integer>platos) {
		PedidoDAO pedidoDAO = PedidoDAO.getPedidoDAO();

		// creamos el pedido e inicializamos los datos
		Pedido p = new Pedido();
		p.setCliente(cliente);
		p.setComentario(comentario);
		p.setRestaurante(restaurante);
		p.setRepartidor(repartidor);
		p.setDatosDireccion(direccion);
		p.setFechaHora(LocalDate.now());
		p.setFechaEsperado(LocalDate.now()); //TODO: como sumar una hora al tiemp actual
		
		//creamos lista estados - Ya veremos
		List<EstadoPedido> estados = new LinkedList<EstadoPedido>();

		//Estado
		EstadoPedido ep = new EstadoPedido();
		ep.setEstado(TipoEstado.INICIO);
		ep.setFechaEstado(LocalDate.now());
		
		//ItemPedido
		
		
		//TODO: revisar como se recorre -> crear un metodo en itemPedidoç
		for (Integer plato: platos.keySet()) {
			ItemPedido ip = new ItemPedido();
			Plato plto = PlatoDAO.getPlatoDAO().findById(plato);
			double total = plto.getPrecio() * platos.get(plato);
			ip.setCantidad(platos.get(plato));
			ip.setPlato(plato);
			ip.setPrecioTotal(total);
			p.addItem(ip);
			
		}
		
		
		
		
		
		
		
		
		ObjectId id = opinionDAO.save(o);
		if (id != null) {
			// si se ha creado tengo que modificar la nota del restaurante
			EntityManager em = EntityManagerHelper.getEntityManager();
			try {
				em.getTransaction().begin();
				Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante);
				Integer numeroValoraciones = r.getNumValoraciones();
				Integer nuevoNumValoraciones = numeroValoraciones + 1;
				r.setNumValoraciones(nuevoNumValoraciones);
				if (r.getNumValoraciones() == 0) { // si no hay mas valoraciones, esa es la media
					r.setValoracionGlobal(valoracion);
				} else { // si hay alguna valoracion recalcular la media
					Double nota = r.getValoracionGlobal();
					double nuevaGlobal = ((nota * numeroValoraciones) + valoracion) / nuevoNumValoraciones;
					r.setValoracionGlobal(nuevaGlobal);
				}
				em.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				em.close();
			}
			return true;
		} else
			return false;
	}*/
}