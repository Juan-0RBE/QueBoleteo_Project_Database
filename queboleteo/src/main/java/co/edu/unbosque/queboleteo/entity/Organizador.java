package co.edu.unbosque.queboleteo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ORGANIZADOR")
public class Organizador {

	@Id
	@Column(name = "NombreOrganizador", length = 100)
	private String nombreOrganizador;

	@Column(name = "CorreoOrganizador", length = 100)
	private String correoOrganizador;

	@Column(name = "Logo", length = 2000)
	private String logo;

	public Organizador() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param nombreOrganizador
	 * @param correoOrganizador
	 * @param logo
	 */
	public Organizador(String nombreOrganizador, String correoOrganizador, String logo) {
		super();
		this.nombreOrganizador = nombreOrganizador;
		this.correoOrganizador = correoOrganizador;
		this.logo = logo;
	}

	/**
	 * @return the nombreOrganizador
	 */
	public String getNombreOrganizador() {
		return nombreOrganizador;
	}

	/**
	 * @param nombreOrganizador the nombreOrganizador to set
	 */
	public void setNombreOrganizador(String nombreOrganizador) {
		this.nombreOrganizador = nombreOrganizador;
	}

	/**
	 * @return the correoOrganizador
	 */
	public String getCorreoOrganizador() {
		return correoOrganizador;
	}

	/**
	 * @param correoOrganizador the correoOrganizador to set
	 */
	public void setCorreoOrganizador(String correoOrganizador) {
		this.correoOrganizador = correoOrganizador;
	}

	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

}
