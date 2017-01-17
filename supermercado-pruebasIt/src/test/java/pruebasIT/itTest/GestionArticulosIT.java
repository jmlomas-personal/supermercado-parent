package pruebasIT.itTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;

import org.junit.BeforeClass;
import org.junit.Test;

import business.business.GestionArticulos;
import common.dao.IArticulosDAO;
import common.domain.Articulo;
import common.utils.ArticuloNotFoundException;
import common.utils.ArticuloYaExisteException;

public class GestionArticulosIT {


	private static GestionArticulos gestionArticulos;
	private static IArticulosDAO articulosDAO;
	private static Articulo articulo;
	private static List<Articulo> articulos;
	private static EJBContainer container;

	@BeforeClass
	public static void initContainer() throws Exception {
		Map properties = new HashMap();
		properties.put(EJBContainer.MODULES, new File[]{new File("classes")});

		properties.put("org.glassfish.ejb.embedded.glassfish.installation.root",
				"/Users/MacbookAir/Documents/glassfish4/glassfish");
		container = EJBContainer.createEJBContainer(properties);

		articulosDAO = (IArticulosDAO) container.getContext().lookup("java:global"
				+ "/supermercado-dao-0.0.1-SNAPSHOT/ArticulosDAO");

		//TODO necesario hacer el set manualmente??
		gestionArticulos.setArticulosDAO(articulosDAO);

		//TODO Necesitamos cambiar esa property para poder supongo que conectar con glassfish
		// y que con lo del contenedor embebido nos inyecte el modulo dao para que gestionArticulos
		// funcione para las pruebas.
	}

	/**
	 * En la base de datos esta el articulo Patatas 5kg por lo que deberia obtenerlo
	 */
	@Test
	public void verArticuloTest(){
		articulo = gestionArticulos.verArticulo("Patatas (5Kg)");

		assertTrue(articulo.getNombre().equals("Patatas (5Kg)"));
		assertTrue(articulo.getPrecio() == 3.0);
	}

	/**
	 * El articulo no esta en la bd. Deberia devolver null
	 */
	@Test
	public void verArticuloNullTest(){
		articulo = gestionArticulos.verArticulo("NOEXISTE");

		assertTrue(articulo == null);
	}

	/**
	 * Comprueba que la lista que devuelve no esta vacia y que contiene
	 * al menos un articulo conocido (Patatas 5Kg)
	 */
	@Test
	public void verArticulosTest(){
		articulos = gestionArticulos.verArticulos();
		Articulo artAux = null;

		for (Articulo a : articulos){
			if(a.getNombre().equals("Patatas (5Kg)")){
				artAux = a;
			}
		}

		assertTrue(articulos != null);
		assertTrue(artAux.getNombre().equals("Patatas (5Kg)"));
		assertTrue(artAux.getPrecio() == 3.0);
	}

	/**
	 * Se crea un articulo y se inserta. Se comprueba que no retorna null
	 */
	@Test
	public void altaArticuloTest() throws ArticuloYaExisteException, ArticuloNotFoundException{
		articulo = new Articulo();
		articulo.setNombre("ArticuloPrueba1");
		articulo.setPrecio(999.9);
		articulo.setUnidadesStock(999);

		gestionArticulos.altaArticulo(articulo);

		try {
			assertTrue(gestionArticulos.altaArticulo(articulo) != null);
			gestionArticulos.bajaArticulo(articulo);
		} catch (ArticuloYaExisteException e) {
			fail();
		} catch (ArticuloNotFoundException e){
			fail();
		}
	}

	/**
	 * Se inserta el articulo 2 veces para que ya exista y se lanza la excepcion
	 * @throws ArticuloNotFoundException 
	 */
	@Test
	public void altaArticuloYaExisteTest() throws ArticuloNotFoundException{
		articulo = new Articulo();
		articulo.setNombre("ArticuloPrueba1");
		articulo.setPrecio(999.9);
		articulo.setUnidadesStock(999);

		try {
			gestionArticulos.altaArticulo(articulo);
			gestionArticulos.altaArticulo(articulo);
			fail();
		} catch (ArticuloYaExisteException e) {
			assertTrue(true);
			gestionArticulos.bajaArticulo(articulo);
		}
	}

	/**
	 * Da de alta un articulo y luego lo da de baja. Deberia devolver el articulo
	 * al borrarlo por lo que comprueba que lo que retorna sea distinto de null
	 */
	@Test
	public void bajaArticuloTest() throws ArticuloYaExisteException, ArticuloNotFoundException{
		articulo = new Articulo();
		articulo.setNombre("ArticuloPrueba1");
		articulo.setPrecio(999.9);
		articulo.setUnidadesStock(999);

		try{
			gestionArticulos.altaArticulo(articulo);
			assertTrue(gestionArticulos.bajaArticulo(articulo) != null);
		} catch (ArticuloYaExisteException e) {
			fail();
		} catch (ArticuloNotFoundException e){
			fail();
		}
	}

	/**
	 * Da de baja un articulo que no existe por lo que lanza excepcion
	 */
	@Test
	public void bajaArticuloNoExisteTest() throws ArticuloNotFoundException{
		articulo = new Articulo();
		articulo.setNombre("ArticuloPrueba1");
		articulo.setPrecio(999.9);
		articulo.setUnidadesStock(999);

		try{
			gestionArticulos.bajaArticulo(articulo);
			fail();
		} catch(ArticuloNotFoundException e){
			assertTrue(true);
		}
	}

	/**
	 * Crea el articulo, lo inserta, lo actualiza y comprueba que se ha actualizado.
	 */
	@Test
	public void actualizaArticuloTest() throws ArticuloNotFoundException, ArticuloYaExisteException{
		articulo = new Articulo();
		articulo.setNombre("ArticuloPrueba1");
		articulo.setPrecio(999.9);
		articulo.setUnidadesStock(100);

		try{
			gestionArticulos.altaArticulo(articulo);
			assertTrue(gestionArticulos.actualizarStockArticulo(articulo, 999).getUnidadesStock() == 999);
			gestionArticulos.bajaArticulo(articulo);
		} catch(ArticuloYaExisteException e){
			fail();
		} catch(ArticuloNotFoundException e){
			fail();
		}
	}

	/**
	 * Actualiza un articulo que no existe en la base de datos.
	 * Comprueba que se lanza excepcion
	 */
	@Test
	public void actualizaArticuloNoExisteTest(){
		articulo = new Articulo();
		articulo.setNombre("ArticuloPrueba1");
		articulo.setPrecio(999.9);
		articulo.setUnidadesStock(100);

		try{
			gestionArticulos.actualizarStockArticulo(articulo, 999);
			fail();
		} catch(ArticuloNotFoundException e){
			assertTrue(true);
		}
	}
}