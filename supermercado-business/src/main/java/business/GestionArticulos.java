package business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.IArticulosDAO;
import domain.Articulo;

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
	 * Metodo que da de alta un articulo
	 */
	public void altaArticulo(Articulo articulo) {
		articulosDAO.addArticulo(articulo);
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
