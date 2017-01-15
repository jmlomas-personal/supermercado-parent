package domain;

/**
 * Clase de dominio que representa
 * un usuario del supermercado
 * @author Juan Manuel Lomas
 *
 */
public class Usuario {

	// Atributos
	private String nombre;
	private String dni;
	private String direccion;
	
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
	
}
