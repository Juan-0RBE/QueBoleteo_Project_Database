package co.edu.unbosque.queboleteo.dto;

public class GrupoDTO {

	private Long idGrupo;
	private String nombreGrupo;
	private String descripcionGrupo;
	private String imagenGrupo;
	private String paisOrigenGrupo;
	private Integer tiempoDuracion;
	private String lenguajeGrupo;

	public GrupoDTO() {
	}

	/**
	 * @param nombreGrupo
	 * @param descripcionGrupo
	 * @param imagenGrupo
	 * @param paisOrigenGrupo
	 * @param tiempoDuracion
	 * @param lenguajeGrupo
	 */
	public GrupoDTO(String nombreGrupo, String descripcionGrupo, String imagenGrupo, String paisOrigenGrupo,
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