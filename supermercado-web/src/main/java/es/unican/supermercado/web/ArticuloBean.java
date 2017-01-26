package es.unican.supermercado.web;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import es.unican.supermercado.businessLayer.IGestionaArticulosRemote;
import es.unican.supermercado.businessLayer.IRealizaPedidosRemote;
import es.unican.supermercado.businessLayer.IVisualizaArticulosRemote;
import es.unican.supermercado.businessLayer.entities.Articulo;
import es.unican.supermercado.businessLayer.entities.LineaPedido;
import es.unican.supermercado.businessLayer.entities.Pedido;
import es.unican.supermercado.utils.ArticuloNotFoundException;
import es.unican.supermercado.utils.ArticuloYaExisteException;
import es.unican.supermercado.utils.StockInsuficienteException;
import es.unican.supermercado.utils.UsuarioNoExisteException;

/**
 * CDI Bean para consultar articulos y ejecutar
 * consultas sobre el EJB que corresponda.
 * 
 * @author Juan Manuel Lomas
 *
 */
@Named
@RequestScoped
public class ArticuloBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// Atributos
	private Articulo articulo = new Articulo();
	private List<Articulo> listaArticulos = new ArrayList<Articulo>();

	// Forma de mappear en JSF las datatables
	private HtmlDataTable datatableArticulos;	
	private HtmlDataTable datatableLineasPedido;	

	// Atributos de JSF
	private FacesContext context;
	private ResourceBundle bundle;
	private FacesMessage msg;

	// SimpleDateFormat para la fecha de recogida
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Inject
	UsuarioBean usuarioBean;

	@Inject
	CarritoBean carritoBean;

	// Si lo hiciesemos con un EJB de la capa de negocio
	@EJB
	private IGestionaArticulosRemote gestionaArticulo;

	@EJB
	private IVisualizaArticulosRemote visualizaArticulo;

	@EJB
	private IRealizaPedidosRemote gestionaPedido;

	/**
	 * Constructor de la clase
	 */
	public ArticuloBean() {
		context = FacesContext.getCurrentInstance();
		bundle = context.getApplication().getResourceBundle(context, "msg");
	}

	/**
	 * Metodo para obtener los articulos. Al tener un scope de peticion
	 * recargara con cada redireccion.
	 */
	@PostConstruct
	public void getArticulos() {
		listaArticulos = visualizaArticulo.verArticulos();
	}

	/**
	 * Metodo para dar de alta a un articulo.
	 * En nuestro caso hemos reutilizado el bean para hacer esta llamada, aunque
	 * podia ser de la parte del backend gestionada mediante credenciales en el servidor
	 * (Podiamos tener otro bean para no mezclar conceptos)
	 * 
	 * @return la misma pagina siempre en este caso
	 */
	public String altaArticulo() {

		try{

			articulo = gestionaArticulo.altaArticulo(articulo);
			listaArticulos = visualizaArticulo.verArticulos();						
			msg = new FacesMessage("Se ha dado correctamente de alta el articulo: " + articulo.getNombre());
			msg.setSeverity(FacesMessage.SEVERITY_WARN);
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, msg);
			
			articulo = new Articulo();
			
			return "/app/administration.xhtml";

		}catch(ArticuloYaExisteException e){
			msg = new FacesMessage(bundle.getString("new_article_error"));
			msg.setSeverity(FacesMessage.SEVERITY_WARN);
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, msg);

			return null;
		}

	}

	/**
	 * Metodo que crea un nuevo pedido con sus lineas asociadas
	 * Al utilizar un EJB Stateful, conseguimos que solo en caso de
	 * que todo vaya bien, se ejecute el confirmar pedido, y con el 
	 * el persist.
	 * 
	 * @return el listado principal de articulo si ha ido bien, 
	 * la misma pagina en caso contrario
	 */
	public String altaPedido(){

		List<LineaPedido> lineas = carritoBean.getLineasPedido();
		Pedido p;
		boolean bOk = true;
		String errorMsg = "";

		context = FacesContext.getCurrentInstance();

		if(lineas.isEmpty()){			
			errorMsg = bundle.getString("cart_empty_error");

			bOk = false;
		}else{

			try {
				gestionaPedido.crearPedido(usuarioBean.getUsuario().getDni());

				for(LineaPedido linea : lineas){

					try {
						gestionaPedido.anyadeLineaPedido(linea);
					} catch (StockInsuficienteException e) {	
						errorMsg += "\n Stock insuficiente " + linea.getArticulo().getNombre();

						bOk = false;
					} catch (ArticuloNotFoundException e) {
						errorMsg += "\n El articulo ya no esta disponible " + linea.getArticulo().getNombre();

						bOk = false;
					}
				}

				if(bOk){
					p = gestionaPedido.confirmarPedido(new Date(carritoBean.getFechaRecogida().getTime()));																			
					msg = new FacesMessage(bundle.getString("cart_process_ok") + " Referencia de pedido: " + p.getId() + ", con fecha de recogida: " + dateFormat.format(carritoBean.getFechaRecogida()));
					
					carritoBean.empty();
				}

			} catch (UsuarioNoExisteException e) {		
				errorMsg += "\n El usuario que intenta realizar el pedido no se eoncontro en el sistema " + usuarioBean.getUsuario().getDni();

				bOk = false;
			}	        				      
		}	

		if(!bOk){
			msg = new FacesMessage(errorMsg);
			msg.setSeverity(FacesMessage.SEVERITY_WARN);
			context.addMessage(null, msg);
			
			carritoBean.setFechaRecogida(null);
			
			return null;
		}else{				
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			context.addMessage(null, msg);			
			context.getExternalContext().getFlash().setKeepMessages(true);
			
			return "/app/listaArticulos.xhtml?faces-redirect=true";	
		}			
	}

	// Metodos para la gestion de llamadas desde un item de un datatable, mediante
	// un command button.
	// Siempre tendremos dos metodos, uno que recogera el evento del boton, y con
	// el que obtendremos el objeto necesario
	// Otro metodo con la accion a realizar tras hacer lo que fuere con el objeto
	
	public String abrirSeleccion(){
		carritoBean.getLineaPedido().setArticulo(articulo);

		return "/app/agregarArticulo.xhtml";
	}

	public void selectArticulo(ActionEvent ev) throws IOException {
		articulo = (Articulo) datatableArticulos.getRowData();			
	}
	
	public String actualizarCarrito(){		
		return "/app/carrito.xhtml";
	}

	public void removeLineaPedido(ActionEvent ev) throws IOException {
		LineaPedido lineaPedido = (LineaPedido) datatableLineasPedido.getRowData();	

		carritoBean.getLineasPedido().remove(lineaPedido);
		carritoBean.setTotal(carritoBean.getTotal() - (lineaPedido.getArticulo().getPrecio() * lineaPedido.getCantidad()));
	}
	
	// Getters y Setters
	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public List<Articulo> getListaArticulos() {
		return listaArticulos;
	}

	public void setListaArticulos(List<Articulo> listaArticulos) {
		this.listaArticulos = listaArticulos;
	}

	public HtmlDataTable getDatatableArticulos() {
		return datatableArticulos;
	}

	public void setDatatableArticulos(HtmlDataTable datatableArticulos) {
		this.datatableArticulos = datatableArticulos;
	}

	public HtmlDataTable getDatatableLineasPedido() {
		return datatableLineasPedido;
	}

	public void setDatatableLineasPedido(HtmlDataTable datatableLineasPedido) {
		this.datatableLineasPedido = datatableLineasPedido;
	}

}