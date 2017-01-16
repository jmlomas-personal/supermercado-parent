package common.business;

import common.domain.Articulo;
import common.utils.ArticuloNotFoundException;
import common.utils.ArticuloYaExisteException;

public abstract interface IGestionaArticulos {
	
	public Articulo altaArticulo(Articulo articulo) throws ArticuloYaExisteException;
	
	public Articulo bajaArticulo(Articulo articulo) throws ArticuloNotFoundException;
	
	public Articulo actualizarStockArticulo(Articulo articulo, int unidades) throws ArticuloNotFoundException;

}
