package business.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import common.business.IGestionaArticulos;
import common.business.IVisualizaArticulos;
import common.dao.IArticulosDAO;
import common.domain.Articulo;

@Stateless
public class GestionArticulos implements IGestionaArticulos, IVisualizaArticulos {


	@EJB
	private IArticulosDAO articulosDAO;

	/**
	 * Constructor de la clase
	 */
	public GestionArticulos(){

	}

	/**
	 * Metodo que da de alta un articulo siempre y cuando no exista en la base de datos.
	 * @return el articulo anyadido o  null si no se pudo anyadir pq ya existia 
	 */
	public Articulo altaArticulo(Articulo articulo) {
		Articulo auxArt = articulosDAO.getArticuloNombre(articulo.getNombre());
		if(auxArt != null){
			return articulosDAO.addArticulo(articulo);
		}
		return null;
	}
	/**
	 * Metodo que da de baja un articulo
	 * @return El articulo borrado. Null si no existia el articulo
	 */
	public Articulo bajaArticulo(Articulo articulo) {
		Articulo auxArt = articulosDAO.getArticulo(articulo.getId());
		if(auxArt != null){
			articulosDAO.deleteArticulo(articulo);
		}
		return auxArt;
	}

	/**
	 * Metodo que actualiza el stock de un articulo
	 * @return El objeto actualizado o Null si el objeto a actualizar
	 * no existe
	 */
	public Articulo actualizarStockArticulo(Articulo articulo, int unidades) {
		Articulo auxArt = articulosDAO.getArticulo(articulo.getId());
		if(auxArt != null){
			articulosDAO.updateArticulo(articulo);
		}
		return auxArt;
	}

	/**
	 * Metodo que retorna la lista de articulos completa
	 * @return Lista de articulos
	 */
	public List<Articulo> verArticulos() {
		return articulosDAO.listArticulos();
	}

	/**
	 * Metodo que retorna el articulo con el id pasado como parametro
	 * @return Articulo con el id indicado o Null si no existe un articulo
	 * con dicho id
	 */
	public Articulo verArticulo(String id) {
		return articulosDAO.getArticulo(id);
	}

}