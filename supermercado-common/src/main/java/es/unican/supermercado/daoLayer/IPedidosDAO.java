package es.unican.supermercado.daoLayer;

import java.util.List;

import javax.ejb.Remote;

import es.unican.supermercado.businessLayer.entities.EstadoPedido;
import es.unican.supermercado.businessLayer.entities.Pedido;

@Remote
public interface IPedidosDAO {

	public Pedido addPedido(Pedido pedido);
	
	public void deletePedido(Pedido pedido);
	
	public Pedido updatePedido(Pedido pedido);
	
	public Pedido getPedido(String id);
	
	public Pedido getUltimoPedidoPendiente(EstadoPedido estado);
	
	public List<Pedido> listPedidos();
}
