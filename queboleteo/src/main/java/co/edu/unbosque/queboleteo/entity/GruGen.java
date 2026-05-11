package co.edu.unbosque.queboleteo.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "GRU_GEN")
public class GruGen {

	@EmbeddedId
	private GruGenId id;

	@ManyToOne
	@MapsId("grupoIdGrupo")
	@JoinColumn(name = "GRUPO_IdGrupo", nullable = false)
	private Grupo grupo;

	@ManyToOne
	@MapsId("generoIdGenero")
	@JoinColumn(name = "GENERO_IdGenero", nullable = false)
	private Genero genero;

	public GruGen() {
	}

	/**
	 * 
	 * @param grupo
	 * @param genero
	 */
	public GruGen(Grupo grupo, Genero genero) {
		this.id = new GruGenId(grupo.getIdGrupo(), genero.getIdGenero());
		this.grupo = grupo;
		this.genero = genero;
	}

	/**
	 * 
	 * @return
	 */
	public GruGenId getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(GruGenId id) {
		this.id = id;
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

	/**
	 * 
	 * @return
	 */
	public Genero getGenero() {
		return genero;
	}

	/**
	 * 
	 * @param genero
	 */
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
}