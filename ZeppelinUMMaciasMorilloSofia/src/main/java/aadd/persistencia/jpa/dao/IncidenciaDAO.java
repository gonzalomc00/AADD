package aadd.persistencia.jpa.dao;

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

}
