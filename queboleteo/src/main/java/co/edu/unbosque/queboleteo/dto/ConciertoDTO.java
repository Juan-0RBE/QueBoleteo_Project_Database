package co.edu.unbosque.queboleteo.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ConciertoDTO {

	private Long idConcierto;
	private String nombreConcierto;
	private String descripcionConcierto;
	private String imagenConcierto;
	private Integer edadMinima;
	private String recomendacion;
	private LocalDateTime fechaConcierto;
	private String estadoConcierto;

	private Long idTour;
	private String nombreSede;

	public ConciertoDTO() {
	}

	/**
	 * @param nombreConcierto
	 * @param descripcionConcierto
	 * @param imagenConcierto
	 * @param edadMinima
	 * @param recomendacion
	 * @param fechaConcierto
	 * @param estadoConcierto
	 * @param idTour
	 * @param nombreSede
	 */
	public ConciertoDTO(String nombreConcierto, String descripcionConcierto, String imagenConcierto, Integer edadMinima,
			String recomendacion, LocalDateTime fechaConcierto, String estadoConcierto, Long idTour,
			String nombreSede) {
		super();
		this.nombreConcierto = nombreConcierto;
		this.descripcionConcierto = descripcionConcierto;
		this.imagenConcierto = imagenConcierto;
		this.edadMinima = edadMinima;
		this.recomendacion = recomendacion;
		this.fechaConcierto = fechaConcierto;
		this.estadoConcierto = estadoConcierto;
		this.idTour = idTour;
		this.nombreSede = nombreSede;
	}

	public Long getIdConcierto() {
		return idConcierto;
	}

	public void setIdConcierto(Long idConcierto) {
		this.idConcierto = idConcierto;
	}

	public String getNombreConcierto() {
		return nombreConcierto;
	}

	public void setNombreConcierto(String nombreConcierto) {
		this.nombreConcierto = nombreConcierto;
	}

	public String getDescripcionConcierto() {
		return descripcionConcierto;
	}

	public void setDescripcionConcierto(String descripcionConcierto) {
		this.descripcionConcierto = descripcionConcierto;
	}

	public String getImagenConcierto() {
		return imagenConcierto;
	}

	public void setImagenConcierto(String imagenConcierto) {
		this.imagenConcierto = imagenConcierto;
	}

	public Integer getEdadMinima() {
		return edadMinima;
	}

	public void setEdadMinima(Integer edadMinima) {
		this.edadMinima = edadMinima;
	}

	public String getRecomendacion() {
		return recomendacion;
	}

	public void setRecomendacion(String recomendacion) {
		this.recomendacion = recomendacion;
	}

	public LocalDateTime getFechaConcierto() {
		return fechaConcierto;
	}

	public void setFechaConcierto(LocalDateTime fechaConcierto) {
		this.fechaConcierto = fechaConcierto;
	}

	public String getEstadoConcierto() {
		return estadoConcierto;
	}

	public void setEstadoConcierto(String estadoConcierto) {
		this.estadoConcierto = estadoConcierto;
	}

	public Long getIdTour() {
		return idTour;
	}

	public void setIdTour(Long idTour) {
		this.idTour = idTour;
	}

	public String getNombreSede() {
		return nombreSede;
	}

	public void setNombreSede(String nombreSede) {
		this.nombreSede = nombreSede;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idConcierto, nombreConcierto, descripcionConcierto, imagenConcierto, edadMinima,
				recomendacion, fechaConcierto, estadoConcierto, idTour, nombreSede);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConciertoDTO other = (ConciertoDTO) obj;
		return Objects.equals(idConcierto, other.idConcierto) && Objects.equals(nombreConcierto, other.nombreConcierto)
				&& Objects.equals(descripcionConcierto, other.descripcionConcierto)
				&& Objects.equals(imagenConcierto, other.imagenConcierto)
				&& Objects.equals(edadMinima, other.edadMinima) && Objects.equals(recomendacion, other.recomendacion)
				&& Objects.equals(fechaConcierto, other.fechaConcierto)
				&& Objects.equals(estadoConcierto, other.estadoConcierto) && Objects.equals(idTour, other.idTour)
				&& Objects.equals(nombreSede, other.nombreSede);
	}

	@Override
	public String toString() {
		return "ConciertoDTO [idConcierto=" + idConcierto + ", nombreConcierto=" + nombreConcierto
				+ ", descripcionConcierto=" + descripcionConcierto + ", imagenConcierto=" + imagenConcierto
				+ ", edadMinima=" + edadMinima + ", recomendacion=" + recomendacion + ", fechaConcierto="
				+ fechaConcierto + ", estadoConcierto=" + estadoConcierto + ", idTour=" + idTour + ", nombreSede="
				+ nombreSede + "]";
	}
}