package es.unican.supermercado.daoLayer.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import es.unican.supermercado.businessLayer.entities.Articulo;
import es.unican.supermercado.daoLayer.IArticulosDAO;

/**
 * Clase DAO para persistir articulos
 * @author Juan Manuel Lomas
 *
 */
@Stateless
public class ArticulosDAO extends GenericDAO<Articulo> implements IArticulosDAO {

	public Articulo addArticulo(Articulo articulo) {
		return create(articulo);
	}

	public void deleteArticulo(Articulo articulo) {
		delete(articulo);
	}

	public Articulo updateArticulo(Articulo articulo) {
		return update(articulo);
	}

	public Articulo getArticulo(String id) {
		return find(id);
	}

	public List<Articulo> listArticulos() {
		return findAll();
	}

	public Articulo getArticuloNombre(String nombre) {
		Query q = em.createNamedQuery("articuloPorNombre");
		q.setParameter("nombre", nombre);
		q.setFirstResult(0);
		q.setMaxResults(1);
		
		return (Articulo) q.getSingleResult();
	}
	
}
