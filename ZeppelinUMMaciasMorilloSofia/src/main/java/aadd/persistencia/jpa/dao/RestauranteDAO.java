package aadd.persistencia.jpa.dao;

import aadd.persistencia.jpa.bean.Restaurante;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import aadd.persistencia.dto.RestauranteDTO;

public class RestauranteDAO extends ExtensionDAO<Restaurante> {

	public RestauranteDAO(Class<Restaurante> persistedClass) {
		super(persistedClass);
	}

	private static RestauranteDAO restauranteDAO;

	public static RestauranteDAO getRestauranteDAO() {
		if (restauranteDAO == null)
			restauranteDAO = new RestauranteDAO(Restaurante.class);
		return restauranteDAO;
	}

	public List<RestauranteDTO> transformarToDTO(List<Restaurante> restaurantes) {
		List<RestauranteDTO> rs = new ArrayList<RestauranteDTO>();
		for (Restaurante r : restaurantes) {
			rs.add(new RestauranteDTO(r.getId(), r.getNombre(), r.getValoracionGlobal()));
		}
		return rs;
	}

	public List<RestauranteDTO> findRestauranteByResponsable(Integer id_responsable, boolean conPlatos) {
		try {
			String queryString = " SELECT r FROM Restaurante r ";
			if (conPlatos) {
				queryString += " INNER JOIN r.platos p on p.disponibilidad = true ";
			}

			queryString += " WHERE r.id is not null ";

			if (id_responsable != null) {
				queryString += " AND r.responsable.id like :id_responsable ";
			}

			Query query = EntityManagerHelper.getEntityManager().createQuery(queryString);
			if (id_responsable != null) {
				query.setParameter("id_responsable", "%" + id_responsable + "%"); // like % cualquier cosa delante o
																					// detras
			}
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}

	// consulta puramente JPQL no nombrada por todo el rollo de los ifs (o algo de
	// eso)
	public List<RestauranteDTO> findRestauranteByFiltros(String keyword, LocalDate fechaAlta, boolean mejorValorados,
			boolean sinPenalizacion) {
		try {
			String queryString = " SELECT r FROM Restaurante r " + " INNER JOIN r.platos p on p.disponibilidad = true " // Con
																														// el
																														// inner
																														// join
																														// me
																														// aseguro
																														// de
																														// que
																														// no
																														// aparezcan
																														// restaurantes
																														// con
																														// 0
																														// platos
																														// disponibles
					+ " WHERE r.id is not null ";// Ponemos una condición que siempre es cierta para poder enlazar las
													// condiciones más fácilmente

			if (keyword != null) {
				queryString += " AND r.nombre like :keyword ";
			}
			if (fechaAlta != null) {
				queryString += " AND r.fechaAlta >= :fechaAlta ";
			}
			if (sinPenalizacion) {
				queryString += " AND r.numPenalizaciones = 0 ";
			}
			queryString += " GROUP BY r.id "; // solo salga una vez cada restaurante, tambien se puede poner un distinc

			if (mejorValorados) {
				queryString += " ORDER BY r.valoracionGlobal desc ";
			}

			Query query = EntityManagerHelper.getEntityManager().createQuery(queryString);
			if (keyword != null) {
				query.setParameter("keyword", "%" + keyword + "%"); // like % cualquier cosa delante o detras
			}
			if (fechaAlta != null) {
				query.setParameter("fechaAlta", fechaAlta);
			}

			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<RestauranteDTO> findRestauranteByFiltrosLazy(String keyword, LocalDate fechaAlta,
			boolean mejorValorados, boolean sinPenalizacion, int start, int max, List<Integer> categorias) {
		try {
			String queryString = " SELECT r FROM Restaurante r " + " INNER JOIN r.platos p on p.disponibilidad = true ";
			if (categorias != null && categorias.size()>0) {
				queryString += " INNER JOIN r.categorias c ";// Con el inner join me aseguro de que no aparezcan
																// restaurantes con
			} // 0 platos disponibles
			queryString += " WHERE r.id is not null ";// Ponemos una condición que siempre es cierta para poder enlazar
														// las
			// condiciones más fácilmente

			if (keyword != null) {
				queryString += " AND r.nombre like :keyword ";
			}
			if (fechaAlta != null) {
				queryString += " AND r.fechaAlta >= :fechaAlta ";
			}
			if (categorias != null  && categorias.size()>0) {
				queryString += " AND (";
				for (int i = 0; i < categorias.size(); i++) {
					queryString += " c.id = :categoria" + i;
					if (i < categorias.size() - 1) {
						queryString += " or ";
					}
				}
				queryString += ")";
			}
			
			if (sinPenalizacion) {
				queryString += " AND r.numPenalizaciones = 0 ";
			}
			queryString += " GROUP BY r.id ";

			if (mejorValorados) {
				queryString += " ORDER BY r.valoracionGlobal desc ";
			}

			Query query = EntityManagerHelper.getEntityManager().createQuery(queryString);
			if (keyword != null) {
				query.setParameter("keyword", "%" + keyword + "%");
			}
			if (fechaAlta != null) {
				query.setParameter("fechaAlta", fechaAlta);
			}

			
			if (categorias != null && categorias.size()>0) {
				for (int i = 0; i < categorias.size(); i++) {
					query.setParameter("categoria" + i, categorias.get(i));
				}
			}
			
			
			System.out.println(query);
			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			query.setFirstResult(start);
			query.setMaxResults(max);
			return transformarToDTO(query.getResultList());
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Number countRestaurantesByFiltros(String keyword, LocalDate fechaAlta, boolean sinPenalizacion) {
		try {
			String queryString = " SELECT count(distinct r) FROM Restaurante r "
					+ " INNER JOIN r.platos p on p.disponibilidad = true " // Con el inner join me aseguro de que no
																			// aparezcan restaurantes con 0 platos
																			// disponibles
					+ " WHERE r.id is not null ";// Ponemos una condición que siempre es cierta para poder enlazar las
													// condiciones más fácilmente

			if (keyword != null) {
				queryString += " AND r.nombre like :keyword ";
			}
			if (fechaAlta != null) {
				queryString += " AND r.fechaAlta >= :fechaAlta ";
			}
			if (sinPenalizacion) {
				queryString += " AND r.numPenalizaciones = 0 ";
			}

			Query query = EntityManagerHelper.getEntityManager().createQuery(queryString);
			if (keyword != null) {
				query.setParameter("keyword", "%" + keyword + "%");
			}
			if (fechaAlta != null) {
				query.setParameter("fechaAlta", fechaAlta);
			}

			query.setHint(QueryHints.REFRESH, HintValues.TRUE);
			return (Number) query.getSingleResult();
		} catch (RuntimeException re) {
			throw re;
		}
	}
}