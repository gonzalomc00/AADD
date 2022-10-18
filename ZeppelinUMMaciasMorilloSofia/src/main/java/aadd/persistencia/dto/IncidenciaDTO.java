package aadd.persistencia.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import aadd.persistencia.jpa.bean.Restaurante;
import aadd.persistencia.jpa.bean.Usuario;

public class IncidenciaDTO {

	private int id;
	private LocalDate fechaCreacion;
	private String descripcion;
	private LocalDate fechaAlta;
	private String comentario;
	private LocalDate fechaCierre;



	public IncidenciaDTO(int id, LocalDate fechaCreacion, String descripcion, LocalDate fechaAlta, String comentario, LocalDate fechaCierre) {
		this.id=id;
		this.fechaCreacion=fechaCreacion;
		this.descripcion=descripcion;
		this.fechaAlta=fechaAlta;
		this.comentario=comentario;
		this.fechaCierre=fechaCierre;
		
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

	public LocalDate getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(LocalDate fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	


}
