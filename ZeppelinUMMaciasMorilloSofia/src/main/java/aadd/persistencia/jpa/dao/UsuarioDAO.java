package aadd.persistencia.jpa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import aadd.persistencia.dto.UsuarioDTO;
import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.persistencia.jpa.bean.Usuario;

public class UsuarioDAO extends ExtensionDAO<Usuario> {

	public UsuarioDAO(Class<Usuario> persistedClass) {
		super(persistedClass);
	}

	private static UsuarioDAO usuarioDAO;

	public static UsuarioDAO getUsuarioDAO() {
		if (usuarioDAO == null)
			usuarioDAO = new UsuarioDAO(Usuario.class);
		return usuarioDAO;
	}

	public List<UsuarioDTO> findByEmailClave(String email, String clave) {
		try {
			Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Usuario.findByEmailClave");
			query.setParameter("email", email);
			query.setParameter("clave", clave);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}

	// invocaciones

	public List<UsuarioDTO> findByEmail(String email) {
		try {
			Query query = EntityManagerHelper.getEntityManager().createNamedQuery("Usuario.findByEmail"); // le pasamos
																											// el nombre
																											// de la
																											// consulta
																						// nombrada
			query.setParameter("email", email); // y el paramatro de la consulta
			return transformarToDTO(query.getResultList()); // resultados de la consulta
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<UsuarioDTO> findUsuarioByTipoRestauranteNoValidado(){
		try {
			String queryString = "SELECT u FROM Usuario u"
					+" WHERE u.id is not null AND u.tipo like 'RESTAURANTE' AND u.validado=0";
			Query query = EntityManagerHelper.getEntityManager().createQuery(queryString);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Integer> findIdsByTipo(List<TipoUsuario> tipos){
	    try {
	        String queryString = " select model.id from "+name+" model "
	            + " where  model.validado = true and (";
	        for(int i=0;i<tipos.size();i++) {
	            queryString +=" model.tipo = :tipo"+i;
	            if(i<tipos.size()-1) {
	                queryString +=" or ";
	            }
	        }
	        queryString +=")";
	        Query query = EntityManagerHelper.getEntityManager().createQuery(queryString);
	        for(int i=0;i<tipos.size();i++) {
	            query.setParameter("tipo"+i, tipos.get(i));
	        }
	        return query.getResultList();
	    } catch (RuntimeException re) {
	        throw re;
	    }
	}

	public List<UsuarioDTO> transformarToDTO(List<Usuario> usuarios) {
		List<UsuarioDTO> users = new ArrayList<UsuarioDTO>();
		for (Usuario u : usuarios) {
			users.add(new UsuarioDTO(u.getId(), u.getNombre(), u.getApellidos(), u.getFechaNacimiento(), u.getTipo(),u.getEmail()));
		}
		return users;
	}
}
