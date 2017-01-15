package business;

import java.sql.Date;

import javax.ejb.EJB;

import dao.IArticulosDAO;
import dao.IPedidosDAO;
import dao.IUsuariosDAO;
import domain.Articulo;
import domain.EstadoPedido;
import domain.LineaPedido;
import domain.Pedido;
import domain.Usuario;
import utils.StockInsuficienteException;
import utils.UsuarioNoExisteException;

public class GestionPedidos implements IGestionaPedidos, IRealizaPedidos {

	@EJB
	private IPedidosDAO pedidosDAO;
	@EJB
	private IUsuariosDAO usuariosDAO;
	@EJB
	private IArticulosDAO articulosDAO;

	private Pedido pedidoPreparacion;

	public GestionPedidos(){

	}

	/**
	 * Metodo que obtiene el ultimo pedido pendiente de la capa de persistencia y modifica
	 * su estado de pendiente a procesado
	 * @return Pedido con el estado cambiado o Null si no quedaban pedidos pendientes
	 * de procesar
	 */
	public Pedido procesarPedido() {
		Pedido pedido = pedidosDAO.getUltimoPedidoPendiente(EstadoPedido.PENDIENTE);
		if(pedido != null){
			pedido.setEstado(EstadoPedido.PROCESADO);
			pedidosDAO.updatePedido(pedido);
		}
		return pedido;
	}

	/**
	 * Metodo que obtiene el pedido con el id pasado como parametro y modifica su
	 * estado a entregado
	 * @return Pedido con el estado entregado o Null si no existe un pedido
	 * con dicho id
	 */
	public Pedido entregarPedido(String id) {
		Pedido pedido = pedidosDAO.getPedido(id);
		if(pedido != null){
			pedido.setEstado(EstadoPedido.ENTREGADO);
			pedidosDAO.updatePedido(pedido);
		}
		return pedido;
	}

	/**
	 * Crea un pedido para el usuario con el dni introducido.
	 * @param dni del usuario que realiza el pedido
	 * @throws UsuarioNoExisteException 
	 */
	public void crearPedido(String dni) throws UsuarioNoExisteException {
		Usuario usuario = usuariosDAO.getUsuario(dni);
		if(usuario == null){
			throw new UsuarioNoExisteException();
		}

		this.pedidoPreparacion = new Pedido();
		pedidoPreparacion.setUsuario(usuario);
	}

	/**
	 * Metodo que anyade una linea de pedido al pedido del usuario
	 * @throws StockInsuficienteException 
	 * 
	 */
	public Pedido anyadeLineaPedido(LineaPedido lineaPedido) throws StockInsuficienteException {
		int cantidad = lineaPedido.getCantidad(); 
		Articulo artAux = lineaPedido.getArticulo();
		int stock = artAux.getUnidadesStock();

		if(cantidad < stock){
			throw new StockInsuficienteException();
		} else {
			artAux.setUnidadesStock(stock - cantidad);
			articulosDAO.updateArticulo(artAux);
			this.pedidoPreparacion.anyadeLineaPedido(lineaPedido);
		}

		return this.pedidoPreparacion;
	}

	/**
	 * Metodo que elimina una linea de pedido al pedido del usuario
	 */
	public Pedido eliminaLineaPedido(LineaPedido lineaPedido) {
		int cantidad = lineaPedido.getCantidad(); 
		Articulo artAux = lineaPedido.getArticulo();
		int stock = artAux.getUnidadesStock();

		artAux.setUnidadesStock(stock + cantidad);
		articulosDAO.updateArticulo(artAux);
		this.pedidoPreparacion.eliminaLineaPedido(lineaPedido);
		return this.pedidoPreparacion;
	}

	public Pedido confirmarPedido(Date horaRecogida ) {
		this.pedidoPreparacion.setHoraRecogida(horaRecogida);
		pedidosDAO.addPedido(pedidoPreparacion);
		return pedidoPreparacion;
	}

}
