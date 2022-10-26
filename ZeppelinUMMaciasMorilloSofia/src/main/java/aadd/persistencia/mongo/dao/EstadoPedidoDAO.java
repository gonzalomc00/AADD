package aadd.persistencia.mongo.dao;

import aadd.persistencia.mongo.bean.EstadoPedido;
import aadd.persistencia.mongo.bean.Opinion;

public class EstadoPedidoDAO extends ExtensionMongoDAO<EstadoPedido> {

	private static EstadoPedidoDAO estadoPedidoDAO;
	
	public static EstadoPedidoDAO getEstadoPedidoDAO() {
		if(estadoPedidoDAO == null) {
			estadoPedidoDAO = new EstadoPedidoDAO();
		}
		return estadoPedidoDAO;
	}
	
	@Override
	public void createCollection() {
		collection = db.getCollection("estadoPedido", EstadoPedido.class).withCodecRegistry(defaultCodecRegistry);
		
	}
	
	

}
