package es.unican.supermercado.daoLayer;

import java.util.List;

import es.unican.supermercado.businessLayer.entities.Articulo;

/**
 * Interfaz proporcionada por el DAO de Articulos
 * @author MacbookAir
 *
 */
public interface IArticulosDAO {

	public Articulo addArticulo(Articulo articulo);
	
	public void deleteArticulo(Articulo articulo);
	
	public Articulo updateArticulo(Articulo articulo);
	
	public Articulo getArticulo(long id);
	
	public Articulo getArticuloNombre(String nombre);
	
	public List<Articulo> listArticulos();
	
}
