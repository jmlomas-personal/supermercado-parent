package es.unican.supermercado.web;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import es.unican.supermercado.businessLayer.IRegistroUsuariosRemote;
import es.unican.supermercado.businessLayer.entities.Usuario;
import es.unican.supermercado.utils.UsuarioNoExisteException;
import es.unican.supermercado.utils.UsuarioYaExisteException;

@Named
@RequestScoped
public class UsuarioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	private String dni, error;

	// Atributos de JSF
	private FacesContext context;
	private ResourceBundle bundle;
	
	// Si lo hiciesemos con un EJB de la capa de negocio
	@EJB
	private IRegistroUsuariosRemote registroUsuarios;
	
	public UsuarioBean() {
		context = FacesContext.getCurrentInstance();
		bundle = context.getApplication().getResourceBundle(context, "msg");
	}	
	
	public String login() {
				
		try{					
			usuario = registroUsuarios.dameUsuario(dni);			
					
			// Pasamos a la siguiente pantalla
			return "listaArticulos.xhtml";
			
		}catch(UsuarioNoExisteException e){			
			error = bundle.getString("login_input_error");
	        return null;
		}
		
	}	
	
	public String altaUsuario() {
		
		try{
			usuario = registroUsuarios.altaUsuario(usuario);
			
			// Pasamos a la siguiente pantalla
			return "login.xhtml";
			
		}catch(UsuarioYaExisteException e){
			error = bundle.getString("register_input_error");     
	        return null;
		}
		
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}