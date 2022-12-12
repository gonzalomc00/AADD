package aadd.zeppelinum;

import java.util.List;

import javax.ejb.Remote;

import org.bson.types.ObjectId;

import aadd.persistencia.dto.EstadisticaOpinionDTO;

@Remote
public interface ZeppelinUMRemoto {
	public Integer getNumVisitas(Integer idUsuario);

	public void pedidoIniciado(ObjectId pedido);

	public List<EstadisticaOpinionDTO> getEstadisticasOpinion(Integer idUsuario);

	public Integer getPedidosRealizadosByUsuario(Integer idUsuario);

	public Integer getPedidosRestaurante(Integer idUsuario);

}