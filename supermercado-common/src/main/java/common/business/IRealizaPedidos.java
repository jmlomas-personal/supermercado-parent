package common.business;

import java.sql.Date;

import javax.ejb.Remote;

import common.domain.LineaPedido;
import common.domain.Pedido;
import common.utils.StockInsuficienteException;
import common.utils.UsuarioNoExisteException;

@Remote
public abstract interface IRealizaPedidos {

	public void crearPedido(String dni) throws UsuarioNoExisteException;
	
	public Pedido anyadeLineaPedido(LineaPedido lineaPedido) throws StockInsuficienteException;
	
	public Pedido eliminaLineaPedido(LineaPedido lineaPedido);
	
	public Pedido confirmarPedido(Date horaRecogida);
}
