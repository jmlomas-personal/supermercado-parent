package es.unican.supermercado.businessLayer;

import javax.ejb.Remote;

import es.unican.supermercado.businessLayer.entities.Articulo;
import es.unican.supermercado.utils.ArticuloNotFoundException;
import es.unican.supermercado.utils.ArticuloYaExisteException;

@Remote
public abstract interface IGestionaArticulos {
	
	public Articulo altaArticulo(Articulo articulo) throws ArticuloYaExisteException;
	
	public Articulo bajaArticulo(Articulo articulo) throws ArticuloNotFoundException;
	
	public Articulo actualizarStockArticulo(Articulo articulo, int unidades) throws ArticuloNotFoundException;

}
