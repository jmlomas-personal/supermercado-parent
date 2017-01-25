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

@Named
@SessionScoped
public class UsuarioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	private String dni;

	// Atributos de JSF
	private FacesContext context;
	private ResourceBundle bundle;
	FacesMessage msg;
	
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
			msg = new FacesMessage(bundle.getString("login_input_error"), "ERROR MSG");
	        msg.setSeverity(FacesMessage.SEVERITY_INFO);
	        context = FacesContext.getCurrentInstance();
	        context.addMessage(null, msg);
	        
	        return null;
		}
		
	}	
	
	public String logout(){
		msg = new FacesMessage(bundle.getString("logout_message"), "INFO MSG");		
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        context = FacesContext.getCurrentInstance();
        context.addMessage(null, msg);
        
        return "login.xhtml";
	}
	
	public String altaUsuario() {
		
		try{
			usuario = registroUsuarios.altaUsuario(usuario);
			
			// Pasamos a la siguiente pantalla
			return "login.xhtml";
			
		}catch(UsuarioYaExisteException e){
			msg = new FacesMessage(bundle.getString("register_input_error"), "ERROR MSG");
	        msg.setSeverity(FacesMessage.SEVERITY_INFO);
	        context = FacesContext.getCurrentInstance();
	        context.addMessage(null, msg);
	        
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

}