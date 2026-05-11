package co.edu.unbosque.queboleteo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "GRU_ART")
public class GruArt {

	@EmbeddedId
	private GruArtId id;

	@ManyToOne
	@MapsId("grupoIdGrupo")
	@JoinColumn(name = "GRUPO_IdGrupo", nullable = false)
	private Grupo grupo;

	@ManyToOne
	@MapsId("artistaIndividualIdArtista")
	@JoinColumn(name = "ARTISTA_INDIVIDUAL_IdArtista", nullable = false)
	private ArtistaIndividual artista;

	@Column(name = "Rol", length = 50)
	private String rol;

	public GruArt() {
	}

	/**
	 * 
	 * @param grupo
	 * @param artista
	 * @param rol
	 */
	public GruArt(Grupo grupo, ArtistaIndividual artista, String rol) {
		this.id = new GruArtId(grupo.getIdGrupo(), artista.getIdArtista());
		this.grupo = grupo;
		this.artista = artista;
		this.rol = rol;
	}

	/**
	 * 
	 * @return
	 */
	public GruArtId getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(GruArtId id) {
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
	public ArtistaIndividual getArtista() {
		return artista;
	}

	/**
	 * 
	 * @param artista
	 */
	public void setArtista(ArtistaIndividual artista) {
		this.artista = artista;
	}

	/**
	 * 
	 * @return
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * 
	 * @param rol
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}
}