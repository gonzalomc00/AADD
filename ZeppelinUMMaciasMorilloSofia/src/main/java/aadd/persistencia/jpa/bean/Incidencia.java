package aadd.persistencia.jpa.bean;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Incidencia
 *
 */
@Entity
@Table(name = "incidencia")
@NamedQueries({
	@NamedQuery(name = "Incidencia.findByUsuario", query = " SELECT i FROM Incidencia i WHERE i.usuario = :usuario")})
public class Incidencia implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "fecha creacion",columnDefinition= "DATE")
	private LocalDate fechaCreacion;
	@Column(name = "descripcion")
	@Lob
	private String descripcion;
	@Column(name = "fecha_alta", columnDefinition = "DATE")
	private LocalDate fechaAlta;
	@Column(name = "comentario")
	@Lob
	private String comentario;
	
	@Column(name = "fecha_cierre", columnDefinition="DATE")
	private LocalDate fechaCierre;
	
	@JoinColumn(name= "usuario")
	@ManyToOne
	private Usuario usuario;
	
	//Restaurante-Incidencia
	@JoinColumn(name= "restaurante")
	@ManyToOne
	private Restaurante restaurante;
	
	
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Incidencia() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public LocalDate getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(LocalDate fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	

	
}
