package pruebasIT.itTest;

import static org.junit.Assert.assertTrue;

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
}
