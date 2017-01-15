package dao;

import java.util.List;

import domain.Pedido;

public interface IPedidosDAO {

	public void addPedido(Pedido pedido);
	
	public Pedido deletePedido(Pedido pedido);
	
	public Pedido updatePedido(Pedido pedido);
	
	public Pedido getPedidoo(String id);
	
	public List<Pedido> listPedidos();
}
