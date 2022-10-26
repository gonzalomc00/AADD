package aadd.persistencia.mongo.dao;

import aadd.persistencia.mongo.bean.ItemPedido;
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
}
