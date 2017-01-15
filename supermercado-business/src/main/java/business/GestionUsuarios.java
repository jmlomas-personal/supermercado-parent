package business;

import javax.ejb.EJB;

import dao.IUsuariosDAO;
import domain.Usuario;

public class GestionUsuarios implements IRegistroUsuarios {

	@EJB
	private IUsuariosDAO usuariosDAO;

	public GestionUsuarios(){

	}

	public void altaUsuario(Usuario usuario) {
		Usuario usuarioAux = usuariosDAO.getUsuarioDni(usuario.getDni());

		if
	}

}
