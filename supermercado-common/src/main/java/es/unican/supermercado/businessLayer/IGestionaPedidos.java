package es.unican.supermercado.businessLayer;

import es.unican.supermercado.businessLayer.entities.Pedido;

/**
 * Interfaz proporcionada por GestionPedidos
 * @author MacbookAir
 *
 */
public abstract interface IGestionaPedidos {

	public Pedido procesarPedido();

	public Pedido entregarPedido(Long id);
	
}
