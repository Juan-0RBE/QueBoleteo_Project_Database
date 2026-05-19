package co.edu.unbosque.queboleteo.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ConOrgId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CONCIERTO_IdConcierto")
	private Long conciertoIdConcierto;

	@Column(name = "ORGANIZADOR_NombreOrganizador", length = 100)
	private String organizadorNombreOrganizador;

	public ConOrgId() {
	}

	/**
	 * Constructor
	 * 
	 * @param conciertoIdConcierto
	 * @param organizadorNombreOrganizador
	 */
	public ConOrgId(Long conciertoIdConcierto, String organizadorNombreOrganizador) {
		super();
		this.conciertoIdConcierto = conciertoIdConcierto;
		this.organizadorNombreOrganizador = organizadorNombreOrganizador;
	}


	/**
	 * @return the conciertoIdConcierto
	 */
	public Long getConciertoIdConcierto() {
		return conciertoIdConcierto;
	}

	/**
	 * @param conciertoIdConcierto the conciertoIdConcierto to set
	 */
	public void setConciertoIdConcierto(Long conciertoIdConcierto) {
		this.conciertoIdConcierto = conciertoIdConcierto;
	}

	/**
	 * @return the organizadorNombreOrganizador
	 */
	public String getOrganizadorNombreOrganizador() {
		return organizadorNombreOrganizador;
	}

	/**
	 * @param organizadorNombreOrganizador the organizadorNombreOrganizador to set
	 */
	public void setOrganizadorNombreOrganizador(String organizadorNombreOrganizador) {
		this.organizadorNombreOrganizador = organizadorNombreOrganizador;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ConOrgId that = (ConOrgId) o;
		return java.util.Objects.equals(conciertoIdConcierto, that.conciertoIdConcierto)
				&& java.util.Objects.equals(organizadorNombreOrganizador, that.organizadorNombreOrganizador);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(conciertoIdConcierto, organizadorNombreOrganizador);
	}
}