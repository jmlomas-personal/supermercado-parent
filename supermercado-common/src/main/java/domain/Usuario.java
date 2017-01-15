package domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Clase de dominio que representa
 * un usuario del supermercado
 * @author Juan Manuel Lomas
 *
 */
@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Atributos
	@Id
	@GeneratedValue
	private long id;
	private String nombre;
	private String dni;
	private String direccion;	
	
	// Relaciones con otras clases de dominio
	@OneToMany( mappedBy = "usuario" )
	private List<Pedido> pedidos;	
	
	// Getters y setters
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
}
