package common.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase de dominio que representa
 * un usuario del supermercado
 * @author Juan Manuel Lomas
 *
 */
@Entity @Table(name = "USUARIO")
@NamedQueries({
	@NamedQuery ( name = "usuariosPorDni", query = "SELECT u FROM Usuario u WHERE u.dni = :dni" )
})
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	// Atributos
	@Id
	@GeneratedValue
	private String id;
	private String nombre;
	@Column(unique = true)
	private String dni;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

}
