package business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.IUsuariosDAO;
import domain.Usuario;
import utils.UsuarioYaExisteException;

@Stateless
public class GestionUsuarios implements IRegistroUsuarios {

	@EJB
	private IUsuariosDAO usuariosDAO;

	public GestionUsuarios(){

	}

	/**
	 * Metodo que da de alta al usuario pasado como parametro.
	 * Si un usuario con ese dni ya existe en el sistema lanza una excepcion
	 * @throws UsuarioYaExisteException 
	 */
	public Usuario altaUsuario(Usuario usuario) throws UsuarioYaExisteException {
		Usuario usuarioAux = usuariosDAO.getUsuarioDni(usuario.getDni());

		if(usuarioAux != null){
			throw new UsuarioYaExisteException();
		}
		return usuariosDAO.addUsuario(usuarioAux);
	}

}
