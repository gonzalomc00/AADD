package aadd.persistencia.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class IncidenciaDTO {

	private int id;
	private LocalDateTime fechaCreacion;
	private String descripcion;
	private String comentario;
	private LocalDateTime fechaCierre;



	public IncidenciaDTO(int id, LocalDateTime fechaCreacion, String descripcion, String comentario, LocalDateTime fechaCierre) {
		this.id=id;
		this.fechaCreacion=fechaCreacion;
		this.descripcion=descripcion;
		this.comentario=comentario;
		this.fechaCierre=fechaCierre;
		
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

	public LocalDateTime getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(LocalDateTime fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	


}
