package web;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import business.business.GestionArticulos;
import common.domain.Articulo;
import common.utils.ArticuloYaExisteException;


@Named(value = "articuloBean")
@RequestScoped
public class ArticuloBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Articulo articulo = new Articulo();
	private List<Articulo> articulos;
	
	// Si lo hiciesemos con un EJB de la capa de negocio
	@EJB
	private GestionArticulos articuloEJB;
	
	public ArticuloBean() {		
	}

	public String altaArticulo() {
		
		 FacesContext context = FacesContext.getCurrentInstance();  
		
		try{
			
			articulo = articuloEJB.altaArticulo(articulo);
			articulos = articuloEJB.verArticulos();
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

	public List<Articulo> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}

}