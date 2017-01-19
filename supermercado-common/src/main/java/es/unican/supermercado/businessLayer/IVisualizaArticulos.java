package es.unican.supermercado.businessLayer;

import java.util.List;

import es.unican.supermercado.businessLayer.entities.Articulo;

public abstract interface IVisualizaArticulos {

	public List<Articulo> verArticulos();
	
	public Articulo verArticulo(String id);
	
}
