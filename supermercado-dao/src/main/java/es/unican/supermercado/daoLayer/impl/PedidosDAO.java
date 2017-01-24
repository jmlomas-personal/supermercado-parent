package es.unican.supermercado.daoLayer.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import es.unican.supermercado.businessLayer.entities.EstadoPedido;
import es.unican.supermercado.businessLayer.entities.Pedido;
import es.unican.supermercado.daoLayer.IPedidosDAOLocal;
import es.unican.supermercado.daoLayer.IPedidosDAORemote;

/**
 * Clase DAO para persistir pedidos
 * @author Juan Manuel Lomas
 *
 */
@Stateless
public class PedidosDAO extends GenericDAO<Pedido> implements IPedidosDAORemote, IPedidosDAOLocal {

	@Override
	public Pedido addPedido(Pedido pedido) {		
		return create(pedido);
	}

	@Override
	public void deletePedido(Pedido pedido) {
		delete(pedido);
	}

	@Override
	public Pedido updatePedido(Pedido pedido) {
		return update(pedido);
	}

	@Override
	public Pedido getPedido(long id) {
		return find(id);
	}

	@Override
	public List<Pedido> listPedidos() {
		return findAll();
	}

	@Override
	public Pedido getUltimoPedidoPendiente(EstadoPedido estado) {
		Query q = em.createNamedQuery("pedidoPorEstadoyFecha");
		q.setParameter("estado", EstadoPedido.PENDIENTE.name());
		q.setFirstResult(0);
		q.setMaxResults(1);
		
		try{
			return (Pedido) q.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}
	
}
