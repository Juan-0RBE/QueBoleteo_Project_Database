package co.edu.unbosque.queboleteo.dto;

public class OrganizadorDTO {

	private String nombreOrganizador;

	private String correoOrganizador;

	private String logo;

	public OrganizadorDTO() {
	}

	/**
	 * @param nombreOrganizador
	 * @param correoOrganizador
	 * @param logo
	 */
	public OrganizadorDTO(String nombreOrganizador,
			String correoOrganizador,
			String logo) {

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