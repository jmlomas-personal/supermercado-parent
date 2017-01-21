package es.unican.supermercado.businessLayer.impl.testBusiness;

import org.junit.BeforeClass;
import org.junit.Test;

import es.unican.supermercado.businessLayer.entities.Articulo;
import es.unican.supermercado.businessLayer.impl.GestionArticulos;
import es.unican.supermercado.daoLayer.IArticulosDAORemote;
import es.unican.supermercado.utils.ArticuloNotFoundException;
import es.unican.supermercado.utils.ArticuloYaExisteException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class GestionArticulosTest {

	private static GestionArticulos gestionArticulos;
	private static IArticulosDAORemote articulosDAO;
	private static Articulo articulo, articulo2;
	private static List<Articulo> articulos;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		gestionArticulos = new GestionArticulos();
		articulo = mock(Articulo.class);
		articulo2 = mock(Articulo.class);
		articulosDAO = mock(IArticulosDAORemote.class);
	}

	/**
	 * El articulo no existe, no lanza excepcion y devuelve el articulo
	 */
	@Test
	public void altaArticuloTest(){
		when(articulo.getNombre()).thenReturn("Tomates (1Kg)");
		when(articulo.getPrecio()).thenReturn(2.0);
		when(articulo.getUnidadesStock()).thenReturn(24);

		when(articulosDAO.getArticuloNombre(articulo.getNombre())).thenReturn(null);
		when(articulosDAO.addArticulo(articulo)).thenReturn(articulo);

		gestionArticulos.setArticulosDAO(articulosDAO);

		try {
			assertTrue(gestionArticulos.altaArticulo(articulo) != null);
		} catch (ArticuloYaExisteException e) {
			fail();
		}
	}

	/**
	 * El articulo ya existe, lanza excepcion
	 */
	@Test
	public void altaArticuloYaExisteTest(){
		when(articulo.getNombre()).thenReturn("Tomates (1Kg)");
		when(articulo.getPrecio()).thenReturn(2.0);
		when(articulo.getUnidadesStock()).thenReturn(24);

		when(articulosDAO.getArticuloNombre(articulo.getNombre())).thenReturn(articulo);

		gestionArticulos.setArticulosDAO(articulosDAO);

		try {
			gestionArticulos.altaArticulo(articulo);
			fail();
		} catch (ArticuloYaExisteException e) {
			assertTrue(true);
		}
	}

	/**
	 * El articulo existe, no lanza excepcion y devuelve el articulo dado de baja
	 */
	@Test
	public void bajaArticuloTest(){
		when(articulo.getNombre()).thenReturn("Tomates (1Kg)");
		when(articulo.getPrecio()).thenReturn(2.0);
		when(articulo.getUnidadesStock()).thenReturn(24);

		when(articulosDAO.getArticuloNombre(articulo.getNombre())).thenReturn(articulo);

		gestionArticulos.setArticulosDAO(articulosDAO);

		try {
			assertTrue(gestionArticulos.bajaArticulo(articulo) != null);
		} catch (ArticuloNotFoundException e){
			fail();
		}
	}

	/**
	 * El articulo a dar de baja no existe, lanza excepcion
	 */
	@Test
	public void bajaArticuloNoExisteTest(){
		when(articulo.getNombre()).thenReturn("Tomates (1Kg)");
		when(articulo.getPrecio()).thenReturn(2.0);
		when(articulo.getUnidadesStock()).thenReturn(24);

		when(articulosDAO.getArticuloNombre(articulo.getNombre())).thenReturn(null);

		gestionArticulos.setArticulosDAO(articulosDAO);

		try {
			gestionArticulos.bajaArticulo(articulo);
			fail();
		} catch (ArticuloNotFoundException e){
			assertTrue(true);
		}
	}

	/**
	 * El articulo a actualizar existe, actualiza y retorna
	 */
	@Test
	public void actualizaArticuloTest(){
		when(articulo.getNombre()).thenReturn("Tomates (1Kg)");
		when(articulo.getPrecio()).thenReturn(2.0);
		when(articulo.getUnidadesStock()).thenReturn(24);

		when(articulosDAO.getArticuloNombre(articulo.getNombre())).thenReturn(articulo);

		gestionArticulos.setArticulosDAO(articulosDAO);

		try {
			assert(gestionArticulos.actualizarStockArticulo(articulo, 126) != null);
		} catch (ArticuloNotFoundException e){
			fail();
		}
	}

	/**
	 * El articulo a actualizar no existe, lanza excepcion
	 */
	@Test
	public void actualizaArticuloNoExisteTest(){
		when(articulo.getNombre()).thenReturn("Tomates (1Kg)");
		when(articulo.getPrecio()).thenReturn(2.0);
		when(articulo.getUnidadesStock()).thenReturn(24);

		when(articulosDAO.getArticuloNombre(articulo.getNombre())).thenReturn(null);

		gestionArticulos.setArticulosDAO(articulosDAO);

		try {
			gestionArticulos.actualizarStockArticulo(articulo, 126);
			fail();
		} catch (ArticuloNotFoundException e){
			assertTrue(true);
		}
	}

	/**
	 * Comprueba que se retorna la lista y que la lista contiene los valores
	 * establecidos en el mock
	 */
	@Test
	public void verArticulosTest(){
		when(articulo.getNombre()).thenReturn("Tomates (1Kg)");
		when(articulo.getPrecio()).thenReturn(2.0);
		when(articulo.getUnidadesStock()).thenReturn(24);

		when(articulo2.getNombre()).thenReturn("Patatas (5Kg)");
		when(articulo2.getPrecio()).thenReturn(4.2);
		when(articulo2.getUnidadesStock()).thenReturn(44);

		articulos = new ArrayList<Articulo>();

		articulos.add(articulo);
		articulos.add(articulo2);

		when(articulosDAO.listArticulos()).thenReturn(articulos);

		gestionArticulos.setArticulosDAO(articulosDAO);

		List<Articulo> listaAux = gestionArticulos.verArticulos();
		assertTrue(listaAux.size() == 2);
		assertTrue(listaAux.get(0).getNombre().equals("Tomates (1Kg)"));
		assertTrue(listaAux.get(1).getPrecio() == 4.2);
	}

	/**
	 * Comprueba que se retorna una lista sin articulos
	 */
	@Test
	public void verArticulosSinArticulosTest(){
		articulos = new ArrayList<Articulo>();

		when(articulosDAO.listArticulos()).thenReturn(articulos);

		gestionArticulos.setArticulosDAO(articulosDAO);

		List<Articulo> listaAux = gestionArticulos.verArticulos();
		assertTrue(listaAux.isEmpty());
	}

	@Test
	public void verArticuloTest(){
		when(articulo.getNombre()).thenReturn("Tomates (1Kg)");
		when(articulo.getPrecio()).thenReturn(2.0);
		when(articulo.getUnidadesStock()).thenReturn(24);

		when(articulosDAO.getArticuloNombre(articulo.getNombre())).thenReturn(articulo);

		gestionArticulos.setArticulosDAO(articulosDAO);

		Articulo articuloAux = gestionArticulos.verArticulo(articulo.getNombre());
		assertTrue(articuloAux.getNombre().equals("Tomates (1Kg)"));
		assertTrue(articuloAux.getPrecio() == 2.0);
		assertTrue(articuloAux.getUnidadesStock() == 24);
	}

}
