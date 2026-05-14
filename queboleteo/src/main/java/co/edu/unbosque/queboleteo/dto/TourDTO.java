package co.edu.unbosque.queboleteo.dto;

import java.time.LocalDate;

public class TourDTO {

	private Long idTour;
	private String nombreTour;
	private String descripcionTour;
	private String imagenTour;
	private LocalDate fechaInicial;
	private LocalDate fechaFinal;

	public TourDTO() {
	}

	/**
	 * @param nombreTour
	 * @param descripcionTour
	 * @param imagenTour
	 * @param fechaInicial
	 * @param fechaFinal
	 */
	public TourDTO(String nombreTour,
			String descripcionTour,
			String imagenTour,
			LocalDate fechaInicial,
			LocalDate fechaFinal) {

		this.nombreTour = nombreTour;
		this.descripcionTour = descripcionTour;
		this.imagenTour = imagenTour;
		this.fechaInicial = fechaInicial;
		this.fechaFinal = fechaFinal;
	}

	/**
	 * @return the idTour
	 */
	public Long getIdTour() {
		return idTour;
	}

	/**
	 * @param idTour the idTour to set
	 */
	public void setIdTour(Long idTour) {
		this.idTour = idTour;
	}

	/**
	 * @return the nombreTour
	 */
	public String getNombreTour() {
		return nombreTour;
	}

	/**
	 * @param nombreTour the nombreTour to set
	 */
	public void setNombreTour(String nombreTour) {
		this.nombreTour = nombreTour;
	}

	/**
	 * @return the descripcionTour
	 */
	public String getDescripcionTour() {
		return descripcionTour;
	}

	/**
	 * @param descripcionTour the descripcionTour to set
	 */
	public void setDescripcionTour(String descripcionTour) {
		this.descripcionTour = descripcionTour;
	}

	/**
	 * @return the imagenTour
	 */
	public String getImagenTour() {
		return imagenTour;
	}

	/**
	 * @param imagenTour the imagenTour to set
	 */
	public void setImagenTour(String imagenTour) {
		this.imagenTour = imagenTour;
	}

	/**
	 * @return the fechaInicial
	 */
	public LocalDate getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * @param fechaInicial the fechaInicial to set
	 */
	public void setFechaInicial(LocalDate fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * @return the fechaFinal
	 */
	public LocalDate getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * @param fechaFinal the fechaFinal to set
	 */
	public void setFechaFinal(LocalDate fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

}