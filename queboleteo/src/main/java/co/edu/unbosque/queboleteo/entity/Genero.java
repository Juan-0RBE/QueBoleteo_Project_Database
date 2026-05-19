package co.edu.unbosque.queboleteo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "GENERO")
public class Genero {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdGenero")
	private Long idGenero;

	@Column(name = "NombreGenero", length = 50)
	private String nombreGenero;

	public Genero() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * 
	 * 
	 * @param nombreGenero
	 */
	public Genero(String nombreGenero) {
		super();
		this.nombreGenero = nombreGenero;
	}

	/**
	 * @return the idGenero
	 */
	public Long getIdGenero() {
		return idGenero;
	}

	/**
	 * @param idGenero the idGenero to set
	 */
	public void setIdGenero(Long idGenero) {
		this.idGenero = idGenero;
	}

	/**
	 * @return the nombreGenero
	 */
	public String getNombreGenero() {
		return nombreGenero;
	}

	/**
	 * @param nombreGenero the nombreGenero to set
	 */
	public void setNombreGenero(String nombreGenero) {
		this.nombreGenero = nombreGenero;
	}

}
