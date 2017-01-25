package es.unican.supermercado.businessLayer;

import es.unican.supermercado.businessLayer.entities.LineaPedido;
import es.unican.supermercado.businessLayer.entities.Pedido;
import es.unican.supermercado.utils.StockInsuficienteException;
import es.unican.supermercado.utils.UsuarioNoExisteException;

public abstract interface IRealizaPedidos {

	public void crearPedido(String dni) throws UsuarioNoExisteException;
	
	public Pedido anyadeLineaPedido(LineaPedido lineaPedido) throws StockInsuficienteException;
	
	public Pedido eliminaLineaPedido(LineaPedido lineaPedido);
	
	public Pedido confirmarPedido();//(Date horaRecogida);
}
