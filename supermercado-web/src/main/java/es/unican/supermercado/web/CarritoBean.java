package es.unican.supermercado.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import es.unican.supermercado.businessLayer.entities.LineaPedido;

@Named
@SessionScoped
public class CarritoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private double total = 0.0;
	private LineaPedido lineaPedido = new LineaPedido();
	private List<LineaPedido> lineasPedido = new ArrayList<LineaPedido>();	
	
	@Inject
	UsuarioBean usuarioBean;
	
	public CarritoBean() {				
	}
	
	public String agregarArticuloAlCarrito(){				
		
		lineasPedido.add(lineaPedido);
		total += (lineaPedido.getArticulo().getPrecio() * lineaPedido.getCantidad());
		lineaPedido = new LineaPedido();
		
		return "listaArticulos.xhtml";
	}

	public List<LineaPedido> getLineasPedido() {
		return lineasPedido;
	}

	public void setLineasPedido(List<LineaPedido> lineasPedido) {
		this.lineasPedido = lineasPedido;
	}

	public LineaPedido getLineaPedido() {
		return lineaPedido;
	}

	public void setLineaPedido(LineaPedido lineaPedido) {
		this.lineaPedido = lineaPedido;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}