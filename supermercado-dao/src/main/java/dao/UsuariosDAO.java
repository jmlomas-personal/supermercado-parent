package dao;

import java.util.List;

import javax.ejb.Stateless;

import domain.Usuario;

/**
 * Clase DAO para persistir usuarios
 * @author Juan Manuel Lomas
 *
 */
@Stateless
public class UsuariosDAO extends GenericDAO<Usuario> implements IUsuariosDAO {

	public Usuario addUsuario(Usuario usuario) {
		return create(usuario);		
	}

	public void deleteUsuario(Usuario usuario) {
		delete(usuario);
	}

	public Usuario updateUsuario(Usuario usuario) {
		return update(usuario);
	}

	public Usuario getUsuario(String id) {
		return find(id);
	}

	public List<Usuario> listUsuarios() {
		return findAll();
	}
	
}
