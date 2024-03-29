package aadd.persistencia.mongo.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import aadd.persistencia.mongo.bean.Direccion;

public class DireccionDAO extends ExtensionMongoDAO<Direccion> {

	private static DireccionDAO direccionDAO;

	public static DireccionDAO getDireccionDAO() {
		if (direccionDAO == null) {
			direccionDAO = new DireccionDAO();
		}
		return direccionDAO;
	}

	@Override
	public void createCollection() {
		collection = db.getCollection("direccion", Direccion.class).withCodecRegistry(defaultCodecRegistry); 
		collection.createIndex(Indexes.geo2dsphere("coordenadas")); 
	}
	public Integer countAllDirecciones(){
		FindIterable<Direccion> resultados = collection.find();

		MongoCursor<Direccion> it = resultados.iterator();
		List<Direccion> direcciones = new ArrayList<Direccion>();
		while (it.hasNext()) {
			direcciones.add(it.next());
		}
		return direcciones.size();
	}

	public List<Direccion> findOrdenadoPorCercania(Double latitud, Double longitud, int limite, int skip) {
		Point puntoBusqueda = new Point(new Position(longitud, latitud));
		Bson query = Filters.near("coordenadas", puntoBusqueda, null, null); // pasamos null para que no nos limite a
																				// una distancia concreta
		FindIterable<Direccion> resultados = collection.find(query).limit(limite).skip(skip); 

		MongoCursor<Direccion> it = resultados.iterator();
		List<Direccion> direcciones = new ArrayList<Direccion>();
		while (it.hasNext()) {
			direcciones.add(it.next());
		}
		return direcciones;
	}

	public Direccion findByRestaurante(Integer restaurante) {
		Bson query = Filters.eq("restaurante", restaurante); // igualdad con eq, busque en el campo restaurante de
																// nuestro documento y lo compare con el id que nos
																// entra
		FindIterable<Direccion> resultados = collection.find(query);
		MongoCursor<Direccion> it = resultados.iterator();
		return it.tryNext();
	}

}