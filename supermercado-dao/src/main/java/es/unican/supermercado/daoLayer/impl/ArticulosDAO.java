package es.unican.supermercado.daoLayer.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import es.unican.supermercado.businessLayer.entities.Articulo;
import es.unican.supermercado.daoLayer.IArticulosDAOLocal;
import es.unican.supermercado.daoLayer.IArticulosDAORemote;

/**
 * Clase DAO para persistir articulos
 * @author Juan Manuel Lomas
 *
 */
@Stateless
public class ArticulosDAO extends GenericDAO<Articulo> implements IArticulosDAORemote, IArticulosDAOLocal {

	@Override
	public Articulo addArticulo(Articulo articulo) {
		return create(articulo);
	}

	@Override
	public void deleteArticulo(Articulo articulo) {
		delete(articulo);
	}

	@Override
	public Articulo updateArticulo(Articulo articulo) {
		return update(articulo);
	}

	@Override
	public Articulo getArticulo(long id) {
		return find(id);
	}

	@Override
	public List<Articulo> listArticulos() {
		return findAll();
	}

	@Override
	public Articulo getArticuloNombre(String nombre) {
		
		Query q = em.createNamedQuery("articuloPorNombre");
		q.setParameter("nombre", nombre);
		q.setFirstResult(0);
		q.setMaxResults(1);			
		
		try{
			return (Articulo) q.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
		
	}
	
}
