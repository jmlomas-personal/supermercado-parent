package common.business;

import java.util.List;

import common.domain.Articulo;

public abstract interface IVisualizaArticulos {

	public List<Articulo> verArticulos();
	
	public Articulo verArticulo(String id);
	
}
