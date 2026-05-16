package co.edu.unbosque.queboleteo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "BOLETO")
public class Boleto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CodigoBoleto")
	private Long codigoBoleto;

	@Column(name = "EstadoBoleto", length = 15)
	private String estadoBoleto;

	// Lado inverso — la FK BOLETO_CodigoBoleto vive en la tabla LUGAR
	@OneToOne(mappedBy = "boleto")
	private Lugar lugar;

	@ManyToOne
	@JoinColumn(name = "ZONA_CONCIERTO_IdPrecio", nullable = false)
	private ZonaConcierto zonaConcierto;

	@ManyToOne
	@JoinColumn(name = "VENTA_IdVenta", nullable = false)
	private Venta venta;

	public Boleto() {
	}

	/**
	 * @param estadoBoleto
	 * @param zonaConcierto
	 * @param venta
	 */
	public Boleto(String estadoBoleto, ZonaConcierto zonaConcierto, Venta venta) {
		this.estadoBoleto = estadoBoleto;
		this.zonaConcierto = zonaConcierto;
		this.venta = venta;
	}

	/**
	 * @return the codigoBoleto
	 */
	public Long getCodigoBoleto() {
		return codigoBoleto;
	}

	/**
	 * @param codigoBoleto the codigoBoleto to set
	 */
	public void setCodigoBoleto(Long codigoBoleto) {
		this.codigoBoleto = codigoBoleto;
	}

	/**
	 * @return the estadoBoleto
	 */
	public String getEstadoBoleto() {
		return estadoBoleto;
	}

	/**
	 * @param estadoBoleto the estadoBoleto to set
	 */
	public void setEstadoBoleto(String estadoBoleto) {
		this.estadoBoleto = estadoBoleto;
	}

	/**
	 * @return the lugar
	 */
	public Lugar getLugar() {
		return lugar;
	}

	/**
	 * @param lugar the lugar to set
	 */
	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

	/**
	 * @return the zonaConcierto
	 */
	public ZonaConcierto getZonaConcierto() {
		return zonaConcierto;
	}

	/**
	 * @param zonaConcierto the zonaConcierto to set
	 */
	public void setZonaConcierto(ZonaConcierto zonaConcierto) {
		this.zonaConcierto = zonaConcierto;
	}

	/**
	 * @return the venta
	 */
	public Venta getVenta() {
		return venta;
	}

	/**
	 * @param venta the venta to set
	 */
	public void setVenta(Venta venta) {
		this.venta = venta;
	}

}