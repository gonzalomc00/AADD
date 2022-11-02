package aadd.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import aadd.persistencia.dto.IncidenciaDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.dto.UsuarioDTO;
import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.zeppelinum.ServicioGestionPedido;
import aadd.zeppelinum.ServicioGestionPlataforma;

class Test {
	@org.junit.jupiter.api.Test
	void crearUsuario() { // lo podemos ver actualizando la BBDD
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario = servicio.registrarUsuario("Pepe", "Alcaraz", fechaNacimiento, "misterbinario@umu.es", "12345",
				TipoUsuario.RESTAURANTE);
		assertTrue(usuario != null);
	}

	// NUEVO USUARIO DE TIPO CLIENTE
	@org.junit.jupiter.api.Test
	void crearUsuario2() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		LocalDate fechaNacimiento = LocalDate.of(1990, 1, 8);
		Integer usuario = servicio.registrarUsuario("Mari", "Legaz", fechaNacimiento, "mclg@um.es", "12345",
				TipoUsuario.CLIENTE);
		assertTrue(usuario != null);
	}

	@org.junit.jupiter.api.Test
	void crearCategoria() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		Integer categoria = servicio.crearCategoria("Familiar");
		assertTrue(categoria != null);
	}

/**	@org.junit.jupiter.api.Test TODO: HE CAMBIADO LOS PARAMETROS DEL RESTAURANTE
	void crearRestaurantePlato() { // MONGO INCLUIDO
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		List<Integer> categorias = new LinkedList<Integer>();

		Integer rest = servicio.registrarRestaurante("Puerta de Murcia", 1, categorias, "Rio Madera", "30110", null,
				"Murcia", 38.009109654488476, -1.1339542029796663);
		Integer rest2 = servicio.registrarRestaurante("Pistatxo", 1, categorias, "Alfaro", "30001", 12, "Murcia",
				37.98654993575417, -1.1305437741450695);
		Integer rest3 = servicio.registrarRestaurante("El Barrilero de Jose", 1, categorias, "Marqués de Espinardo",
				"30100", 4, "Murcia", 38.00805160364204, -1.152337749004084);
		assertTrue(rest != null);
		assertTrue(rest2 != null);
		assertTrue(rest3 != null);
		boolean exito = servicio.nuevoPlato("Marmitako de bonito", "plato de bonito, patatas y cebolla con verduras",
				20d, rest);
		assertTrue(exito);
	}**/

	/// teST PARA ORDENAR RESTAURANTES POR CERCANIA
	@org.junit.jupiter.api.Test
	void buscarRestaurantesOrdenados() { // SI FUNCIONA
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();

		List<RestauranteDTO> restaurantes = servicio.getRestaurantesByCercanía(38.02410905700919, -1.1740641907325182,
				20, 0);
		for (RestauranteDTO r : restaurantes) {
			System.out.println(r.getNombre());
		}
		assertTrue(restaurantes.size() > 0);
	}

	@org.junit.jupiter.api.Test
	void addCategoria() {
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
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		List<RestauranteDTO> u = servicio.getRestaurantesByResponsable(1);
		assertTrue(u.size() == 1);
	}

	@org.junit.jupiter.api.Test
	public void buscarUsuariosRestauranteSinValidar() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		List<UsuarioDTO> u = servicio.getUsuarioTipoRestauranteNoValidados();
		assertTrue(u.size() == 1);
	}

	@org.junit.jupiter.api.Test
	public void crearIncidencia() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		LocalDate fechaCreacion = LocalDate.now();
		LocalDate fechaCierre = LocalDate.of(2023, 11, 10);
		LocalDate fechaAlta = LocalDate.of(2022, 10, 20);
		Integer i = servicio.registrarIncidencia(fechaCreacion, "No tenia suficientes pepinillos", fechaAlta,
				"Quiero mas pepinillos",null,2,1);
		assertTrue(i != null);
	}
	
	@org.junit.jupiter.api.Test
	public void buscarIncidenciaByUsuario() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		List<IncidenciaDTO> i =servicio.getIncidenciaByUsuario(2);
		assertTrue(i.size()==1);
	}
	
	@org.junit.jupiter.api.Test
	public void buscarIncidenciaSinCerrar() {
		ServicioGestionPlataforma servicio = ServicioGestionPlataforma.getServicioGestionPlataforma();
		List<IncidenciaDTO> i= servicio.getIncidenciaSinCerrar();
		assertTrue(i.size()==1);
	}

	// TEST OPINIONES MONGO
	@org.junit.jupiter.api.Test
	void crearOpinion() {
		ServicioGestionPedido servicio = ServicioGestionPedido.getServicioGestionPedido();
		servicio.opinar(4, 5, "Todo estupendo y muy rico", 10d);
		servicio.opinar(4, 1, "La comida llegó un poco fría", 7.5d);
		servicio.opinar(4, 3, "El menú es un poco escaso, pero todo muy bueno", 8d);
		servicio.opinar(4, 4, "Nos trajeron un plato cambiado", 5d);
	}

	@org.junit.jupiter.api.Test
	void buscarOpiniones() {
		ServicioGestionPedido servicio = ServicioGestionPedido.getServicioGestionPedido();
		assertTrue(servicio.findByUsuario(4).size() == 4);
		assertTrue(servicio.findByRestaurante(5).size() == 1);
	}

}
