package business;

import javax.ejb.EJB;

import dao.IPedidosDAO;
import dao.IUsuariosDAO;
import domain.LineaPedido;
import domain.Pedido;
import domain.Usuario;

public class GestionaPedidos implements IGestionaPedidos, IRealizaPedidos {

	@EJB
	private IPedidosDAO pedidosDAO;
	@EJB
	private IUsuariosDAO usuariosDAO;

	
	public Pedido procesarPedido() {
		// TODO Auto-generated method stub
		return null;
	}

	public void entregarPedido(Pedido pedido) {
		// TODO Auto-generated method stub
		
	}

	public void crearPedido(Usuario u) {
		// TODO Auto-generated method stub
		
	}

	public void anyadeLineaPedido(LineaPedido lineaPedido) {
		// TODO Auto-generated method stub
		
	}

	public void eliminaLineaPedido(LineaPedido lineaPedido) {
		// TODO Auto-generated method stub
		
	}

	public Pedido confirmarPedido() {
		// TODO Auto-generated method stub
		return null;
	}

}
