package es.unican.supermercado.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import es.unican.supermercado.businessLayer.IGestionaArticulosRemote;
import es.unican.supermercado.businessLayer.IVisualizaArticulosRemote;
import es.unican.supermercado.businessLayer.entities.Articulo;
import es.unican.supermercado.utils.ArticuloYaExisteException;

@Named
@RequestScoped
public class ArticuloBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Articulo articulo = new Articulo();
	private List<Articulo> listaArticulos = new ArrayList<Articulo>();
	
	// Si lo hiciesemos con un EJB de la capa de negocio
	@EJB
	private IGestionaArticulosRemote gestionaArticulo;
	
	@EJB
	private IVisualizaArticulosRemote visualizaArticulo;
	
	public ArticuloBean() {				
	}
	
	@PostConstruct
	public void getArticulos() {
		listaArticulos = visualizaArticulo.verArticulos();
	}

	public String altaArticulo() {
		
		FacesContext context = FacesContext.getCurrentInstance();  
		
		try{
			
			articulo = gestionaArticulo.altaArticulo(articulo);
			listaArticulos = visualizaArticulo.verArticulos();
			return "listaArticulos.xhtml";
			
		}catch(ArticuloYaExisteException e){
			context.addMessage(null, new FacesMessage("Error, el articulo ya existe"));	        
	        return null;
		}
		
	}
	
	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public List<Articulo> getListaArticulos() {
		return listaArticulos;
	}

	public void setListaArticulos(List<Articulo> listaArticulos) {
		this.listaArticulos = listaArticulos;
	}

}