package es.unican.supermercado.businessLayer.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import es.unican.supermercado.businessLayer.IGestionaArticulosLocal;
import es.unican.supermercado.businessLayer.IGestionaArticulosRemote;
import es.unican.supermercado.businessLayer.IVisualizaArticulosLocal;
import es.unican.supermercado.businessLayer.IVisualizaArticulosRemote;
import es.unican.supermercado.businessLayer.entities.Articulo;
import es.unican.supermercado.daoLayer.IArticulosDAO;
import es.unican.supermercado.daoLayer.IArticulosDAORemote;
import es.unican.supermercado.utils.ArticuloNotFoundException;
import es.unican.supermercado.utils.ArticuloYaExisteException;

@Stateless
public class GestionArticulos implements IGestionaArticulosRemote, IGestionaArticulosLocal, IVisualizaArticulosRemote, IVisualizaArticulosLocal {

	@EJB
	private IArticulosDAORemote articulosDAO;

	/**
	 * Constructor de la clase
	 */
	public GestionArticulos(){

	}

	/**
	 * Metodo que da de alta un articulo siempre y cuando no exista en la base de datos.
	 * @return el articulo anyadido  
	 * @throws ArticuloYaExisteException lanzada si ya existe un articulo con dicho
	 * nombre
	 */
	@Override
	public Articulo altaArticulo(Articulo articulo) throws ArticuloYaExisteException {
		Articulo auxArt = articulosDAO.getArticuloNombre(articulo.getNombre());
		
		if(auxArt != null){
			throw new ArticuloYaExisteException();
		} else {
			articulosDAO.addArticulo(articulo);
		}
		
		return articulo;
	}
	/**
	 * Metodo que da de baja un articulo
	 * @return El articulo borrado. Null si no existia el articulo
	 * @throws ArticuloNotFoundException lanzada si no existe un articulo con dicho
	 * nombre
	 */
	@Override
	public Articulo bajaArticulo(Articulo articulo) throws ArticuloNotFoundException {
		Articulo auxArt = articulosDAO.getArticuloNombre(articulo.getNombre());
		if(auxArt == null){
			throw new ArticuloNotFoundException();
		} else {
			articulosDAO.deleteArticulo(auxArt);
		}
		return auxArt;
	}

	/**
	 * Metodo que actualiza el stock de un articulo
	 * @return El objeto actualizado o Null si el objeto a actualizar
	 * no existe
	 * @throws ArticuloNotFoundException 
	 */
	@Override
	public Articulo actualizarStockArticulo(Articulo articulo, int unidades) throws ArticuloNotFoundException {
		Articulo auxArt = articulosDAO.getArticuloNombre(articulo.getNombre());
		if(auxArt == null){
			throw new ArticuloNotFoundException();
		} else {
			auxArt.setUnidadesStock(unidades);
			articulosDAO.updateArticulo(auxArt);
		}
		return auxArt;
	}
	
	/**
	 * Metodo que retorna la lista de articulos completa
	 * @return Lista de articulos
	 */
	@Override
	public List<Articulo> verArticulos() {
		return articulosDAO.listArticulos();
	}

	/**
	 * Metodo que retorna el articulo con el id pasado como parametro
	 * @return Articulo con el id indicado o Null si no existe un articulo
	 * con dicho id
	 */
	@Override
	public Articulo verArticulo(String nombre) {
		return articulosDAO.getArticuloNombre(nombre);
	}

	// Getters y setters. Necesarios para futuros tests unitarios con mockito para poder
	// asignar un valor a los atributos DAO ya que no disponemos de la inyeccion del
	// EJB.

	public IArticulosDAO getArticulosDAO() {
		return articulosDAO;
	}

	public void setArticulosDAO(IArticulosDAO articulosDAO) {
		this.articulosDAO = (IArticulosDAORemote) articulosDAO;
	}

}
