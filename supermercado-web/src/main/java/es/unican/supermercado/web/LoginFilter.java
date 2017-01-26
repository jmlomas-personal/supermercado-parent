package es.unican.supermercado.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filtro para controlar el acceso para la gestion de usuarios propia de la aplicacion
 * Con esto conseguimos que no se acceda a ninguna pagina bajo la ruta especificada en la
 * configuracion (web.xml), sin estar logueado previamente
 */
public class LoginFilter implements Filter {

	// Necesitamos acceder al CDI bean, con un Inject nos vale
	@Inject
	private UsuarioBean usuarioBean;
	
	/**
	 * Metodo que comprueba se el usuario que accede a la pagina esta logueado o no.
	 * Si no esta logueado, lo mandamos a la pagina de login
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if (usuarioBean == null || !usuarioBean.isUserLogged()) {
			String contextPath = ((HttpServletRequest)request).getContextPath();
			((HttpServletResponse)response).sendRedirect(contextPath + "/login.xhtml");
		}	
		
		chain.doFilter(request, response);

	}

	// Metodos propios de la clase Filter. No nos interesa tocarlos
	
	public void init(FilterConfig config) throws ServletException {
		// Nothing to do here!
	}

	public void destroy() {
		// Nothing to do here!
	}	
	
}