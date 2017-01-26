package es.unican.supermercado.daoLayer.impl.testDAO;

import javax.persistence.EntityManager;

import org.junit.BeforeClass;
import org.junit.Test;

import es.unican.supermercado.businessLayer.entities.Articulo;
import es.unican.supermercado.daoLayer.IArticulosDAO;
import es.unican.supermercado.daoLayer.impl.ArticulosDAO;

import static org.mockito.Mockito.*;

/**
 * Clase de prueba de la clase DAO de Articulos
 * @author MacbookAir
 *
 */
public class ArticulosDAOTest {

	private static IArticulosDAO articulosDAO;
	private static Articulo articulo;
	private static EntityManager em;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		articulosDAO = new ArticulosDAO();
		em = mock(EntityManager.class);
		((ArticulosDAO)articulosDAO).setEm(em);
	}

	@Test
	public void addArticuloTest(){
		articulo = new Articulo();
		articulosDAO.addArticulo(articulo);
		verify(em).persist(articulo);
	}
}
