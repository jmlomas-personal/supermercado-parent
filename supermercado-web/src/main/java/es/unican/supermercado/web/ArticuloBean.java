package es.unican.supermercado.web;

import java.io.IOException;
import java.io.Serializable;
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
			return "listaArticulos.xhtml";
			
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
		
		if(lineas.isEmpty()){
			msg = new FacesMessage(bundle.getString("cart_empty_error"));
	        msg.setSeverity(FacesMessage.SEVERITY_WARN);
	        context = FacesContext.getCurrentInstance();
	        context.addMessage(null, msg);
	        				       
	        return "carrito.xhtml";
		}else{
			
			try {
				gestionaPedido.crearPedido(usuarioBean.getUsuario().getDni());
					
				for(LineaPedido linea : lineas){
					
					try {
						gestionaPedido.anyadeLineaPedido(linea);
					} catch (StockInsuficienteException e) {	
						msg = new FacesMessage("Error de stock");
				        msg.setSeverity(FacesMessage.SEVERITY_WARN);
				        context = FacesContext.getCurrentInstance();
				        context.addMessage(null, msg);
				        
				        return null;
					}
				}
				
				Pedido p = gestionaPedido.confirmarPedido();
				
				msg = new FacesMessage(bundle.getString("cart_process_ok") + " Referencia de pedido: " + p.getId());
		        msg.setSeverity(FacesMessage.SEVERITY_WARN);
		        context = FacesContext.getCurrentInstance();
		        context.addMessage(null, msg);
			} catch (UsuarioNoExisteException e) {				
			}					
	        
			return "listaArticulos.xhtml";
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