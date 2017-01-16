package common.business;

import common.domain.Pedido;


public abstract interface IGestionaPedidos {

	public Pedido procesarPedido();

	public Pedido entregarPedido(String id);
	
}
