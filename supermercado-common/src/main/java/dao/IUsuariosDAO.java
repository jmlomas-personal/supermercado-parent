package dao;

import java.util.List;

import domain.Usuario;

public interface IUsuariosDAO {

	public Usuario addUsuario(Usuario usuario);

	public void deleteUsuario(Usuario usuario);

	public Usuario updateUsuario(Usuario usuario);

	public Usuario getUsuario(String id);

	public List<Usuario> listUsuarios();
}
