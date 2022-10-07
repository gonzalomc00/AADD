package aadd.persistencia.jpa.bean;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: CategoriaRestaurante
 *
 */
@Entity
@Table(name = "categoria")
public class CategoriaRestaurante implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "nombre")
	private String nombre;
	
	//Podemos quitarlo para dejarlo mas simple	(unidireccional)
	//@ManyToMany(mappedBy= "categorias", cascade = CascadeType.ALL)
	//private List<Restaurante> restaurantes;
	
	private static final long serialVersionUID = 1L;

	public CategoriaRestaurante() {
		super();
	}
   //getters y setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
