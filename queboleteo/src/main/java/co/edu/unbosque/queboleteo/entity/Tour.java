package co.edu.unbosque.queboleteo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TOUR")
public class Tour {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdTour")
	private Long idTour;

	@Column(name = "NombreTour", length = 100)
	private String nombreTour;

	@Column(name = "DescripcionTour", length = 500)
	private String descripcionTour;

	@Column(name = "ImagenTour", length = 2000)
	private String imagenTour;

	@Column(name = "FechaInicial")
	private LocalDate fechaInicial;

	@Column(name = "FechaFinal")
	private LocalDate fechaFinal;

	public Tour() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param nombreTour
	 * @param descripcionTour
	 * @param imagenTour
	 * @param fechaInicial
	 * @param fechaFinal
	 */
	public Tour(String nombreTour, String descripcionTour, String imagenTour, LocalDate fechaInicial,
			LocalDate fechaFinal) {
		super();
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
