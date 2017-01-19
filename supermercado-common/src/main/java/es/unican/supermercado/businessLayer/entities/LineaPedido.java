package es.unican.supermercado.businessLayer.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Clase de dominio que representa
 * una linea del pedido
 * @author Juan Manuel Lomas
 *
 */
@Entity @Table(name = "LINEAPEDIDO")
public class LineaPedido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Atributos
	@Id
	@GeneratedValue
	private String id;
	private int cantidad;

	// Relaciones con otras clases de dominio
	@ManyToOne
	@JoinColumn ( name = "articulo_fk" )
	private Articulo articulo;
	
	@ManyToOne
	@JoinColumn ( name = "pedido_fk" )
	private Pedido pedido;
	
	// Setters y getters
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}	
	
}

