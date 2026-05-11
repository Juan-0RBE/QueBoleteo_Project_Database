package co.edu.unbosque.queboleteo.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "CON_GRU")
public class ConGru {

	@EmbeddedId
	private ConGruId id;

	@ManyToOne
	@MapsId("conciertoIdConcierto")
	@JoinColumn(name = "CONCIERTO_IdConcierto", nullable = false)
	private Concierto concierto;

	@ManyToOne
	@MapsId("grupoIdGrupo")
	@JoinColumn(name = "GRUPO_IdGrupo", nullable = false)
	private Grupo grupo;

	public ConGru() {
	}

	/**
	 * 
	 * @param concierto
	 * @param grupo
	 */
	public ConGru(Concierto concierto, Grupo grupo) {
		this.id = new ConGruId(concierto.getIdConcierto(), grupo.getIdGrupo());
		this.concierto = concierto;
		this.grupo = grupo;
	}

	/**
	 * 
	 * @return
	 */
	public ConGruId getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(ConGruId id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public Concierto getConcierto() {
		return concierto;
	}

	/**
	 * 
	 * @param concierto
	 */
	public void setConcierto(Concierto concierto) {
		this.concierto = concierto;
	}

	/**
	 * 
	 * @return
	 */
	public Grupo getGrupo() {
		return grupo;
	}

	/**
	 * 
	 * @param grupo
	 */
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
}