package es.unican.supermercado.businessLayer.impl;

import java.sql.Date;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import es.unican.supermercado.businessLayer.IGestionaPedidosLocal;
import es.unican.supermercado.businessLayer.IGestionaPedidosRemote;
import es.unican.supermercado.businessLayer.IRealizaPedidosLocal;
import es.unican.supermercado.businessLayer.IRealizaPedidosRemote;
import es.unican.supermercado.businessLayer.entities.Articulo;
import es.unican.supermercado.businessLayer.entities.EstadoPedido;
import es.unican.supermercado.businessLayer.entities.LineaPedido;
import es.unican.supermercado.businessLayer.entities.Pedido;
import es.unican.supermercado.businessLayer.entities.Usuario;
import es.unican.supermercado.daoLayer.IArticulosDAO;
import es.unican.supermercado.daoLayer.IArticulosDAORemote;
import es.unican.supermercado.daoLayer.IPedidosDAO;
import es.unican.supermercado.daoLayer.IPedidosDAORemote;
import es.unican.supermercado.daoLayer.IUsuariosDAO;
import es.unican.supermercado.daoLayer.IUsuariosDAORemote;
import es.unican.supermercado.utils.StockInsuficienteException;
import es.unican.supermercado.utils.UsuarioNoExisteException;

@Stateful
public class GestionPedidos implements IRealizaPedidosLocal, IRealizaPedidosRemote, IGestionaPedidosLocal, IGestionaPedidosRemote  {

	@EJB
	private IPedidosDAORemote pedidosDAO;
	
	@EJB
	private IUsuariosDAORemote usuariosDAO;
	
	@EJB
	private IArticulosDAORemote articulosDAO;

	private Pedido pedidoPreparacion;

	public GestionPedidos(){

	}

	/**
	 * Metodo que obtiene el ultimo pedido pendiente de la capa de persistencia y modifica
	 * su estado de pendiente a procesado
	 * @return Pedido con el estado cambiado o Null si no quedaban pedidos pendientes
	 * de procesar
	 */
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	 * @return El pedido (para mostrar su estado)
	 */
	@Override
	public Pedido eliminaLineaPedido(LineaPedido lineaPedido) {
		int cantidad = lineaPedido.getCantidad(); 
		Articulo artAux = lineaPedido.getArticulo();
		int stock = artAux.getUnidadesStock();

		artAux.setUnidadesStock(stock + cantidad);
		articulosDAO.updateArticulo(artAux);
		this.pedidoPreparacion.eliminaLineaPedido(lineaPedido);
		return this.pedidoPreparacion;
	}

	/**
	 * Metodo que confirma un pedido, anyadiendo este a la bd
	 * @return El pedido
	 */
	@Override
	public Pedido confirmarPedido(Date horaRecogida ) {
		this.pedidoPreparacion.setHoraRecogida(horaRecogida);
		pedidosDAO.addPedido(pedidoPreparacion);
		return pedidoPreparacion;
	}

	// Getters y setters. Necesarios para futuros tests unitarios con mockito para poder
	// asignar un valor a los atributos DAO ya que no disponemos de la inyeccion del
	// EJB.

	public IPedidosDAO getPedidosDAO() {
		return pedidosDAO;
	}

	public void setPedidosDAO(IPedidosDAO pedidosDAO) {
		this.pedidosDAO = (IPedidosDAORemote) pedidosDAO;
	}

	public IUsuariosDAO getUsuariosDAO() {
		return usuariosDAO;
	}

	public void setUsuariosDAO(IUsuariosDAO usuariosDAO) {
		this.usuariosDAO = (IUsuariosDAORemote) usuariosDAO;
	}

	public IArticulosDAO getArticulosDAO() {
		return articulosDAO;
	}

	public void setArticulosDAO(IArticulosDAO articulosDAO) {
		this.articulosDAO = (IArticulosDAORemote) articulosDAO;
	}

}
