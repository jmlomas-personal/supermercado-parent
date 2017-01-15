package domain;

import java.sql.Date;

/**
 * Clase de dominio que representa
 * un pedido del supermercado
 * @author Juan Manuel Lomas
 *
 */
public class Pedido {

	// Atributos
	private String id;
	private EstadoPedido estado;
	private Date fecha;
	
	// Getters y setters
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public EstadoPedido getEstado() {
		return estado;
	}
	
	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
