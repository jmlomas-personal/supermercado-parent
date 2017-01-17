package common.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase de dominio que representa
 * un pedido del supermercado
 * @author Juan Manuel Lomas
 *
 */
@Entity @Table(name = "PEDIDO")
@NamedQueries({
	@NamedQuery ( name = "pedidoPorEstadoyFecha", query = "SELECT p FROM Pedido p WHERE p.estado = :estado ORDER BY p.fecha DESC" )
})
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Atributos
	@Id
	@GeneratedValue
	private String id;
	
	@Enumerated(EnumType.STRING)
	private EstadoPedido estado;
	private Date fecha;
	private Date horaRecogida;	

	// Relaciones con otras clases de dominio
	@ManyToOne
	@JoinColumn ( name = "usuario_fk" )
	private Usuario usuario;
	
	@OneToMany( mappedBy = "pedido" )
	private List<LineaPedido> lineasPedido;	
	
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<LineaPedido> getLineasPedido() {
		return lineasPedido;
	}

	public void setLineasPedido(List<LineaPedido> lineasPedido) {
		this.lineasPedido = lineasPedido;
	}
	
	public void anyadeLineaPedido(LineaPedido lineaPedido){
		this.lineasPedido.add(lineaPedido);
	}
	
	public void eliminaLineaPedido(LineaPedido lineaPedido){
		this.lineasPedido.remove(lineaPedido);
	}
	
	public Date getHoraRecogida() {
		return horaRecogida;
	}

	public void setHoraRecogida(Date horaRecogida) {
		this.horaRecogida = horaRecogida;
	}
	
}
