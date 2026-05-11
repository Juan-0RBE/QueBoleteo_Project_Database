package co.edu.unbosque.queboleteo.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ConArtId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CONCIERTO_IdConcierto")
	private Long conciertoIdConcierto;

	@Column(name = "ARTISTA_INDIVIDUAL_IdArtista")
	private Long artistaIndividualIdArtista;

	public ConArtId() {
	}

	/**
	 * @param conciertoIdConcierto
	 * @param artistaIndividualIdArtista
	 */
	public ConArtId(Long conciertoIdConcierto, Long artistaIndividualIdArtista) {
		this.conciertoIdConcierto = conciertoIdConcierto;
		this.artistaIndividualIdArtista = artistaIndividualIdArtista;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Long getConciertoIdConcierto() {
		return conciertoIdConcierto;
	}

	/**
	 * 
	 * @param conciertoIdConcierto
	 */
	public void setConciertoIdConcierto(Long conciertoIdConcierto) {
		this.conciertoIdConcierto = conciertoIdConcierto;
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
	

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ConArtId that = (ConArtId) o;
		return java.util.Objects.equals(conciertoIdConcierto, that.conciertoIdConcierto)
				&& java.util.Objects.equals(artistaIndividualIdArtista, that.artistaIndividualIdArtista);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(conciertoIdConcierto, artistaIndividualIdArtista);
	}
}