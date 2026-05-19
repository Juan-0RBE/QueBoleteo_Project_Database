package co.edu.unbosque.queboleteo.dto;

public class SedeDTO {

	private String nombreSede;
	private String calle;
	private String carrera;
	private String ciudad;
	private Boolean tieneAccesibilidad;
	private String imagenSede;
	private String imagenSeccion;

	public SedeDTO() {
	}

	/**
	 * Constructor
	 * 
	 * @param nombreSede
	 * @param calle
	 * @param carrera
	 * @param ciudad
	 * @param tieneAccesibilidad
	 * @param imagenSede
	 * @param imagenSeccion
	 */
	public SedeDTO(String nombreSede, String calle, String carrera,
			String ciudad, Boolean tieneAccesibilidad,
			String imagenSede, String imagenSeccion) {

		this.nombreSede = nombreSede;
		this.calle = calle;
		this.carrera = carrera;
		this.ciudad = ciudad;
		this.tieneAccesibilidad = tieneAccesibilidad;
		this.imagenSede = imagenSede;
		this.imagenSeccion = imagenSeccion;
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

	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}

	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}

	/**
	 * @return the carrera
	 */
	public String getCarrera() {
		return carrera;
	}

	/**
	 * @param carrera the carrera to set
	 */
	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * @return the tieneAccesibilidad
	 */
	public Boolean getTieneAccesibilidad() {
		return tieneAccesibilidad;
	}

	/**
	 * @param tieneAccesibilidad the tieneAccesibilidad to set
	 */
	public void setTieneAccesibilidad(Boolean tieneAccesibilidad) {
		this.tieneAccesibilidad = tieneAccesibilidad;
	}

	/**
	 * @return the imagenSede
	 */
	public String getImagenSede() {
		return imagenSede;
	}

	/**
	 * @param imagenSede the imagenSede to set
	 */
	public void setImagenSede(String imagenSede) {
		this.imagenSede = imagenSede;
	}

	/**
	 * @return the imagenSeccion
	 */
	public String getImagenSeccion() {
		return imagenSeccion;
	}

	/**
	 * @param imagenSeccion the imagenSeccion to set
	 */
	public void setImagenSeccion(String imagenSeccion) {
		this.imagenSeccion = imagenSeccion;
	}

}