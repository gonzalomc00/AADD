package aadd.persistencia.jpa.bean;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Plato
 *
 */
@Entity
@Table(name = "plato")
@NamedQueries({
		@NamedQuery(name = "Plato.findPlatosDisponiblesByRestaurante", query = " SELECT p FROM Plato p WHERE p.disponibilidad = true and p.restaurante.id = :restaurante ") })
//vamos a devolver a la vista plato pero NO la entidad plato, sino un platoDTO con lo que nos interese.
public class Plato implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "titulo")
	private String titulo;
	@Column(name = "descripcion")
	@Lob // etiqueta que indica que un string va a ser largo en la base de datos (long
			// text)
	private String descripcion;
	@Column(name = "precio")
	private Double precio;
	@Column(name = "disponibilidad")
	private boolean disponibilidad;

	@ManyToOne
	@JoinColumn(name = "restaurante") // el orden de las etiquetas dan igual mientras que esten encima del atributo
										// que aplica
	private Restaurante restaurante; // vamos a hacer la relacion restaurante-plato bidireccional

	private static final long serialVersionUID = 1L;

	public Plato() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public boolean isDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

}
