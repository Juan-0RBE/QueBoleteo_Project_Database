package co.edu.unbosque.queboleteo.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ZONA_CONCIERTO")
public class ZonaConcierto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdPrecio")
	private Long idPrecio;

	@Column(name = "Precio", precision = 28, scale = 2)
	private BigDecimal precio;

	@Column(name = "CantidadDisponible")
	private Integer cantidadDisponible;

	@ManyToOne
	@JoinColumn(name = "ZONA_IdZona", nullable = false)
	private Zona zona;

	@ManyToOne
	@JoinColumn(name = "CONCIERTO_IdConcierto", nullable = false)
	private Concierto concierto;

	public ZonaConcierto() {
	}

	/**
	 * Constructor
	 * 
	 * 
	 * @param precio
	 * @param cantidadDisponible
	 * @param zona
	 * @param concierto
	 */
	public ZonaConcierto(BigDecimal precio, Integer cantidadDisponible, Zona zona, Concierto concierto) {
		super();
		this.precio = precio;
		this.cantidadDisponible = cantidadDisponible;
		this.zona = zona;
		this.concierto = concierto;
	}

	/**
	 * @return the idPrecio
	 */
	public Long getIdPrecio() {
		return idPrecio;
	}

	/**
	 * @param idPrecio the idPrecio to set
	 */
	public void setIdPrecio(Long idPrecio) {
		this.idPrecio = idPrecio;
	}

	/**
	 * @return the precio
	 */
	public BigDecimal getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	/**
	 * @return the cantidadDisponible
	 */
	public Integer getCantidadDisponible() {
		return cantidadDisponible;
	}

	/**
	 * @param cantidadDisponible the cantidadDisponible to set
	 */
	public void setCantidadDisponible(Integer cantidadDisponible) {
		this.cantidadDisponible = cantidadDisponible;
	}

	/**
	 * @return the zona
	 */
	public Zona getZona() {
		return zona;
	}

	/**
	 * @param zona the zona to set
	 */
	public void setZona(Zona zona) {
		this.zona = zona;
	}

	/**
	 * @return the concierto
	 */
	public Concierto getConcierto() {
		return concierto;
	}

	/**
	 * @param concierto the concierto to set
	 */
	public void setConcierto(Concierto concierto) {
		this.concierto = concierto;
	}

}
