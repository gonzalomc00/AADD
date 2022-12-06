package aadd.persistencia.dto;

public class CategoriaRestauranteDTO {
	private Integer id;
	private String nombreCategoria;
	
	public CategoriaRestauranteDTO(Integer id,String nombre) {
		this.id=id;
		nombreCategoria=nombre;
	}
	
	public String getNombreCategoria() {
		return nombreCategoria;
	}
	
	public void setNombreCategoria(String nombre) {
		nombreCategoria=nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
