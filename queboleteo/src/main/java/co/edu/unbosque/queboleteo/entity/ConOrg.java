package co.edu.unbosque.queboleteo.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "CON_ORG")
public class ConOrg {

	@EmbeddedId
	private ConOrgId id;

	@ManyToOne
	@MapsId("conciertoIdConcierto")
	@JoinColumn(name = "CONCIERTO_IdConcierto", nullable = false)
	private Concierto concierto;

	@ManyToOne
	@MapsId("organizadorNombreOrganizador")
	@JoinColumn(name = "ORGANIZADOR_NombreOrganizador", nullable = false)
	private Organizador organizador;

	public ConOrg() {
	}

	/**
	 * Constructor
	 * 
	 * @param concierto
	 * @param organizador
	 */
	public ConOrg(Concierto concierto, Organizador organizador) {
		this.id = new ConOrgId(concierto.getIdConcierto(), organizador.getNombreOrganizador());
		this.concierto = concierto;
		this.organizador = organizador;
	}

	/**
	 * @return the id
	 */
	public ConOrgId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ConOrgId id) {
		this.id = id;
	}

	/**
	 * @return the concierto
	 */
	public Concierto getConcierto() {
		return concierto;
	}

	/**
	 * @param concierto the concierto to set
	 */
	public void setConcierto(Concierto concierto) {
		this.concierto = concierto;
	}

	/**
	 * @return the organizador
	 */
	public Organizador getOrganizador() {
		return organizador;
	}

	/**
	 * @param organizador the organizador to set
	 */
	public void setOrganizador(Organizador organizador) {
		this.organizador = organizador;
	}
	
	

}