package common.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase de dominio que representa
 * un articulo del supermercado
 * @author Juan Manuel Lomas
 *
 */
@Entity @Table(name = "ARTICULO") 
public class Articulo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Atributos
	@Id
	@GeneratedValue
	private String id;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
