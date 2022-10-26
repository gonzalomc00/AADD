package aadd.persistencia.mongo.dao;

import aadd.persistencia.mongo.bean.EstadoPedido;
import aadd.persistencia.mongo.bean.ItemPedido;

public class ItemPedidoDAO extends ExtensionMongoDAO<ItemPedido> {

	private static ItemPedidoDAO itemPedidoDAO;

	public static ItemPedidoDAO getEstadoPedidoDAO() {
		if (itemPedidoDAO == null) {
			itemPedidoDAO = new ItemPedidoDAO();
		}
		return itemPedidoDAO;
	}

	@Override
	public void createCollection() {
		collection = db.getCollection("itemPedido", ItemPedido.class).withCodecRegistry(defaultCodecRegistry);

	}
	
}
