package aadd.persistencia.jpa.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import aadd.persistencia.dto.PlatoDTO;
import aadd.persistencia.dto.RestauranteDTO;
import aadd.persistencia.jpa.bean.Plato;

public class PlatoDAO extends ExtensionDAO<Plato> {

	public PlatoDAO(Class<Plato> persistedClass) {
		super(persistedClass);
	}

	private static PlatoDAO platoDAO;

	public static PlatoDAO getPlatoDAO() {
		if (platoDAO == null)
			platoDAO = new PlatoDAO(Plato.class);
		return platoDAO;
	}

	public List<PlatoDTO> findPlatosDisponiblesByRestaurante(Integer restaurante, boolean mostrarTodos) {
		try {
			Query query;
			if (!mostrarTodos) {
				query = EntityManagerHelper.getEntityManager()
						.createNamedQuery("Plato.findPlatosDisponiblesByRestaurante");
			
			} else {
				query = EntityManagerHelper.getEntityManager().createNamedQuery("Plato.findAllPlatosByRestaurante");
			}
			query.setParameter("restaurante", restaurante);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<PlatoDTO> transformarToDTO(List<Plato> platos) {
		List<PlatoDTO> menu = new ArrayList<PlatoDTO>();
		for (Plato p : platos) {
			menu.add(new PlatoDTO(p.getId(), p.getDescripcion(), p.getTitulo(), p.getPrecio(),p.isDisponibilidad()));
		}
		return menu;
	}

}