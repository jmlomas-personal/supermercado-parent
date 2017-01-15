package business;

import domain.Pedido;


public abstract interface IGestionaPedidos {

	public Pedido procesarPedido();

	public void entregarPedido(Pedido pedido);
	
}
