package common.business;

import javax.ejb.Remote;

import common.domain.Usuario;
import common.utils.UsuarioYaExisteException;

@Remote
public abstract interface IRegistroUsuarios {

	public Usuario altaUsuario(Usuario usuario) throws UsuarioYaExisteException;
}
