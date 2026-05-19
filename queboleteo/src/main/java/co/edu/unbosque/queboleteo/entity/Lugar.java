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

	@ManyToOne
	@JoinColumn(name = "ZONA_IdZona", nullable = false)
	private Zona zona;

	public Lugar() {
	}

	/**
	 * Constructor
	 * 
	 * @param numeroAsiento
	 * @param fila
	 * @param zona
	 */
	public Lugar(Integer numeroAsiento, String fila, Zona zona) {
		this.numeroAsiento = numeroAsiento;
		this.fila = fila;
		this.zona = zona;
	}

	public Long getIdLugar() {
		return idLugar;
	}

	public void setIdLugar(Long idLugar) {
		this.idLugar = idLugar;
	}

	public Integer getNumeroAsiento() {
		return numeroAsiento;
	}

	public void setNumeroAsiento(Integer numeroAsiento) {
		this.numeroAsiento = numeroAsiento;
	}

	public String getFila() {
		return fila;
	}

	public void setFila(String fila) {
		this.fila = fila;
	}

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}
}