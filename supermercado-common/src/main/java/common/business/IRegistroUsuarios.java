package common.business;

import common.domain.Usuario;
import common.utils.UsuarioYaExisteException;

public abstract interface IRegistroUsuarios {

	public Usuario altaUsuario(Usuario usuario) throws UsuarioYaExisteException;
}
