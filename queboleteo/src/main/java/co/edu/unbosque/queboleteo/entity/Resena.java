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
@Table(name = "RESENA")
public class Resena {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdResena")
	private Long idResena;

	@Column(name = "Comentario", length = 350)
	private String comentario;

	@Column(name = "Calificacion")
	private Integer calificacion;

	@ManyToOne
	@JoinColumn(name = "USUARIO_Correo", nullable = false)
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "CONCIERTO_IdConcierto", nullable = false)
	private Concierto concierto;

	public Resena() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param comentario
	 * @param calificacion
	 * @param usuario
	 * @param concierto
	 */
	public Resena(String comentario, Integer calificacion, Usuario usuario, Concierto concierto) {
		super();
		this.comentario = comentario;
		this.calificacion = calificacion;
		this.usuario = usuario;
		this.concierto = concierto;
	}

	/**
	 * @return the idResena
	 */
	public Long getIdResena() {
		return idResena;
	}

	/**
	 * @param idResena the idResena to set
	 */
	public void setIdResena(Long idResena) {
		this.idResena = idResena;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * @return the calificacion
	 */
	public Integer getCalificacion() {
		return calificacion;
	}

	/**
	 * @param calificacion the calificacion to set
	 */
	public void setCalificacion(Integer calificacion) {
		this.calificacion = calificacion;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
