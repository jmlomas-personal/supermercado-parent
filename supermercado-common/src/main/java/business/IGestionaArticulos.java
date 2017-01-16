package business;

import domain.Articulo;

public abstract interface IGestionaArticulos {
	
	public Articulo altaArticulo(Articulo articulo);
	
	public Articulo bajaArticulo(Articulo articulo);
	
	public Articulo actualizarStockArticulo(Articulo articulo, int unidades);

}
