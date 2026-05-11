package co.edu.unbosque.queboleteo.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "ART_GEN")
public class ArtGen {

	@EmbeddedId
	private ArtGenId id;

	@ManyToOne
	@MapsId("artistaIndividualIdArtista")
	@JoinColumn(name = "ARTISTA_INDIVIDUAL_IdArtista", nullable = false)
	private ArtistaIndividual artista;

	@ManyToOne
	@MapsId("generoIdGenero")
	@JoinColumn(name = "GENERO_IdGenero", nullable = false)
	private Genero genero;

	public ArtGen() {
	}

	/**
	 * 
	 * @param artista
	 * @param genero
	 */
	public ArtGen(ArtistaIndividual artista, Genero genero) {
		this.id = new ArtGenId(artista.getIdArtista(), genero.getIdGenero());
		this.artista = artista;
		this.genero = genero;
	}

	/**
	 * 
	 * @return
	 */
	public ArtGenId getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(ArtGenId id) {
		this.id = id;
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