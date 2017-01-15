package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Clase de dominio que representa
 * un articulo del supermercado
 * @author Juan Manuel Lomas
 *
 */
@Entity
public class Articulo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Atributos
	@Id
	@GeneratedValue
	private long id;
	private String nombre;
	private int unidadesStock;
	private double precio;
	
	// Setters y getters
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getUnidadesStock() {
		return unidadesStock;
	}
	
	public void setUnidadesStock(int unidadesStock) {
		this.unidadesStock = unidadesStock;
	}
	
	public double getPrecio() {
		return precio;
	}
	
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
