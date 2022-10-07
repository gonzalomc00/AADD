package aadd.persistencia.jpa.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Restaurante
 *
 */
@Entity
@Table(name = "restaurante") // para nombrar la tabla en BBDD
public class Restaurante implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ID autogenerado
	@Column(name = "id") // @Column para darle el nombre a la columna y controlar como se ponen en la
							// BBDD
	private Integer id; // clave primaria @ID
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "fecha_alta", columnDefinition = "DATE") // columnDefinition para que lo identifique como Date de SQL
	private LocalDate fechaAlta;
	@Column(name = "valoracion_global")
	private Double valoracionGlobal;
	@Column(name = "num_valoraciones")
	private Integer numValoraciones;
	@Column(name = "num_penalizaciones")
	private Integer numPenalizaciones;
	
	@JoinColumn(name= "responsable") //define la columna para la clave ajena de esta manera!!
	@ManyToOne
	private Usuario responsable;
	
	@OneToMany(mappedBy= "restaurante", cascade = CascadeType.ALL) //mapped by para decir que es una misma relacion (bidireccional)
	private List<Plato> platos;
	
	//Ejercicio Propuesto 
	@JoinColumn(name = "categorias_restaurante")
	@ManyToMany
	private List<CategoriaRestaurante> categorias;
	
	private static final long serialVersionUID = 1L;

	public Restaurante() {
		super();
	}
	// getters y setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Double getValoracionGlobal() {
		return valoracionGlobal;
	}

	public void setValoracionGlobal(Double valoracionGlobal) {
		this.valoracionGlobal = valoracionGlobal;
	}

	public Integer getNumValoraciones() {
		return numValoraciones;
	}

	public void setNumValoraciones(Integer numValoraciones) {
		this.numValoraciones = numValoraciones;
	}

	public Integer getNumPenalizaciones() {
		return numPenalizaciones;
	}

	public void setNumPenalizaciones(Integer numPenalizaciones) {
		this.numPenalizaciones = numPenalizaciones;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	public List<CategoriaRestaurante> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaRestaurante> categorias) {
		this.categorias = categorias;
	}
	
	public void addCategoria(CategoriaRestaurante c) {
		if (categorias == null) {
			categorias = new LinkedList<CategoriaRestaurante>();
		}
		this.categorias.add(c);	
	}
	
	

}
