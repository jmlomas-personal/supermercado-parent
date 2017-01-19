package common.business;

import javax.ejb.Remote;

import common.domain.Pedido;

@Remote
public abstract interface IGestionaPedidos {

	public Pedido procesarPedido();

	public Pedido entregarPedido(String id);
	
}
