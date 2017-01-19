package es.unican.supermercado.businessLayer;

import javax.ejb.Remote;

import es.unican.supermercado.businessLayer.entities.Usuario;
import es.unican.supermercado.utils.UsuarioYaExisteException;

@Remote
public abstract interface IRegistroUsuarios {

	public Usuario altaUsuario(Usuario usuario) throws UsuarioYaExisteException;
}
