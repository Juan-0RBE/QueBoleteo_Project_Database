package co.edu.unbosque.queboleteo.dto;

import java.util.Objects;

public class GruGenDTO {

	private Long idGrupo;
	private Long idGenero;

	public GruGenDTO() {
	}

	/**
	 * Constructor
	 * 
	 * @param idGrupo
	 * @param idGenero
	 */
	public GruGenDTO(Long idGrupo, Long idGenero) {
		super();
		this.idGrupo = idGrupo;
		this.idGenero = idGenero;
	}

	/**
	 * @return the idGrupo
	 */
	public Long getIdGrupo() {
		return idGrupo;
	}

	/**
	 * @param idGrupo the idGrupo to set
	 */
	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
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

	@Override
	public int hashCode() {
		return Objects.hash(idGrupo, idGenero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GruGenDTO other = (GruGenDTO) obj;
		return Objects.equals(idGrupo, other.idGrupo) && Objects.equals(idGenero, other.idGenero);
	}

	@Override
	public String toString() {
		return "GruGenDTO [idGrupo=" + idGrupo + ", idGenero=" + idGenero + "]";
	}
}