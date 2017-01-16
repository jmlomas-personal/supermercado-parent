package business.testBusiness;

import org.junit.BeforeClass;
import org.junit.Test;

import business.business.GestionArticulos;
import common.dao.IArticulosDAO;
import common.domain.Articulo;
import common.utils.ArticuloNotFoundException;
import common.utils.ArticuloYaExisteException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

//import javax.persistence.EntityManager;

public class GestionArticulosTest {

	private static GestionArticulos gestionArticulos;
	private static IArticulosDAO articulosDAO;
	private static Articulo articulo;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		gestionArticulos = new GestionArticulos();
		articulo = mock(Articulo.class);
		articulosDAO = mock(IArticulosDAO.class);
	}

	/**
	 * El articulo no existe, no lanza excepcion y devuelve el articulo
	 */
	@Test
	public void anyadeArticuloTest(){
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
	public void anyadeArticuloYaExisteTest(){
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

		try {
			assertTrue(gestionArticulos.bajaArticulo(articulo) != null);
		} catch (ArticuloNotFoundException e){
			fail();
		}
	}

	/**
	 * El articulo a dar de baja no existe, retorna null
	 */
	@Test
	public void bajaArticuloNoExisteTest(){
		when(articulo.getNombre()).thenReturn("Tomates (1Kg)");
		when(articulo.getPrecio()).thenReturn(2.0);
		when(articulo.getUnidadesStock()).thenReturn(24);

		when(articulosDAO.getArticuloNombre(articulo.getNombre())).thenReturn(null);

		try {
			gestionArticulos.bajaArticulo(articulo);
			fail();
		} catch (ArticuloNotFoundException e){
			assertTrue(true);
		}
	}
	
	@Test
	public void actualizaArticuloTest(){
		when(articulo.getNombre()).thenReturn("Tomates (1Kg)");
		when(articulo.getPrecio()).thenReturn(2.0);
		when(articulo.getUnidadesStock()).thenReturn(24);

		when(articulosDAO.getArticuloNombre(articulo.getNombre())).thenReturn(null);
		
		try {
			gestionArticulos.actualizarStockArticulo(articulo, 126);
		} catch (ArticuloNotFoundException e){
			fail();
		}
	}
}
