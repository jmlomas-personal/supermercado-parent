package es.unican.supermercado.businessLayer;

import java.sql.Date;

import javax.ejb.Remote;

import es.unican.supermercado.businessLayer.entities.LineaPedido;
import es.unican.supermercado.businessLayer.entities.Pedido;
import es.unican.supermercado.utils.StockInsuficienteException;
import es.unican.supermercado.utils.UsuarioNoExisteException;

@Remote
public abstract interface IRealizaPedidos {

	public void crearPedido(String dni) throws UsuarioNoExisteException;
	
	public Pedido anyadeLineaPedido(LineaPedido lineaPedido) throws StockInsuficienteException;
	
	public Pedido eliminaLineaPedido(LineaPedido lineaPedido);
	
	public Pedido confirmarPedido(Date horaRecogida);
}
