package co.edu.unbosque.queboleteo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ARTISTA_INDIVIDUAL")
public class ArtistaIndividual {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdArtista")
	private Long idArtista;

	@Column(name = "NombreArtista", length = 100)
	private String nombreArtista;

	@Column(name = "DescripcionArtista", length = 200)
	private String descripcionArtista;

	@Column(name = "ImagenArtista", length = 2000)
	private String imagenArtista;

	@Column(name = "PaisOrigenArtista", length = 50)
	private String paisOrigenArtista;

	@Column(name = "EdadArtista")
	private Integer edadArtista;

	@Column(name = "LenguajeArtista", length = 50)
	private String lenguajeArtista;

	public ArtistaIndividual() {
	}

	/**
	 * @param nombreArtista
	 * @param descripcionArtista
	 * @param imagenArtista
	 * @param paisOrigenArtista
	 * @param edadArtista
	 * @param lenguajeArtista
	 */
	public ArtistaIndividual(String nombreArtista, String descripcionArtista, String imagenArtista,
			String paisOrigenArtista, Integer edadArtista, String lenguajeArtista) {
		super();
		this.nombreArtista = nombreArtista;
		this.descripcionArtista = descripcionArtista;
		this.imagenArtista = imagenArtista;
		this.paisOrigenArtista = paisOrigenArtista;
		this.edadArtista = edadArtista;
		this.lenguajeArtista = lenguajeArtista;
	}

	/**
	 * @return the idArtista
	 */
	public Long getIdArtista() {
		return idArtista;
	}

	/**
	 * @param idArtista the idArtista to set
	 */
	public void setIdArtista(Long idArtista) {
		this.idArtista = idArtista;
	}

	/**
	 * @return the nombreArtista
	 */
	public String getNombreArtista() {
		return nombreArtista;
	}

	/**
	 * @param nombreArtista the nombreArtista to set
	 */
	public void setNombreArtista(String nombreArtista) {
		this.nombreArtista = nombreArtista;
	}

	/**
	 * @return the descripcionArtista
	 */
	public String getDescripcionArtista() {
		return descripcionArtista;
	}

	/**
	 * @param descripcionArtista the descripcionArtista to set
	 */
	public void setDescripcionArtista(String descripcionArtista) {
		this.descripcionArtista = descripcionArtista;
	}

	/**
	 * @return the imagenArtista
	 */
	public String getImagenArtista() {
		return imagenArtista;
	}

	/**
	 * @param imagenArtista the imagenArtista to set
	 */
	public void setImagenArtista(String imagenArtista) {
		this.imagenArtista = imagenArtista;
	}

	/**
	 * @return the paisOrigenArtista
	 */
	public String getPaisOrigenArtista() {
		return paisOrigenArtista;
	}

	/**
	 * @param paisOrigenArtista the paisOrigenArtista to set
	 */
	public void setPaisOrigenArtista(String paisOrigenArtista) {
		this.paisOrigenArtista = paisOrigenArtista;
	}

	/**
	 * @return the edadArtista
	 */
	public Integer getEdadArtista() {
		return edadArtista;
	}

	/**
	 * @param edadArtista the edadArtista to set
	 */
	public void setEdadArtista(Integer edadArtista) {
		this.edadArtista = edadArtista;
	}

	/**
	 * @return the lenguajeArtista
	 */
	public String getLenguajeArtista() {
		return lenguajeArtista;
	}

	/**
	 * @param lenguajeArtista the lenguajeArtista to set
	 */
	public void setLenguajeArtista(String lenguajeArtista) {
		this.lenguajeArtista = lenguajeArtista;
	}

}
