package co.edu.unbosque.queboleteo.dto;

import java.util.Objects;

public class GeneroDTO {

	private Long idGenero;
	private String nombreGenero;

	public GeneroDTO() {
	}

	/**
	 * Constructor
	 * 
	 * @param nombreGenero
	 */
	public GeneroDTO(String nombreGenero) {
		this.nombreGenero = nombreGenero;
	}

	/**
	 * 
	 * @return
	 */
	public Long getIdGenero() {
		return idGenero;
	}

	/**
	 * 
	 * @param idGenero
	 */
	public void setIdGenero(Long idGenero) {
		this.idGenero = idGenero;
	}

	/**
	 * 
	 * @return
	 */
	public String getNombreGenero() {
		return nombreGenero;
	}

	/**
	 * 
	 * @param nombreGenero
	 */
	public void setNombreGenero(String nombreGenero) {
		this.nombreGenero = nombreGenero;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombreGenero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeneroDTO other = (GeneroDTO) obj;
		return Objects.equals(nombreGenero, other.nombreGenero);
	}

	@Override
	public String toString() {
		return "GeneroDTO [idGenero=" + idGenero + ", nombreGenero=" + nombreGenero + "]";
	}

}