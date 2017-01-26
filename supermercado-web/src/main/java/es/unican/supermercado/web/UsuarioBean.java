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
	private boolean isUserLogged;

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
			isUserLogged = true;
			// Pasamos a la siguiente pantalla
			return "/app/listaArticulos.xhtml";
			
		}catch(UsuarioNoExisteException e){		
			msg = new FacesMessage(bundle.getString("login_input_error"));
	        msg.setSeverity(FacesMessage.SEVERITY_WARN);
	        context = FacesContext.getCurrentInstance();
	        context.addMessage(null, msg);
	        
	        isUserLogged = false;
	        
	        return null;
		}
		
	}	
	
	public String logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		msg = new FacesMessage(bundle.getString("logout_message"));		
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        context = FacesContext.getCurrentInstance();
        context.addMessage(null, msg);               
        
        usuario = new Usuario();
        dni = "";
        isUserLogged = false;
        
        return "/login.xhtml";
	}
	
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