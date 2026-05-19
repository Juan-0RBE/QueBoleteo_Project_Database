package co.edu.unbosque.queboleteo.dto;

import java.time.LocalDate;
import java.util.Objects;

import co.edu.unbosque.queboleteo.entity.Usuario.Role;

public class UsuarioDTO {

	private String correo;
	private String nombreUsuario;
	private String clave;
	private String documentoIdentidad;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private LocalDate fechaNacimiento;
	private Integer edad;
	private String numeroTelefono;
	private Boolean isVerified;
	private Role role;

	public UsuarioDTO() {
	}

	/**
	 * Constructor
	 * 
	 * @param correo
	 * @param nombreUsuario
	 * @param clave
	 * @param documentoIdentidad
	 * @param primerNombre
	 * @param segundoNombre
	 * @param primerApellido
	 * @param segundoApellido
	 * @param fechaNacimiento
	 * @param edad
	 * @param numeroTelefono
	 */
	public UsuarioDTO(String correo, String nombreUsuario, String clave,
			String documentoIdentidad, String primerNombre,
			String segundoNombre, String primerApellido,
			String segundoApellido, LocalDate fechaNacimiento,
			Integer edad, String numeroTelefono) {

		this.correo = correo;
		this.nombreUsuario = nombreUsuario;
		this.clave = clave;
		this.documentoIdentidad = documentoIdentidad;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.fechaNacimiento = fechaNacimiento;
		this.edad = edad;
		this.numeroTelefono = numeroTelefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	public void setDocumentoIdentidad(String documentoIdentidad) {
		this.documentoIdentidad = documentoIdentidad;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(correo, nombreUsuario,
				documentoIdentidad);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		UsuarioDTO other = (UsuarioDTO) obj;

		return Objects.equals(correo, other.correo)
				&& Objects.equals(nombreUsuario,
						other.nombreUsuario)
				&& Objects.equals(documentoIdentidad,
						other.documentoIdentidad);
	}

	@Override
	public String toString() {

		return "UsuarioDTO [correo=" + correo
				+ ", nombreUsuario=" + nombreUsuario
				+ ", documentoIdentidad="
				+ documentoIdentidad + "]";
	}
	
	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}


}