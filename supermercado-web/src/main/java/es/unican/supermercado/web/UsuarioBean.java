package es.unican.supermercado.web;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import es.unican.supermercado.businessLayer.IRegistroUsuariosRemote;
import es.unican.supermercado.businessLayer.entities.Usuario;
import es.unican.supermercado.utils.UsuarioNoExisteException;
import es.unican.supermercado.utils.UsuarioYaExisteException;

/**
 * CDI Bean para el mantenimiento
 * de la sesion del usuario, asi como
 * para desconectarse de la
 * aplicacion.
 *  
 * @author Juan Manuel Lomas
 *
 */
@Named
@SessionScoped
public class UsuarioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Atributos del Bean que estaran disponibles durante la vida de este
	private Usuario usuario = new Usuario();
	private String dni;
	private boolean isUserLogged;

	// Atributos de JSF
	private FacesContext context;
	private ResourceBundle bundle;
	FacesMessage msg;
	
	// Lo utilizaremos para hacer comporbaciones (Stateless)
	@EJB
	private IRegistroUsuariosRemote registroUsuarios;
	
	/**
	 * Constructor de la clase
	 */
	public UsuarioBean() {
		context = FacesContext.getCurrentInstance();
		bundle = context.getApplication().getResourceBundle(context, "msg");
	}	
	
	/**
	 * Metodo con el que se identifica el usuario
	 * 
	 * @return la siguiente pagina en cado de login satisfactorio, 
	 * la misma en cualquier otro caso
	 */
	public String login() {
				
		try{					
			usuario = registroUsuarios.dameUsuario(dni);
			isUserLogged = true;
			// Pasamos a la siguiente pantalla
			return "/app/listaArticulos.xhtml?faces-redirect=true";
			
		}catch(UsuarioNoExisteException e){		
			msg = new FacesMessage(bundle.getString("login_input_error"));
	        msg.setSeverity(FacesMessage.SEVERITY_WARN);
	        context = FacesContext.getCurrentInstance();
	        context.addMessage(null, msg);
	        
	        isUserLogged = false;
	        
	        return null;
		}
		
	}	
	
	/**
	 * Metodo que desconecta al usuario
	 * 
	 * @return la pagina de login principal para volver a identificarse
	 */
	public String logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		msg = new FacesMessage(bundle.getString("logout_message"));		
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        context = FacesContext.getCurrentInstance();
        context.addMessage(null, msg);               
        
        usuario = new Usuario();
        dni = "";
        isUserLogged = false;
        
        return "/login.xhtml?faces-redirect=true";
	}
	
	/**
	 * Metodo para dar de alta un nuevo usuario en el sistema
	 * 
	 * @return la pagina de login en caso de registrarse correctamente,
	 * la misma en cualquier otro caso
	 */
	public String altaUsuario() {
		
		try{
			registroUsuarios.altaUsuario(usuario);
			
			msg = new FacesMessage(bundle.getString("register_success"));
	        msg.setSeverity(FacesMessage.SEVERITY_INFO);
	        context = FacesContext.getCurrentInstance();
	        context.addMessage(null, msg);
			
	        usuario = new Usuario();
	        isUserLogged = false;
	        
			// Pasamos a la siguiente pantalla
			return "/login.xhtml";
			
		}catch(UsuarioYaExisteException e){
			msg = new FacesMessage(bundle.getString("register_input_error"));
	        msg.setSeverity(FacesMessage.SEVERITY_WARN);
	        context = FacesContext.getCurrentInstance();
	        context.addMessage(null, msg);
	        
	        return null;
		}
		
	}
	
	// Getters y Setters
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

	public boolean isUserLogged() {
		return isUserLogged;
	}

	public void setUserLogged(boolean isUserLogged) {
		this.isUserLogged = isUserLogged;
	}
}