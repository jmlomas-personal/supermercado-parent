package domain;

/**
 * Clase de dominio que representa
 * un articulo del supermercado
 * @author Juan Manuel Lomas
 *
 */
public class Articulo {

	// Atributos
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
	
}
