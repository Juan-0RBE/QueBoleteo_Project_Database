package co.edu.unbosque.queboleteo.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ArtGenId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ARTISTA_INDIVIDUAL_IdArtista")
	private Long artistaIndividualIdArtista;

	@Column(name = "GENERO_IdGenero")
	private Long generoIdGenero;

	public ArtGenId() {
	}

	
	/**
	 * Constructor
	 * 
	 * @param artistaIndividualIdArtista
	 * @param generoIdGenero
	 */
	public ArtGenId(Long artistaIndividualIdArtista, Long generoIdGenero) {
		this.artistaIndividualIdArtista = artistaIndividualIdArtista;
		this.generoIdGenero = generoIdGenero;
	}

	/**
	 * 
	 * @return
	 */
	public Long getArtistaIndividualIdArtista() {
		return artistaIndividualIdArtista;
	}

	/**
	 * 
	 * @param artistaIndividualIdArtista
	 */
	public void setArtistaIndividualIdArtista(Long artistaIndividualIdArtista) {
		this.artistaIndividualIdArtista = artistaIndividualIdArtista;
	}

	/**
	 * 
	 * @return
	 */
	public Long getGeneroIdGenero() {
		return generoIdGenero;
	}

	/**
	 * 
	 * @param generoIdGenero
	 */
	public void setGeneroIdGenero(Long generoIdGenero) {
		this.generoIdGenero = generoIdGenero;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ArtGenId that = (ArtGenId) o;
		return java.util.Objects.equals(artistaIndividualIdArtista, that.artistaIndividualIdArtista)
				&& java.util.Objects.equals(generoIdGenero, that.generoIdGenero);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(artistaIndividualIdArtista, generoIdGenero);
	}
}