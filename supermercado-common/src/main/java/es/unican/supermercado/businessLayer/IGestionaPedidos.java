package es.unican.supermercado.businessLayer;

import es.unican.supermercado.businessLayer.entities.Pedido;

public abstract interface IGestionaPedidos {

	public Pedido procesarPedido();

	public Pedido entregarPedido(String id);
	
}
