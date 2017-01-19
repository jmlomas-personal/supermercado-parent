package es.unican.supermercado.daoLayer;

import java.util.List;

import javax.ejb.Remote;

import es.unican.supermercado.businessLayer.entities.Usuario;

@Remote
public interface IUsuariosDAO {

	public Usuario addUsuario(Usuario usuario);

	public void deleteUsuario(Usuario usuario);

	public Usuario updateUsuario(Usuario usuario);

	public Usuario getUsuario(String id);
	
	public Usuario getUsuarioDni(String dni);

	public List<Usuario> listUsuarios();
}
