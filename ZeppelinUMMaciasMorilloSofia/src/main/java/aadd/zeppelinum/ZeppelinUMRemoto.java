package aadd.zeppelinum;

import java.util.List;

import javax.ejb.Remote;

import org.bson.types.ObjectId;

import aadd.persistencia.dto.EstadisticaOpinionDTO;
import aadd.persistencia.dto.EstadisticaPedidoDTO;

@Remote
public interface ZeppelinUMRemoto {
	public Integer getNumVisitas(Integer idUsuario);

	public void pedidoIniciado(ObjectId pedido);

	public void pedidoNoRecogido(ObjectId pedido);

	public void pedidoNoPreparado(ObjectId pedido);
	
	public List<EstadisticaOpinionDTO> getEstadisticasOpinion(Integer idUsuario);
	
	public List<EstadisticaPedidoDTO> getEstadisticasPedidoDTO(Integer idUsuario);
	
	public List<EstadisticaPedidoDTO> getEstadisticasRestaurantePedidoDTO(Integer idUsuario);

	public Integer getPedidosRealizadosByUsuario(Integer idUsuario);

}