package co.edu.unbosque.queboleteo.dto;

import java.util.Objects;

public class ArtGenDTO {

	private Long idArtista;
	private Long idGenero;

	public ArtGenDTO() {
	}

	/**
	 * @param idArtista
	 * @param idGenero
	 */
	public ArtGenDTO(Long idArtista, Long idGenero) {
		super();
		this.idArtista = idArtista;
		this.idGenero = idGenero;
	}

	/**
	 * @return the idArtista
	 */
	public Long getIdArtista() {
		return idArtista;
	}

	/**
	 * @param idArtista the idArtista to set
	 */
	public void setIdArtista(Long idArtista) {
		this.idArtista = idArtista;
	}

	/**
	 * @return the idGenero
	 */
	public Long getIdGenero() {
		return idGenero;
	}

	/**
	 * @param idGenero the idGenero to set
	 */
	public void setIdGenero(Long idGenero) {
		this.idGenero = idGenero;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idArtista, idGenero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArtGenDTO other = (ArtGenDTO) obj;
		return Objects.equals(idArtista, other.idArtista) && Objects.equals(idGenero, other.idGenero);
	}

	@Override
	public String toString() {
		return "ArtGenDTO [idArtista=" + idArtista + ", idGenero=" + idGenero + "]";
	}
}