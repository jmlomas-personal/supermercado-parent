package es.unican.supermercado.businessLayer.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import es.unican.supermercado.businessLayer.IRegistroUsuariosLocal;
import es.unican.supermercado.businessLayer.IRegistroUsuariosRemote;
import es.unican.supermercado.businessLayer.entities.Usuario;
import es.unican.supermercado.daoLayer.IUsuariosDAO;
import es.unican.supermercado.daoLayer.IUsuariosDAORemote;
import es.unican.supermercado.utils.UsuarioYaExisteException;

@Stateless
public class GestionUsuarios implements IRegistroUsuariosLocal, IRegistroUsuariosRemote {

	@EJB
	private IUsuariosDAORemote usuariosDAO;

	public GestionUsuarios(){

	}

	/**
	 * Metodo que da de alta al usuario pasado como parametro.
	 * Si un usuario con ese dni ya existe en el sistema lanza una excepcion
	 * @throws UsuarioYaExisteException 
	 */
	@Override
	public Usuario altaUsuario(Usuario usuario) throws UsuarioYaExisteException {
		Usuario usuarioAux = usuariosDAO.getUsuarioDni(usuario.getDni());

		if(usuarioAux != null){
			throw new UsuarioYaExisteException();
		}
		return usuariosDAO.addUsuario(usuarioAux);
	}

	// Getters y setters. Necesarios para futuros tests unitarios con mockito para poder
	// asignar un valor a los atributos DAO ya que no disponemos de la inyeccion del
	// EJB.

	public IUsuariosDAO getUsuariosDAO() {
		return usuariosDAO;
	}

	public void setUsuariosDAO(IUsuariosDAO usuariosDAO) {
		this.usuariosDAO = (IUsuariosDAORemote) usuariosDAO;
	}

}
