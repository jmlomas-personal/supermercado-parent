package es.unican.supermercado.businessLayer.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Clase de dominio que representa
 * un articulo del supermercado
 * @author Juan Manuel Lomas
 *
 */
@Entity @Table(name = "ARTICULO")
@NamedQueries({
	@NamedQuery ( name = "articuloPorNombre", query = "SELECT a FROM Articulo a WHERE a.nombre = :nombre" )
})
public class Articulo implements Serializable {

	private static final long serialVersionUID = 1L;

	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String nombre;
	private int unidadesStock;
	private double precio;

	public Articulo(){

	}

	public Articulo(String nombre, double precio, int unidadesStock){
		this.nombre = nombre;
		this.unidadesStock = unidadesStock;
		this.precio = precio;
	}

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
