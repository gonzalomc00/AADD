package aadd.zeppelinum;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import aadd.persistencia.dto.PlatoDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.dto.UsuarioDTO;
import aadd.persistencia.jpa.bean.CategoriaRestaurante;
import aadd.persistencia.jpa.bean.Plato;
import aadd.persistencia.jpa.bean.Restaurante;
import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.persistencia.jpa.bean.Usuario;
import aadd.persistencia.jpa.dao.CategoriaRestauranteDAO;
import aadd.persistencia.jpa.dao.EntityManagerHelper;
import aadd.persistencia.jpa.dao.PlatoDAO;
import aadd.persistencia.jpa.dao.RestauranteDAO;
import aadd.persistencia.jpa.dao.UsuarioDAO;

public class ServicioGestionPlataforma {

	private static ServicioGestionPlataforma servicio;

	public static ServicioGestionPlataforma getServicioGestionPlataforma() {
		if (servicio == null) {
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
			usu.setValidado(false);

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

	public Integer registrarRestaurante(String nombre, Integer responsable, List<Integer> listaC) { // entra el nombre
																									// del restaurante y
																									// el id
																									// del responsable

		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			Restaurante r = new Restaurante(); // crea el restaurante e inicializa sus datos
			Usuario u = UsuarioDAO.getUsuarioDAO().findById(responsable);
			for (Integer c : listaC) { // podemos recuperarlo de golpe
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

	public boolean añadirCategoria(Integer categoria, Integer restaurante) {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();

			CategoriaRestaurante cat = CategoriaRestauranteDAO.getCategoriaRestauranteDAO().findById(categoria); // recupera la entidad usuario de la BBDD
			Restaurante res =  RestauranteDAO.getRestauranteDAO().findById(restaurante);
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
	public List<PlatoDTO> getMenuByRestaurante(Integer restaurante) {
		return PlatoDAO.getPlatoDAO().findPlatosDisponiblesByRestaurante(restaurante);
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
	public List<RestauranteDTO> getRestaurantesByResponsable(Integer id_responsable) {
		return RestauranteDAO.getRestauranteDAO().findRestauranteByResponsable(id_responsable);
	}
	//METODO QUE LLAMA A LA CLASE DAO DE USUARIO PARA QUE DEVUELVA AQUELLOS DE TIPO RESTAURANTE NO VALIDADOS
	public List<UsuarioDTO> getUsuarioTipoRestauranteNoValidados(){
		return UsuarioDAO.getUsuarioDAO().findByTipoRestauranteNoValidado();
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

}