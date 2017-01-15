package dao;

import java.util.List;

import domain.Articulo;

public interface IArticulosDAO {

	public Articulo addArticulo(Articulo articulo);
	
	public void deleteArticulo(Articulo articulo);
	
	public Articulo updateArticulo(Articulo articulo);
	
	public Articulo getArticulo(String id);
	
	public List<Articulo> listArticulos();
	
}
