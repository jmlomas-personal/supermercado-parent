package es.unican.supermercado.businessLayer;

import es.unican.supermercado.businessLayer.entities.Usuario;
import es.unican.supermercado.utils.UsuarioNoExisteException;
import es.unican.supermercado.utils.UsuarioYaExisteException;

/**
 * Interfaz proporcionada por GestionUsuarios
 * @author MacbookAir
 *
 */
public abstract interface IRegistroUsuarios {

	public Usuario altaUsuario(Usuario usuario) throws UsuarioYaExisteException;
	
	public Usuario dameUsuario(String dni) throws UsuarioNoExisteException;
	
}
