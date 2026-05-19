package co.edu.unbosque.queboleteo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CONCIERTO")
public class Concierto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdConcierto")
	private Long idConcierto;

	@Column(name = "NombreConcierto", length = 50)
	private String nombreConcierto;

	@Column(name = "DescripcionConcierto", length = 500)
	private String descripcionConcierto;

	@Column(name = "ImagenConcierto", length = 2000)
	private String imagenConcierto;

	@Column(name = "EdadMinima")
	private Integer edadMinima;

	@Column(name = "Recomendacion", length = 350)
	private String recomendacion;

	@Column(name = "FechaConcierto")
	private LocalDateTime fechaConcierto;

	@Column(name = "EstadoConcierto", length = 20)
	private String estadoConcierto;

	@ManyToOne
	@JoinColumn(name = "TOUR_IdTour")
	private Tour tour;

	@ManyToOne
	@JoinColumn(name = "SEDE_NombreSede", nullable = false)
	private Sede sede;

	public Concierto() {
	}

	/**
	 * @param nombreConcierto
	 * @param descripcionConcierto
	 * @param imagenConcierto
	 * @param edadMinima
	 * @param recomendacion
	 * @param fechaConcierto
	 * @param estadoConcierto
	 * @param tour
	 * @param sede
	 */
	public Concierto(String nombreConcierto, String descripcionConcierto, String imagenConcierto, Integer edadMinima,
			String recomendacion, LocalDateTime fechaConcierto, String estadoConcierto, Tour tour, Sede sede) {
		super();
		this.nombreConcierto = nombreConcierto;
		this.descripcionConcierto = descripcionConcierto;
		this.imagenConcierto = imagenConcierto;
		this.edadMinima = edadMinima;
		this.recomendacion = recomendacion;
		this.fechaConcierto = fechaConcierto;
		this.estadoConcierto = estadoConcierto;
		this.tour = tour;
		this.sede = sede;
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
	 * @return the nombreConcierto
	 */
	public String getNombreConcierto() {
		return nombreConcierto;
	}

	/**
	 * @param nombreConcierto the nombreConcierto to set
	 */
	public void setNombreConcierto(String nombreConcierto) {
		this.nombreConcierto = nombreConcierto;
	}

	/**
	 * @return the descripcionConcierto
	 */
	public String getDescripcionConcierto() {
		return descripcionConcierto;
	}

	/**
	 * @param descripcionConcierto the descripcionConcierto to set
	 */
	public void setDescripcionConcierto(String descripcionConcierto) {
		this.descripcionConcierto = descripcionConcierto;
	}

	/**
	 * @return the imagenConcierto
	 */
	public String getImagenConcierto() {
		return imagenConcierto;
	}

	/**
	 * @param imagenConcierto the imagenConcierto to set
	 */
	public void setImagenConcierto(String imagenConcierto) {
		this.imagenConcierto = imagenConcierto;
	}

	/**
	 * @return the edadMinima
	 */
	public Integer getEdadMinima() {
		return edadMinima;
	}

	/**
	 * @param edadMinima the edadMinima to set
	 */
	public void setEdadMinima(Integer edadMinima) {
		this.edadMinima = edadMinima;
	}

	/**
	 * @return the recomendacion
	 */
	public String getRecomendacion() {
		return recomendacion;
	}

	/**
	 * @param recomendacion the recomendacion to set
	 */
	public void setRecomendacion(String recomendacion) {
		this.recomendacion = recomendacion;
	}

	/**
	 * @return the fechaConcierto
	 */
	public LocalDateTime getFechaConcierto() {
		return fechaConcierto;
	}

	/**
	 * @param fechaConcierto the fechaConcierto to set
	 */
	public void setFechaConcierto(LocalDateTime fechaConcierto) {
		this.fechaConcierto = fechaConcierto;
	}

	/**
	 * @return the estadoConcierto
	 */
	public String getEstadoConcierto() {
		return estadoConcierto;
	}

	/**
	 * @param estadoConcierto the estadoConcierto to set
	 */
	public void setEstadoConcierto(String estadoConcierto) {
		this.estadoConcierto = estadoConcierto;
	}

	/**
	 * @return the tour
	 */
	public Tour getTour() {
		return tour;
	}

	/**
	 * @param tour the tour to set
	 */
	public void setTour(Tour tour) {
		this.tour = tour;
	}

	/**
	 * @return the sede
	 */
	public Sede getSede() {
		return sede;
	}

	/**
	 * @param sede the sede to set
	 */
	public void setSede(Sede sede) {
		this.sede = sede;
	}

}
