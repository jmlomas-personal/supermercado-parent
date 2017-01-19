package common.dao;

import java.util.List;

import javax.ejb.Remote;

import common.domain.Usuario;

@Remote
public interface IUsuariosDAO {

	public Usuario addUsuario(Usuario usuario);

	public void deleteUsuario(Usuario usuario);

	public Usuario updateUsuario(Usuario usuario);

	public Usuario getUsuario(String id);
	
	public Usuario getUsuarioDni(String dni);

	public List<Usuario> listUsuarios();
}
