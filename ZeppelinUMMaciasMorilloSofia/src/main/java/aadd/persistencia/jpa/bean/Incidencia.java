package aadd.persistencia.jpa.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Incidencia
 *
 */
@Entity
@Table(name = "incidencia")
@NamedQueries({
	@NamedQuery(name = "Incidencia.findByUsuario", query = " SELECT i FROM Incidencia i WHERE i.usuario.id = :usuario")})
public class Incidencia implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "fechaCreacion", columnDefinition= "DATE")
	private LocalDateTime fechaCreacion;
	@Column(name = "descripcion")
	@Lob
	private String descripcion;
	@Column(name = "comentario")
	@Lob
	private String comentario;
	
	@Column(name = "fechaCierre", columnDefinition="DATE")
	private LocalDateTime fechaCierre;
	
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

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public LocalDateTime getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(LocalDateTime fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	

	
}
