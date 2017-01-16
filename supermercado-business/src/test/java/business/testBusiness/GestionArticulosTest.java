package business.testBusiness;

import org.junit.BeforeClass;
import org.junit.Test;

import business.business.GestionArticulos;
import common.dao.IArticulosDAO;
import common.domain.Articulo;

import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;

public class GestionArticulosTest {

	private static GestionArticulos gestionArticulos;
	private static IArticulosDAO articulosDAO;
	private static Articulo articulo;
	private static EntityManager em;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		gestionArticulos = mock(GestionArticulos.class);
		articulosDAO = mock(IArticulosDAO.class);
		em = mock(EntityManager.class);
		//((IArticulosDAO)articulosDAO).setEm(em);
	}

	@Test
	public void anyadeArticuloTest(){
		
	}

}
