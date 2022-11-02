package aadd.persistencia.jpa.bean;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity
@Table(name = "usuario")
//CONSULTAS NOMBRADAS EN LA CABECERA DE LA CLASE
@NamedQueries({
		@NamedQuery(name = "Usuario.findByEmailClave", query = " SELECT u FROM Usuario u WHERE u.email = :email and u.clave = :clave AND u.validado = true"),
		@NamedQuery(name = "Usuario.findByEmail", query = " SELECT u FROM Usuario u WHERE u.email = :email ")})

//la consulta se invoca desde UsuarioDAO, al registrarse y logearse se recuperarán los datos desde UsuarioDAO, sin que la capa de la vista lo vea.
//nunca devolver el objeto completo que hemos recuperado de la base de datos, mediante el DTO. Nunca mandar hacia la capa superiores objetos de persistencia
public class Usuario implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "email")
	private String email;
	@Column(name = "clave")
	private String clave;
	@Column(name = "fecha_nacimiento", columnDefinition = "DATE")
	private LocalDate fechaNacimiento;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "apellidos")
	private String apellidos;
	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipo;
	@Column(name = "validado")
	private boolean validado;

	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}
	// getters y setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public boolean isValidado() {
		return validado;
	}

	public void setValidado(boolean validado) {
		this.validado = validado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}