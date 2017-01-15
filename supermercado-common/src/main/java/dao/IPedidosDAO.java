package dao;

import java.util.List;

import domain.Pedido;
import domain.EstadoPedido;

public interface IPedidosDAO {

	public void addPedido(Pedido pedido);
	
	public Pedido deletePedido(Pedido pedido);
	
	public Pedido updatePedido(Pedido pedido);
	
	public Pedido getPedido(String id);
	
	public Pedido getUltimoPedidoPendiente(EstadoPedido estado);
	
	public List<Pedido> listPedidos();
}
