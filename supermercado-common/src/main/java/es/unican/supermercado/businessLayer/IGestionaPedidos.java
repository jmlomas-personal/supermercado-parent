package es.unican.supermercado.businessLayer;

import javax.ejb.Remote;

import es.unican.supermercado.businessLayer.entities.Pedido;

@Remote
public abstract interface IGestionaPedidos {

	public Pedido procesarPedido();

	public Pedido entregarPedido(String id);
	
}
