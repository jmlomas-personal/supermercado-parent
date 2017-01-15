package business;

import domain.Pedido;


public abstract interface IGestionaPedidos {

	public Pedido procesarPedido();

	public Pedido entregarPedido(String id);
	
}
