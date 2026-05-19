package co.edu.unbosque.queboleteo.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "CON_ART")
public class ConArt {

	@EmbeddedId
	private ConArtId id;

	@ManyToOne
	@MapsId("conciertoIdConcierto")
	@JoinColumn(name = "CONCIERTO_IdConcierto", nullable = false)
	private Concierto concierto;

	@ManyToOne
	@MapsId("artistaIndividualIdArtista")
	@JoinColumn(name = "ARTISTA_INDIVIDUAL_IdArtista", nullable = false)
	private ArtistaIndividual artista;

	public ConArt() {
	}

	/**
	 * Constructor
	 * 
	 * @param concierto
	 * @param artista
	 */
	public ConArt(Concierto concierto, ArtistaIndividual artista) {
		this.id = new ConArtId(concierto.getIdConcierto(), artista.getIdArtista());
		this.concierto = concierto;
		this.artista = artista;
	}

	/**
	 * 
	 * @return
	 */
	public ConArtId getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(ConArtId id) {
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
}