package es.unican.supermercado.daoLayer;

import java.util.List;

import es.unican.supermercado.businessLayer.entities.EstadoPedido;
import es.unican.supermercado.businessLayer.entities.Pedido;

/**
 * Interfaz proporcionada por el DAO de Pedidos
 * @author MacbookAir
 *
 */
public interface IPedidosDAO {

	public Pedido addPedido(Pedido pedido);
	
	public void deletePedido(Pedido pedido);
	
	public Pedido updatePedido(Pedido pedido);
	
	public Pedido getPedido(long id);
	
	public Pedido getUltimoPedidoPendiente(EstadoPedido estado);
	
	public List<Pedido> listPedidos();
}
