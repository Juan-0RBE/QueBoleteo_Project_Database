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
@Table(name = "LUGAR")
public class Lugar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdLugar")
	private Long idLugar;

	@Column(name = "NumeroAsiento")
	private Integer numeroAsiento;

	@Column(name = "Fila", length = 10)
	private String fila;

	@OneToOne(mappedBy = "lugar")
	private Boleto boleto;

	@ManyToOne
	@JoinColumn(name = "ZONA_IdZona", nullable = false)
	private Zona zona;

	public Lugar() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param numeroAsiento
	 * @param fila
	 * @param boleto
	 * @param zona
	 */
	public Lugar(Integer numeroAsiento, String fila, Boleto boleto, Zona zona) {
		super();
		this.numeroAsiento = numeroAsiento;
		this.fila = fila;
		this.boleto = boleto;
		this.zona = zona;
	}

	/**
	 * @return the idLugar
	 */
	public Long getIdLugar() {
		return idLugar;
	}

	/**
	 * @param idLugar the idLugar to set
	 */
	public void setIdLugar(Long idLugar) {
		this.idLugar = idLugar;
	}

	/**
	 * @return the numeroAsiento
	 */
	public Integer getNumeroAsiento() {
		return numeroAsiento;
	}

	/**
	 * @param numeroAsiento the numeroAsiento to set
	 */
	public void setNumeroAsiento(Integer numeroAsiento) {
		this.numeroAsiento = numeroAsiento;
	}

	/**
	 * @return the fila
	 */
	public String getFila() {
		return fila;
	}

	/**
	 * @param fila the fila to set
	 */
	public void setFila(String fila) {
		this.fila = fila;
	}

	/**
	 * @return the boleto
	 */
	public Boleto getBoleto() {
		return boleto;
	}

	/**
	 * @param boleto the boleto to set
	 */
	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
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

}
