package aadd.persistencia.mongo.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.BsonNull;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import aadd.persistencia.mongo.bean.EstadoPedido;
import aadd.persistencia.mongo.bean.Pedido;

public class PedidoDAO extends ExtensionMongoDAO<Pedido> {
	private static PedidoDAO pedidoDAO;

	public static PedidoDAO getPedidoDAO() {
		if (pedidoDAO == null) {
			pedidoDAO = new PedidoDAO();
		}
		return pedidoDAO;
	}

	@Override
	public void createCollection() {
		collection = db.getCollection("pedido", Pedido.class).withCodecRegistry(defaultCodecRegistry);
	}

	public void updateEstado(ObjectId id, EstadoPedido p) {
		Bson filter = Filters.eq("_id", id);
		Document actualizacion = new Document("$push", new Document("estados", p));
		collection.updateOne(filter, actualizacion);
	}

	public List<Pedido> findByCliente(Integer usuario) {
		Bson query = Filters.eq("cliente", usuario);
		FindIterable<Pedido> resultados = collection.find(query);
		MongoCursor<Pedido> it = resultados.iterator();
		List<Pedido> pedidos = new ArrayList<Pedido>();
		while (it.hasNext()) {
			pedidos.add(it.next());
		}
		return pedidos;
	}

	public List<Pedido> findByRestaurante(Integer restaurante) {
		Bson query = Filters.eq("restaurante", restaurante);
		FindIterable<Pedido> resultados = collection.find(query);
		MongoCursor<Pedido> it = resultados.iterator();
		List<Pedido> pedidos = new ArrayList<Pedido>();
		while (it.hasNext()) {
			pedidos.add(it.next());
		}
		return pedidos;
	}

	// filtrar por pedidos que no tienen riders, por estado tambien
	public void asignarRepartidor(ObjectId id, Integer repartidor) {
		Bson filter = Filters.eq("_id", id);
		Document actualizacion = new Document("$set", new Document("repartidor", repartidor));
		collection.updateOne(filter, actualizacion);
	}

	public List<Pedido> findPedidoSinRider() {
		

		Bson query= Filters.elemMatch("estados", Filters.eq("estado", "INICIO"));
		FindIterable<Pedido> resultados = collection.find(query);
		MongoCursor<Pedido> it = resultados.iterator();
		List<Pedido> pedidos = new ArrayList<Pedido>();
		while (it.hasNext()) {
			pedidos.add(it.next());
		}
		return pedidos;
	}

	public List<Pedido> findPedidoByRider(Integer repartidor) {
		Bson query = Filters.eq("repartidor", repartidor);
		FindIterable<Pedido> resultados = collection.find(query);
		MongoCursor<Pedido> it = resultados.iterator();
		List<Pedido> pedidos = new ArrayList<Pedido>();
		while (it.hasNext()) {
			pedidos.add(it.next());
		}
		return pedidos;
	}

}
