package business;

import java.util.List;
import domain.Articulo;

public abstract interface IVisualizaArticulos {

	public List<Articulo> verArticulos();
	
	public Articulo verArticulo();
	
}
