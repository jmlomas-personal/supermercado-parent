package business;

import domain.LineaPedido;
import domain.Pedido;
import domain.Usuario;

public abstract interface IRealizaPedidos {

	public void crearPedido(Usuario u);
	
	public void anyadeLineaPedido(LineaPedido lineaPedido);
	
	public Pedido confirmarPedido();
}
