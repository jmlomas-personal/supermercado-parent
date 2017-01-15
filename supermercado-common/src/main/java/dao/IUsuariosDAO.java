package dao;

import java.util.List;

import domain.Usuario;

public interface IUsuariosDAO {

	public void addUsuario(Usuario Usuario);

	public Usuario deleteUsuario(Usuario Usuario);

	public Usuario updateUsuario(Usuario Usuario);

	public Usuario getUsuario(String id);

	public List<Usuario> listUsuarios();
}
