package co.edu.unbosque.queboleteo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SEDE")
public class Sede {

	@Id
	@Column(name = "NombreSede", length = 100)
	private String nombreSede;

	@Column(name = "Calle", length = 100)
	private String calle;

	@Column(name = "Carrera", length = 100)
	private String carrera;

	@Column(name = "Ciudad", length = 50)
	private String ciudad;

	@Column(name = "TieneAccesibilidad")
	private Boolean tieneAccesibilidad;

	@Column(name = "ImagenSede", length = 2000)
	private String imagenSede;

	@Column(name = "ImagenSeccion", length = 2000)
	private String imagenSeccion;

	public Sede() {
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
	public Sede(String nombreSede, String calle, String carrera, String ciudad, Boolean tieneAccesibilidad,
			String imagenSede, String imagenSeccion) {
		super();
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
