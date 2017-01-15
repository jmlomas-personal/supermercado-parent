package business;

import domain.Articulo;

public abstract interface IGestionaArticulos {
	
	public void altaArticulo(Articulo articulo);
	
	public Articulo bajaArticulo(Articulo articulo);
	
	public void actualizarStockArticulo(Articulo articulo, int unidades);

}
