package co.edu.unbosque.queboleteo.dto;

import java.util.Objects;

public class ZonaDTO {

	private Long idZona;
	private String nombreZona;
	private Boolean tieneAsiento;

	private String nombreSede;

	public ZonaDTO() {
	}

	/**
	 * @param nombreZona
	 * @param tieneAsiento
	 * @param nombreSede
	 */
	public ZonaDTO(String nombreZona, Boolean tieneAsiento, String nombreSede) {
		super();
		this.nombreZona = nombreZona;
		this.tieneAsiento = tieneAsiento;
		this.nombreSede = nombreSede;
	}

	/**
	 * @return the idZona
	 */
	public Long getIdZona() {
		return idZona;
	}

	/**
	 * @param idZona the idZona to set
	 */
	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	/**
	 * @return the nombreZona
	 */
	public String getNombreZona() {
		return nombreZona;
	}

	/**
	 * @param nombreZona the nombreZona to set
	 */
	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}

	/**
	 * @return the tieneAsiento
	 */
	public Boolean getTieneAsiento() {
		return tieneAsiento;
	}

	/**
	 * @param tieneAsiento the tieneAsiento to set
	 */
	public void setTieneAsiento(Boolean tieneAsiento) {
		this.tieneAsiento = tieneAsiento;
	}

	/**
	 * @return the nombreSede
	 */
	public String getNombreSede() {
		return nombreSede;
	}

	/**
	 * @param nombreSede the nombreSede to set
	 */
	public void setNombreSede(String nombreSede) {
		this.nombreSede = nombreSede;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idZona, nombreZona, tieneAsiento, nombreSede);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZonaDTO other = (ZonaDTO) obj;
		return Objects.equals(idZona, other.idZona) && Objects.equals(nombreZona, other.nombreZona)
				&& Objects.equals(tieneAsiento, other.tieneAsiento) && Objects.equals(nombreSede, other.nombreSede);
	}

	@Override
	public String toString() {
		return "ZonaDTO [idZona=" + idZona + ", nombreZona=" + nombreZona + ", tieneAsiento=" + tieneAsiento
				+ ", nombreSede=" + nombreSede + "]";
	}
}