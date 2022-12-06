package aadd.persistencia.jpa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import aadd.persistencia.dto.CategoriaRestauranteDTO;
import aadd.persistencia.dto.UsuarioDTO;
import aadd.persistencia.jpa.bean.CategoriaRestaurante;

public class CategoriaRestauranteDAO extends ExtensionDAO<CategoriaRestaurante>{

	public CategoriaRestauranteDAO(Class<CategoriaRestaurante> persistedClass) {
		super(persistedClass);
	}
	
	private static CategoriaRestauranteDAO categoriaRestauranteDAO;
	
	public static CategoriaRestauranteDAO getCategoriaRestauranteDAO() {
		if (categoriaRestauranteDAO == null)
			categoriaRestauranteDAO = new CategoriaRestauranteDAO(CategoriaRestaurante.class);
		return categoriaRestauranteDAO;
	}
	
	public List<CategoriaRestauranteDTO> findAllCategoriasRestaurante(){
		try {
			String queryString = " SELECT c FROM CategoriaRestaurante c ";
			Query query = EntityManagerHelper.getEntityManager().createQuery(queryString);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public CategoriaRestaurante findByNombre(String nombre) {
		try {
			Query query = EntityManagerHelper.getEntityManager().createNamedQuery("CategoriaRestaurante.findByNombre");
			query.setParameter("nombre", nombre);
			List<CategoriaRestaurante> categoria = query.getResultList();
			return categoria.get(0);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	

	
	public List<CategoriaRestauranteDTO> transformarToDTO(List<CategoriaRestaurante> categorias) {
		List<CategoriaRestauranteDTO> categorias_dto = new ArrayList<CategoriaRestauranteDTO>();
		for (CategoriaRestaurante c : categorias) {
			categorias_dto.add(new CategoriaRestauranteDTO(c.getId(),c.getNombre()));
		}
		return categorias_dto;
	}

}
