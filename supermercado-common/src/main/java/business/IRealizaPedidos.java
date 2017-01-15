package business;

import domain.LineaPedido;
import domain.Pedido;
import utils.StockInsuficienteException;
import utils.UsuarioNoExisteException;

public abstract interface IRealizaPedidos {

	public void crearPedido(String dni) throws UsuarioNoExisteException;
	
	public Pedido anyadeLineaPedido(LineaPedido lineaPedido) throws StockInsuficienteException;
	
	public Pedido eliminaLineaPedido(LineaPedido lineaPedido);
	
	public Pedido confirmarPedido();
}
