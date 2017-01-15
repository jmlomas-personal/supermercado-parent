package business;

import java.util.List;

import javax.ejb.EJB;

import dao.IArticulosDAO;
import domain.Articulo;

public class GestionaArticulos implements IGestionaArticulos {

	private List<Articulo> articulos;
	@EJB
	private IArticulosDAO articulosDAO;

	/**
	 * Constructor de la clase
	 */
	public GestionaArticulos(){
		
	}

	/**
	 * Metodo que da de alta un articulo
	 */
	public void altaArticulo(Articulo articulo) {
		this.articulos.add(articulo);
	}

	/**
	 * Metodo que da de baja un articulo
	 * @return El articulo borrado. Null si no existia el articulo
	 */
	public Articulo bajaArticulo(Articulo articulo) {
		Articulo auxArticulo = buscaArticulo(articulo);
		if (auxArticulo != null){
			articulos.remove(articulo);
		}
		return auxArticulo;
	}

	/**
	 * Metodo que actualiza el stock de un articulo
	 */
	public void actualizarStockArticulo(Articulo articulo, int unidades) {
		int aux = 0;
		Articulo articuloAux = null;

		while(aux < articulos.size() && articuloAux == null){	
			if(articulo.getId() == articulos.get(aux).getId()){
				articuloAux = articulos.get(aux);
				articuloAux.setUnidadesStock(unidades);
			}
			aux++;
		}
	}

	/**
	 * Metodo privado que busca un articulo
	 * @param articulo articulo a buscar
	 * @return El articulo buscado o Null si no se encontro el articulo
	 */
	private Articulo buscaArticulo(Articulo articulo){
		int aux = 0;
		Articulo articuloAux = null;

		while(aux < articulos.size() && articuloAux == null){	
			if(articulo.getId() == articulos.get(aux).getId()){
				articuloAux = articulos.get(aux);
			}
			aux++;
		}
		return articuloAux;
	}
}
