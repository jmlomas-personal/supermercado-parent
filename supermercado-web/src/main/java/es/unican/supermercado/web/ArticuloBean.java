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

@Named
@RequestScoped
public class ArticuloBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Articulo articulo = new Articulo();
	private List<Articulo> listaArticulos = new ArrayList<Articulo>();

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

	public ArticuloBean() {
		context = FacesContext.getCurrentInstance();
		bundle = context.getApplication().getResourceBundle(context, "msg");
	}

	@PostConstruct
	public void getArticulos() {
		listaArticulos = visualizaArticulo.verArticulos();
	}

	public String altaArticulo() {

		try{

			articulo = gestionaArticulo.altaArticulo(articulo);
			listaArticulos = visualizaArticulo.verArticulos();						
			msg = new FacesMessage("Se ha dado correctamente de alta el articulo: " + articulo.getNombre());
			msg.setSeverity(FacesMessage.SEVERITY_WARN);
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, msg);
			
			articulo = new Articulo();
			
			return "administration.xhtml";

		}catch(ArticuloYaExisteException e){
			msg = new FacesMessage(bundle.getString("new_article_error"));
			msg.setSeverity(FacesMessage.SEVERITY_WARN);
			context = FacesContext.getCurrentInstance();
			context.addMessage(null, msg);

			return null;
		}

	}

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
			
			return "listaArticulos.xhtml?faces-redirect=true";	
		}			
	}

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

	public String abrirSeleccion(){
		carritoBean.getLineaPedido().setArticulo(articulo);

		return "agregarArticulo.xhtml";
	}

	public void selectArticulo(ActionEvent ev) throws IOException {
		articulo = (Articulo) datatableArticulos.getRowData();			
	}

	public HtmlDataTable getDatatableLineasPedido() {
		return datatableLineasPedido;
	}

	public void setDatatableLineasPedido(HtmlDataTable datatableLineasPedido) {
		this.datatableLineasPedido = datatableLineasPedido;
	}

	public String actualizarCarrito(){		
		return "carrito.xhtml";
	}

	public void removeLineaPedido(ActionEvent ev) throws IOException {
		LineaPedido lineaPedido = (LineaPedido) datatableLineasPedido.getRowData();	

		carritoBean.getLineasPedido().remove(lineaPedido);
		carritoBean.setTotal(carritoBean.getTotal() - (lineaPedido.getArticulo().getPrecio() * lineaPedido.getCantidad()));
	}

}