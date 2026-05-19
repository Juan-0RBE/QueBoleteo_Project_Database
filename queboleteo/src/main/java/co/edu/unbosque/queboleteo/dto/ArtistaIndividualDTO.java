package co.edu.unbosque.queboleteo.dto;

import java.util.Objects;

public class ArtistaIndividualDTO {

	private Long idArtista;
	private String nombreArtista;
	private String descripcionArtista;
	private String imagenArtista;
	private String paisOrigenArtista;
	private Integer edadArtista;
	private String lenguajeArtista;

	public ArtistaIndividualDTO() {
	}

	/**
	 * Constructor
	 * 
	 * @param nombreArtista
	 * @param descripcionArtista
	 * @param imagenArtista
	 * @param paisOrigenArtista
	 * @param edadArtista
	 * @param lenguajeArtista
	 */
	public ArtistaIndividualDTO(String nombreArtista, String descripcionArtista, String imagenArtista,
			String paisOrigenArtista, Integer edadArtista, String lenguajeArtista) {
		super();
		this.nombreArtista = nombreArtista;
		this.descripcionArtista = descripcionArtista;
		this.imagenArtista = imagenArtista;
		this.paisOrigenArtista = paisOrigenArtista;
		this.edadArtista = edadArtista;
		this.lenguajeArtista = lenguajeArtista;
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
	 * @return the nombreArtista
	 */
	public String getNombreArtista() {
		return nombreArtista;
	}

	/**
	 * @param nombreArtista the nombreArtista to set
	 */
	public void setNombreArtista(String nombreArtista) {
		this.nombreArtista = nombreArtista;
	}

	/**
	 * @return the descripcionArtista
	 */
	public String getDescripcionArtista() {
		return descripcionArtista;
	}

	/**
	 * @param descripcionArtista the descripcionArtista to set
	 */
	public void setDescripcionArtista(String descripcionArtista) {
		this.descripcionArtista = descripcionArtista;
	}

	/**
	 * @return the imagenArtista
	 */
	public String getImagenArtista() {
		return imagenArtista;
	}

	/**
	 * @param imagenArtista the imagenArtista to set
	 */
	public void setImagenArtista(String imagenArtista) {
		this.imagenArtista = imagenArtista;
	}

	/**
	 * @return the paisOrigenArtista
	 */
	public String getPaisOrigenArtista() {
		return paisOrigenArtista;
	}

	/**
	 * @param paisOrigenArtista the paisOrigenArtista to set
	 */
	public void setPaisOrigenArtista(String paisOrigenArtista) {
		this.paisOrigenArtista = paisOrigenArtista;
	}

	/**
	 * @return the edadArtista
	 */
	public Integer getEdadArtista() {
		return edadArtista;
	}

	/**
	 * @param edadArtista the edadArtista to set
	 */
	public void setEdadArtista(Integer edadArtista) {
		this.edadArtista = edadArtista;
	}

	/**
	 * @return the lenguajeArtista
	 */
	public String getLenguajeArtista() {
		return lenguajeArtista;
	}

	/**
	 * @param lenguajeArtista the lenguajeArtista to set
	 */
	public void setLenguajeArtista(String lenguajeArtista) {
		this.lenguajeArtista = lenguajeArtista;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descripcionArtista, edadArtista, idArtista, imagenArtista, lenguajeArtista, nombreArtista,
				paisOrigenArtista);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		ArtistaIndividualDTO other = (ArtistaIndividualDTO) obj;

		return Objects.equals(descripcionArtista, other.descripcionArtista)
				&& Objects.equals(edadArtista, other.edadArtista) && Objects.equals(idArtista, other.idArtista)
				&& Objects.equals(imagenArtista, other.imagenArtista)
				&& Objects.equals(lenguajeArtista, other.lenguajeArtista)
				&& Objects.equals(nombreArtista, other.nombreArtista)
				&& Objects.equals(paisOrigenArtista, other.paisOrigenArtista);
	}

	@Override
	public String toString() {
		return "ArtistaIndividualDTO [idArtista=" + idArtista + ", nombreArtista=" + nombreArtista
				+ ", descripcionArtista=" + descripcionArtista + ", imagenArtista=" + imagenArtista
				+ ", paisOrigenArtista=" + paisOrigenArtista + ", edadArtista=" + edadArtista + ", lenguajeArtista="
				+ lenguajeArtista + "]";
	}

}