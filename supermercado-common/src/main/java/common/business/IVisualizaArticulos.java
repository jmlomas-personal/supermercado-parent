package common.business;

import java.util.List;

import javax.ejb.Remote;

import common.domain.Articulo;

@Remote
public abstract interface IVisualizaArticulos {

	public List<Articulo> verArticulos();
	
	public Articulo verArticulo(String id);
	
}
