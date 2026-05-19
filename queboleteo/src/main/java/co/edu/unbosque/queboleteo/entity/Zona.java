package co.edu.unbosque.queboleteo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ZONA")
public class Zona {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdZona")
	private Long idZona;

	@Column(name = "NombreZona", length = 50)
	private String nombreZona;

	@Column(name = "TieneAsiento")
	private Boolean tieneAsiento;

	@ManyToOne
	@JoinColumn(name = "SEDE_NombreSede", nullable = false)
	private Sede sede;

	public Zona() {
	}

	/**
	 * Constructor
	 * 
	 * 
	 * @param nombreZona
	 * @param tieneAsiento
	 * @param sede
	 */
	public Zona(String nombreZona, Boolean tieneAsiento, Sede sede) {
		super();
		this.nombreZona = nombreZona;
		this.tieneAsiento = tieneAsiento;
		this.sede = sede;
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
