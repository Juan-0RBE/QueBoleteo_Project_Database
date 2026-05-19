package co.edu.unbosque.queboleteo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "GRUPO")
public class Grupo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdGrupo")
	private Long idGrupo;

	@Column(name = "NombreGrupo", length = 100)
	private String nombreGrupo;

	@Column(name = "DescripcionGrupo", length = 500)
	private String descripcionGrupo;

	@Column(name = "ImagenGrupo", length = 2000)
	private String imagenGrupo;

	@Column(name = "PaisOrigenGrupo", length = 100)
	private String paisOrigenGrupo;

	@Column(name = "TiempoDuracion")
	private Integer tiempoDuracion;

	@Column(name = "LenguajeGrupo", length = 50)
	private String lenguajeGrupo;

	public Grupo() {
	}

	/**
	 * Constructor
	 * 
	 * @param nombreGrupo
	 * @param descripcionGrupo
	 * @param imagenGrupo
	 * @param paisOrigenGrupo
	 * @param tiempoDuracion
	 * @param lenguajeGrupo
	 */
	public Grupo(String nombreGrupo, String descripcionGrupo, String imagenGrupo, String paisOrigenGrupo,
			Integer tiempoDuracion, String lenguajeGrupo) {
		super();
		this.nombreGrupo = nombreGrupo;
		this.descripcionGrupo = descripcionGrupo;
		this.imagenGrupo = imagenGrupo;
		this.paisOrigenGrupo = paisOrigenGrupo;
		this.tiempoDuracion = tiempoDuracion;
		this.lenguajeGrupo = lenguajeGrupo;
	}

	/**
	 * @return the idGrupo
	 */
	public Long getIdGrupo() {
		return idGrupo;
	}

	/**
	 * @param idGrupo the idGrupo to set
	 */
	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	/**
	 * @return the nombreGrupo
	 */
	public String getNombreGrupo() {
		return nombreGrupo;
	}

	/**
	 * @param nombreGrupo the nombreGrupo to set
	 */
	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	/**
	 * @return the descripcionGrupo
	 */
	public String getDescripcionGrupo() {
		return descripcionGrupo;
	}

	/**
	 * @param descripcionGrupo the descripcionGrupo to set
	 */
	public void setDescripcionGrupo(String descripcionGrupo) {
		this.descripcionGrupo = descripcionGrupo;
	}

	/**
	 * @return the imagenGrupo
	 */
	public String getImagenGrupo() {
		return imagenGrupo;
	}

	/**
	 * @param imagenGrupo the imagenGrupo to set
	 */
	public void setImagenGrupo(String imagenGrupo) {
		this.imagenGrupo = imagenGrupo;
	}

	/**
	 * @return the paisOrigenGrupo
	 */
	public String getPaisOrigenGrupo() {
		return paisOrigenGrupo;
	}

	/**
	 * @param paisOrigenGrupo the paisOrigenGrupo to set
	 */
	public void setPaisOrigenGrupo(String paisOrigenGrupo) {
		this.paisOrigenGrupo = paisOrigenGrupo;
	}

	/**
	 * @return the tiempoDuracion
	 */
	public Integer getTiempoDuracion() {
		return tiempoDuracion;
	}

	/**
	 * @param tiempoDuracion the tiempoDuracion to set
	 */
	public void setTiempoDuracion(Integer tiempoDuracion) {
		this.tiempoDuracion = tiempoDuracion;
	}

	/**
	 * @return the lenguajeGrupo
	 */
	public String getLenguajeGrupo() {
		return lenguajeGrupo;
	}

	/**
	 * @param lenguajeGrupo the lenguajeGrupo to set
	 */
	public void setLenguajeGrupo(String lenguajeGrupo) {
		this.lenguajeGrupo = lenguajeGrupo;
	}

}
