package aadd.persistencia.jpa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import aadd.persistencia.dto.IncidenciaDTO;
import aadd.persistencia.jpa.bean.Incidencia;
import aadd.persistencia.jpa.bean.Usuario;

public class IncidenciaDAO extends ExtensionDAO<Incidencia> {

	public IncidenciaDAO(Class<Incidencia> persistedClass) {
		super(persistedClass);
	}

	private static IncidenciaDAO incidenciaDAO;

	public static IncidenciaDAO getIncidenciaDAO() {
		if (incidenciaDAO == null)
			incidenciaDAO = new IncidenciaDAO(Incidencia.class);
		return incidenciaDAO;
	}

	public List<IncidenciaDTO> findIncidenciaByUsuario(int id_usuario) {
		try {
			Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Incidencia.findByUsuario");
			query.setParameter("usuario", id_usuario);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<IncidenciaDTO> transformarToDTO(List<Incidencia> incidencias) {
		List<IncidenciaDTO> inci = new ArrayList<IncidenciaDTO>();
		for (Incidencia i : incidencias) {
			inci.add(new IncidenciaDTO(i.getId(), i.getFechaCreacion(), i.getDescripcion(), i.getComentario(),
					i.getFechaCierre()));
		}
		return inci;
	}

	public List<IncidenciaDTO> findIncidenciaSinCerrarRestaurante(Integer id_restaurante) {
		try {
			String queryString = " SELECT i FROM Incidencia i "
					+ " WHERE i.id is not null AND i.fechaCierre is null AND i.restaurante.id like :id_restaurante ";

			Query query = EntityManagerHelper.getEntityManager().createQuery(queryString);
			query.setParameter("id_restaurante", "%" + id_restaurante + "%");
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
