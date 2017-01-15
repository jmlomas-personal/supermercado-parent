package business;

import domain.Usuario;
import utils.UsuarioYaExisteException;

public abstract interface IRegistroUsuarios {

	public Usuario altaUsuario(Usuario usuario) throws UsuarioYaExisteException;
}
