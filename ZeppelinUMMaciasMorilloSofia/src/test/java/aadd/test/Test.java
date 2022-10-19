package aadd.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.mysql.cj.result.LocalDateTimeValueFactory;

import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.zeppelinum.ServicioGestionPlataforma;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.dto.UsuarioDTO;
import aadd.persistencia.jpa.bean.*;

class Test {
	@org.junit.jupiter.api.Test
	void crearUsuario() { // lo podemos ver actualizando la BBDD
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario = servicio.registrarUsuario("Pepe", "Alcaraz", fechaNacimiento, "misterbinario@umu.es",
				"12345", TipoUsuario.RESTAURANTE);
		assertTrue(usuario != null);
	}

	@org.junit.jupiter.api.Test
	void crearCategoria() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		Integer categoria = servicio.crearCategoria("Familiar");
		assertTrue(categoria != null);
	}

	@org.junit.jupiter.api.Test
	void crearRestaurantePlato() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();

		List<Integer> categorias= new LinkedList<Integer>();
		Integer rest = servicio.registrarRestaurante("McGonzalo", 1, categorias); //TODO: Null está bien puesto?????????? !!!!!
		assertTrue(rest != null);
		boolean exito = servicio.nuevoPlato("CBO", "pollo queso y bacon",
				20d, rest);
		assertTrue(exito);

	}
	
	@org.junit.jupiter.api.Test
	void addCategoria(){
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		boolean exito = servicio.añadirCategoria(1, 1);
		assertTrue(exito);
	}

	@org.junit.jupiter.api.Test
	void cambiarDispPlato() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		boolean exito = servicio.changeDisponibilidadPlato(2, false);
		assertTrue(exito);
	}

	@org.junit.jupiter.api.Test
	void validarUsuario() { // con la opcion createextenddatabase da error porque ya estan creadas (son
							// warnings) y no pasa nada
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		boolean exito = servicio.validarUsuario(1);
		assertTrue(exito);
	}



	@org.junit.jupiter.api.Test
	public void loginTest() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		assertTrue(servicio.login("periquita@palotes.es", "12345") != null);
		assertFalse(servicio.login("mdclg3@um.es", "loquesea") != null);
		assertFalse(servicio.login("periquita@palotes.es", "123456") != null);
	}

	@org.junit.jupiter.api.Test
	public void checkUsuarioTest() { // algo pasa aqui, vuelve a checkear sofia
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		assertTrue(servicio.isUsuarioRegistrado("periquita@palotes.es"));
		assertFalse(servicio.isUsuarioRegistrado("mdclg3@um.es"));
	}

	@org.junit.jupiter.api.Test
	void crearPlato() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		boolean exito = servicio.nuevoPlato("Plato no disponible", "plato que voy a cambiar a no disponible", 20d, 1);
		assertTrue(exito);
	}

	@org.junit.jupiter.api.Test
	public void platosByRestaurante() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		assertTrue(servicio.getMenuByRestaurante(1).size() == 1);
	}

	@org.junit.jupiter.api.Test
	public void buscarRestaurantes() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		assertTrue(servicio.getRestaurantesByFiltros("periqui", true, true, true).size() == 1);
		assertTrue(servicio.getRestaurantesByFiltros("venta", true, true, true).size() == 0);
	}
	
	
	@org.junit.jupiter.api.Test
	public void buscarRestaurantesPorResponsable() {
		ServicioGestionPlataforma servicio= ServicioGestionPlataforma.getServicioGestionPlataforma();
		List<RestauranteDTO> u= servicio.getRestaurantesByResponsable(1);
		assertTrue(u.size()==1);
	}
	
	@org.junit.jupiter.api.Test
	public void buscarUsuariosRestauranteSinValidar() {
		ServicioGestionPlataforma servicio= ServicioGestionPlataforma.getServicioGestionPlataforma();
		List<UsuarioDTO> u = servicio.getUsuarioTipoRestauranteNoValidados();
		assertTrue(u.size()==1);
	}
	
	@org.junit.jupiter.api.Test
	public void crearIncidencia() {
		ServicioGestionPlataforma servicio= ServicioGestionPlataforma.getServicioGestionPlataforma();
		LocalDate fechaCreacion= LocalDate.now();
		LocalDate fechaCierre = LocalDate.of(2023, 11, 10);
		LocalDate fechaAlta = LocalDate.of(2022, 10, 20);
		Integer i = servicio.registrarIncidencia(fechaCreacion, "No tenia suficientes pepinillos", fechaAlta," Quiero mas pepinillos" , fechaCierre,2,1);
		assertTrue(i != null);
	}
	}

