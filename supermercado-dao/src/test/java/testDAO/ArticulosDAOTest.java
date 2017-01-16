package testDAO;

import javax.persistence.EntityManager;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;

import java.util.List;

import dao.ArticulosDAO;
import dao.IArticulosDAO;
import domain.Articulo;

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
