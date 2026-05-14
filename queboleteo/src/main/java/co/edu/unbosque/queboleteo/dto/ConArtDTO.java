package co.edu.unbosque.queboleteo.dto;

import java.util.Objects;

public class ConArtDTO {

	private Long idConcierto;
	private Long idArtista;

	public ConArtDTO() {
	}

	/**
	 * @param idConcierto
	 * @param idArtista
	 */
	public ConArtDTO(Long idConcierto, Long idArtista) {
		super();
		this.idConcierto = idConcierto;
		this.idArtista = idArtista;
	}

	/**
	 * @return the idConcierto
	 */
	public Long getIdConcierto() {
		return idConcierto;
	}

	/**
	 * @param idConcierto the idConcierto to set
	 */
	public void setIdConcierto(Long idConcierto) {
		this.idConcierto = idConcierto;
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

	@Override
	public int hashCode() {
		return Objects.hash(idConcierto, idArtista);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConArtDTO other = (ConArtDTO) obj;
		return Objects.equals(idConcierto, other.idConcierto) && Objects.equals(idArtista, other.idArtista);
	}

	@Override
	public String toString() {
		return "ConArtDTO [idConcierto=" + idConcierto + ", idArtista=" + idArtista + "]";
	}
}