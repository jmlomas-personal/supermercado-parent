package common.dao;

import java.util.List;

import javax.ejb.Local;

import common.domain.Articulo;

@Local
public interface IArticulosDAO {

	public Articulo addArticulo(Articulo articulo);
	
	public void deleteArticulo(Articulo articulo);
	
	public Articulo updateArticulo(Articulo articulo);
	
	public Articulo getArticulo(String id);
	
	public Articulo getArticuloNombre(String nombre);
	
	public List<Articulo> listArticulos();
	
}
