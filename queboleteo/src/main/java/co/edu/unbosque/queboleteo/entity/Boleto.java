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
@Table(name = "BOLETO")
public class Boleto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CodigoBoleto")
	private Long codigoBoleto;

	@Column(name = "EstadoBoleto", length = 15)
	private String estadoBoleto;

	@ManyToOne
	@JoinColumn(name = "LUGAR_IdLugar", nullable = true)
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
	 * Constructor para zona general
	 * 
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
	 * Constructor para zona con asientos
	 * 
	 * @param estadoBoleto
	 * @param lugar
	 * @param zonaConcierto
	 * @param venta
	 */
	public Boleto(String estadoBoleto, Lugar lugar, ZonaConcierto zonaConcierto, Venta venta) {
		this.estadoBoleto = estadoBoleto;
		this.lugar = lugar;
		this.zonaConcierto = zonaConcierto;
		this.venta = venta;
	}

	public Long getCodigoBoleto() {
		return codigoBoleto;
	}

	public void setCodigoBoleto(Long codigoBoleto) {
		this.codigoBoleto = codigoBoleto;
	}

	public String getEstadoBoleto() {
		return estadoBoleto;
	}

	public void setEstadoBoleto(String estadoBoleto) {
		this.estadoBoleto = estadoBoleto;
	}

	public Lugar getLugar() {
		return lugar;
	}

	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

	public ZonaConcierto getZonaConcierto() {
		return zonaConcierto;
	}

	public void setZonaConcierto(ZonaConcierto zonaConcierto) {
		this.zonaConcierto = zonaConcierto;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}
}