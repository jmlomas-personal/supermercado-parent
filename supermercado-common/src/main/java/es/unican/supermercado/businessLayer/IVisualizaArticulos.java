package es.unican.supermercado.businessLayer;

import java.util.List;

import javax.ejb.Remote;

import es.unican.supermercado.businessLayer.entities.Articulo;

@Remote
public abstract interface IVisualizaArticulos {

	public List<Articulo> verArticulos();
	
	public Articulo verArticulo(String id);
	
}
