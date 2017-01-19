package es.unican.supermercado.daoLayer.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import es.unican.supermercado.businessLayer.entities.Usuario;
import es.unican.supermercado.daoLayer.IUsuariosDAOLocal;
import es.unican.supermercado.daoLayer.IUsuariosDAORemote;

/**
 * Clase DAO para persistir usuarios
 * @author Juan Manuel Lomas
 *
 */
@Stateless
public class UsuariosDAO extends GenericDAO<Usuario> implements IUsuariosDAORemote, IUsuariosDAOLocal {

	@Override
	public Usuario addUsuario(Usuario usuario) {
		return create(usuario);		
	}

	@Override
	public void deleteUsuario(Usuario usuario) {
		delete(usuario);
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) {
		return update(usuario);
	}

	@Override
	public Usuario getUsuario(String id) {
		return find(id);
	}

	@Override
	public List<Usuario> listUsuarios() {
		return findAll();
	}

	@Override
	public Usuario getUsuarioDni(String dni) {
		Query q = em.createNamedQuery("usuariosPorDni");
		q.setParameter("dni", dni);
		q.setFirstResult(0);
		q.setMaxResults(1);
		
		try{
			return (Usuario) q.getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}
	
}
