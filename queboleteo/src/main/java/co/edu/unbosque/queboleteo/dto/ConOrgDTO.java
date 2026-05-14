package co.edu.unbosque.queboleteo.dto;

import java.util.Objects;

public class ConOrgDTO {

	private Long idConcierto;

	private String nombreOrganizador;

	public ConOrgDTO() {
	}

	/**
	 * @param idConcierto
	 * @param nombreOrganizador
	 */
	public ConOrgDTO(Long idConcierto, String nombreOrganizador) {
		super();
		this.idConcierto = idConcierto;
		this.nombreOrganizador = nombreOrganizador;
	}

	/**
	 * @return the idConcierto
	 */
	public Long getIdConcierto() {
		return idConcierto;
	}

	/**
	 * @param idConcierto the idConcierto to set
	 */
	public void setIdConcierto(Long idConcierto) {
		this.idConcierto = idConcierto;
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

	@Override
	public int hashCode() {
		return Objects.hash(idConcierto, nombreOrganizador);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConOrgDTO other = (ConOrgDTO) obj;
		return Objects.equals(idConcierto, other.idConcierto)
				&& Objects.equals(nombreOrganizador, other.nombreOrganizador);
	}

	@Override
	public String toString() {
		return "ConOrgDTO [idConcierto=" + idConcierto + ", nombreOrganizador=" + nombreOrganizador + "]";
	}
}