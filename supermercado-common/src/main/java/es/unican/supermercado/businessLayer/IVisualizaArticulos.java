package es.unican.supermercado.businessLayer;

import java.util.List;

import es.unican.supermercado.businessLayer.entities.Articulo;

/**
 * Interfaz proporcionada por GestionArticulos
 * @author MacbookAir
 *
 */
public abstract interface IVisualizaArticulos {

	public List<Articulo> verArticulos();
	
	public Articulo verArticulo(String id);
	
}
