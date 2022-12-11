package aadd.zeppelinum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.persistence.EntityManager;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import aadd.persistencia.dto.CategoriaRestauranteDTO;
import aadd.persistencia.dto.EstadisticaOpinionDTO;
import aadd.persistencia.dto.IncidenciaDTO;
import aadd.persistencia.dto.PlatoDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.dto.UsuarioDTO;
import aadd.persistencia.jpa.bean.CategoriaRestaurante;
import aadd.persistencia.jpa.bean.Incidencia;
import aadd.persistencia.jpa.bean.Plato;
import aadd.persistencia.jpa.bean.Restaurante;
import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.persistencia.jpa.bean.Usuario;
import aadd.persistencia.jpa.dao.CategoriaRestauranteDAO;
import aadd.persistencia.jpa.dao.EntityManagerHelper;
import aadd.persistencia.jpa.dao.IncidenciaDAO;
import aadd.persistencia.jpa.dao.PlatoDAO;
import aadd.persistencia.jpa.dao.RestauranteDAO;
import aadd.persistencia.jpa.dao.UsuarioDAO;
import aadd.persistencia.mongo.bean.Direccion;
import aadd.persistencia.mongo.dao.DireccionDAO;

public class ServicioGestionPlataforma {

	private static ServicioGestionPlataforma servicio;

	private static ZeppelinUMRemoto zeppelinumRemoto;

	public static ServicioGestionPlataforma getServicioGestionPlataforma() {
		if (servicio == null) {
			try {
				zeppelinumRemoto = (ZeppelinUMRemoto) InitialContextUtil.getInstance().lookup(
						"ejb:AADD2022/ZeppelinUMMaciasManzanaresEJB/ZeppelinUMRemoto!aadd.zeppelinum.ZeppelinUMRemoto");
			} catch (NamingException e) {
				e.printStackTrace();
			}
			servicio = new ServicioGestionPlataforma();
		}
		return servicio;
	}

	public Integer registrarUsuario(String nombre, String apellidos, LocalDate fechaNacimiento, String email,
			String clave, TipoUsuario tipo) {

		EntityManager em = EntityManagerHelper.getEntityManager(); // recuperamos un EntityManager a partir del
																	// EntityManagerHelper -> para hacer la gestion de
																	// transacciones.
		try {
			em.getTransaction().begin(); // comenzamos una transaccion

			Usuario usu = new Usuario(); // recibe los datos para crear un usuario por primera vez
			usu.setNombre(nombre);
			usu.setApellidos(apellidos);
			usu.setFechaNacimiento(fechaNacimiento);
			usu.setEmail(email);
			usu.setClave(clave);
			usu.setTipo(tipo);
			// CÓDIGO NUEVO
			if (tipo.name().equals("RESTAURANTE"))
				usu.setValidado(false);
			else
				usu.setValidado(true);

			UsuarioDAO.getUsuarioDAO().save(usu, em); // persistimos la entidad (con el metodo save para que haga el
														// persist)

			em.getTransaction().commit(); // importante hacer el commit
			return usu.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally { // siempre tener el bloque finally por si hay un error al final del codigo y la
					// transaccion se queda a medio abierta (MUY PELIGROSO)
			if (em.getTransaction().isActive()) { // comprueba si se ha dejado activa la transaccion (si ha habido algun
													// problema)
				em.getTransaction().rollback(); // deshace lo hecho
			}
			em.close(); // cerramos entity manager
		}
	}

	public boolean validarUsuario(Integer usuario) {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			Usuario usu = UsuarioDAO.getUsuarioDAO().findById(usuario); // recupera la entidad usuario de la BBDD
			usu.setValidado(true);// marco como validado

			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	public Integer registrarRestaurante(String nombre, Integer responsable, String calle, String codigoPostal,
			Integer numero, String ciudad, Double latitud, Double longitud, List<Integer> listaC) { // entra el nombre
		// del restaurante y
		// el id
		// del responsable

		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			Restaurante r = new Restaurante(); // crea el restaurante e inicializa sus datos
			Usuario u = UsuarioDAO.getUsuarioDAO().findById(responsable);

			for (Integer c : listaC) { // podemos recuperarlo de golpe
				System.out.println(c);
				CategoriaRestaurante cat = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(c);
				r.addCategoria(cat);
			}

			r.setResponsable(u); // el responsable ya debe de estar en la BD
			r.setNombre(nombre);
			r.setFechaAlta(LocalDate.now());
			r.setValoracionGlobal(0d);
			r.setNumPenalizaciones(0);
			r.setNumValoraciones(0);
			// TODO: Permitir crear un restaurante con un listado de categorias.

			RestauranteDAO.getRestauranteDAO().save(r, em);
			// Codigo nuevo MONGO
			// Forzamos un flush para que mysql nos de un id para el restaurante
			em.flush();
			Direccion d = new Direccion();
			d.setCalle(calle);
			d.setCiudad(ciudad);
			d.setCodigoPostal(codigoPostal);
			d.setCoordenadas(new Point(new Position(longitud, latitud)));
			d.setNumero(numero);
			d.setRestaurante(r.getId());

			DireccionDAO.getDireccionDAO().save(d);
			//////

			em.getTransaction().commit();
			return r.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	public Integer registrarIncidencia(LocalDate fechaCreacion, String descripcion, LocalDate fechaAlta,
			String comentario, LocalDate fechaCierre, Integer usuario, Integer restaurante) {

		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin(); // comenzamos una transaccion

			Incidencia incidencia = new Incidencia(); // recibe los datos para crear un usuario por primera vez
			Usuario u = UsuarioDAO.getUsuarioDAO().findById(usuario);
			Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante);
			incidencia.setFechaCreacion(fechaCreacion);
			incidencia.setDescripcion(descripcion);
			incidencia.setFechaAlta(fechaAlta);
			incidencia.setComentario(comentario);
			incidencia.setFechaCierre(fechaCierre);
			incidencia.setUsuario(u);
			incidencia.setRestaurante(r);

			IncidenciaDAO.getIncidenciaDAO().save(incidencia, em); // persistimos la entidad (con el metodo save para
																	// que haga el
			// persist)

			em.getTransaction().commit(); // importante hacer el commit
			return incidencia.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally { // siempre tener el bloque finally por si hay un error al final del codigo y la
					// transaccion se queda a medio abierta (MUY PELIGROSO)
			if (em.getTransaction().isActive()) { // comprueba si se ha dejado activa la transaccion (si ha habido algun
													// problema)
				em.getTransaction().rollback(); // deshace lo hecho
			}
			em.close(); // cerramos entity manager
		}
	}

	public boolean añadirCategoria(Integer categoria, Integer restaurante) {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			CategoriaRestaurante cat = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(categoria); // recupera
																													// la
																													// entidad
																													// usuario
																													// de
																													// la
																													// BBDD
			Restaurante res = RestauranteDAO.getRestauranteDAO().findById(restaurante);
			res.addCategoria(cat);

			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}

	}

	public boolean nuevoPlato(String titulo, String descripcion, double precio, Integer restaurante) {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin(); // abrimos transaccion

			Restaurante r = RestauranteDAO.getRestauranteDAO().findById(restaurante); // recuperamos restaurante
			Plato p = new Plato(); // creamos el plato
			p.setDescripcion(descripcion);
			p.setTitulo(titulo);
			p.setPrecio(precio);
			p.setRestaurante(r);
			p.setDisponibilidad(true);

			PlatoDAO.getPlatoDAO().save(p, em); // y lo persistimos

			em.getTransaction().commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	public boolean changeDisponibilidadPlato(Integer plato, boolean disp) {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin(); // abrimos transaccion

			Plato p = PlatoDAO.getPlatoDAO().findById(plato);
			p.setDisponibilidad(disp);

			em.getTransaction().commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	// duplicado usuario??
	public boolean isUsuarioRegistrado(String email) {
		List<UsuarioDTO> u = UsuarioDAO.getUsuarioDAO().findByEmail(email);
		if (u != null && !u.isEmpty()) {
			return true;
		}
		return false;
	} // este tipo de usuarios devuelve una lista, entonces si es 1 o no esta vacia es
		// que ya existe

	// login
	public UsuarioDTO login(String email, String clave) {
		List<UsuarioDTO> usuarios = UsuarioDAO.getUsuarioDAO().findByEmailClave(email, clave);
		if (usuarios.isEmpty()) {
			System.out.println("Usuario no encontrado, email o clave incorrectos");
			return null;
		} else {
			System.out.println("Usuario logueado " + usuarios.get(0).getNombre());
			return usuarios.get(0);
		}
	}

//metodo en el servicio que dado el id de un restaurante nos devuelva todo su menu disponible
	public List<PlatoDTO> getMenuByRestaurante(Integer restaurante, boolean mostrarTodos) {
		return PlatoDAO.getPlatoDAO().findPlatosDisponiblesByRestaurante(restaurante, mostrarTodos);
	}

	// nos devuelve los restaurantes segun el filtro
	public List<RestauranteDTO> getRestaurantesByFiltros(String keyword, boolean verNovedades,
			boolean ordernarByValoracion, boolean ceroIncidencias) {
		if (keyword != null && keyword.isBlank()) {
			keyword = null;
		}
		LocalDate fecha = null;
		if (verNovedades) { // filtramos por aquellos dados de alta la última semana
			fecha = LocalDate.now();
			fecha = fecha.minusWeeks(1);
		}
		return RestauranteDAO.getRestauranteDAO().findRestauranteByFiltros(keyword, fecha, ordernarByValoracion,
				ceroIncidencias);
	}

	// nos devuelve los restaurantes segun el filtro
	public List<RestauranteDTO> getRestaurantesByResponsable(Integer id_responsable, boolean conPlatos) {
		return RestauranteDAO.getRestauranteDAO().findRestauranteByResponsable(id_responsable, conPlatos);
	}

	// METODO QUE LLAMA A LA CLASE DAO DE USUARIO PARA QUE DEVUELVA AQUELLOS DE TIPO
	// RESTAURANTE NO VALIDADOS
	public List<UsuarioDTO> getUsuarioTipoRestauranteNoValidados() {
		return UsuarioDAO.getUsuarioDAO().findUsuarioByTipoRestauranteNoValidado();
	}

	public List<IncidenciaDTO> getIncidenciaByUsuario(Integer id_usuario) {
		return IncidenciaDAO.getIncidenciaDAO().findIncidenciaByUsuario(id_usuario);

	}

	public List<IncidenciaDTO> getIncidenciaSinCerrar() {
		return IncidenciaDAO.getIncidenciaDAO().findIncidenciaSinCerrar();

	}

	public Integer crearCategoria(String nombre) { // entra el nombre de la categoria y su id
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			CategoriaRestaurante c = new CategoriaRestaurante(); // crea la categoria e inicializa sus datos
			c.setNombre(nombre);

			CategoriaRestauranteDAO.getCategoriaRestauranteDAO().save(c, em);

			em.getTransaction().commit();
			return c.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	//// MONGO restaurante -parte sql parte mongo
	public List<RestauranteDTO> getRestaurantesByCercanía(Double latitud, Double longitud, int limite, int skip) {
		List<Direccion> direcciones = DireccionDAO.getDireccionDAO().findOrdenadoPorCercania(latitud, longitud, limite,
				skip);

		RestauranteDAO restauranteDAO = RestauranteDAO.getRestauranteDAO();
		List<RestauranteDTO> restaurantes = new ArrayList<>();
		for (Direccion d : direcciones) {
			Restaurante r = restauranteDAO.findById(d.getRestaurante());
			Position coordenadas = d.getCoordenadas().getCoordinates();

			RestauranteDTO restauranteDTO = new RestauranteDTO(r.getId(), r.getNombre(), r.getValoracionGlobal(),
					coordenadas.getValues().get(0), coordenadas.getValues().get(1), d.getCalle(), d.getCodigoPostal(),
					d.getCiudad(), d.getNumero());
			restaurantes.add(restauranteDTO);
		}
		return restaurantes;
	}

	public RestauranteDTO getDatosRestaurante(RestauranteDTO restaurante) {
		Direccion d = DireccionDAO.getDireccionDAO().findByRestaurante(restaurante.getId());
		Position coordenadas = d.getCoordenadas().getCoordinates();
		restaurante.setLongitud(coordenadas.getValues().get(0));
		restaurante.setLatitud(coordenadas.getValues().get(1));
		restaurante.setCalle(d.getCalle());
		restaurante.setCiudad(d.getCiudad());
		restaurante.setCodigoPostal(d.getCodigoPostal());
		restaurante.setNumero(d.getNumero());
		return restaurante;
	}

	public List<CategoriaRestauranteDTO> getAllCategorias() {
		return CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findAllCategoriasRestaurante();
	}

	// boletin jsf
	public RestauranteDTO getRestaurante(Integer idRestaurante) {
		Restaurante restaurante = RestauranteDAO.getRestauranteDAO().findById(idRestaurante);
		RestauranteDTO restauranteDTO = new RestauranteDTO(idRestaurante, restaurante.getNombre(),
				restaurante.getValoracionGlobal());
		restauranteDTO.setResposable(restaurante.getResponsable().getId());
		return restauranteDTO;
	}

	public List<Integer> getIdUsuariosByTipo(List<TipoUsuario> tipos) {
		return UsuarioDAO.getUsuarioDAO().findIdsByTipo(tipos);
	}

	// EJB
	public List<EstadisticaOpinionDTO> getEstadisticasOpinion(Integer idUsuario) {
		return zeppelinumRemoto.getEstadisticasOpinion(idUsuario);
	}

	public Integer getNumVisitas(Integer idUsuario) {
		return zeppelinumRemoto.getNumVisitas(idUsuario);
	}

	

}