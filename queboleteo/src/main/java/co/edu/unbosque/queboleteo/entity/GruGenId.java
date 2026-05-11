package co.edu.unbosque.queboleteo.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class GruGenId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "GRUPO_IdGrupo")
	private Long grupoIdGrupo;

	@Column(name = "GENERO_IdGenero")
	private Long generoIdGenero;

	public GruGenId() {
	}

	/**
	 * 
	 * @param grupoIdGrupo
	 * @param generoIdGenero
	 */
	public GruGenId(Long grupoIdGrupo, Long generoIdGenero) {
		this.grupoIdGrupo = grupoIdGrupo;
		this.generoIdGenero = generoIdGenero;
	}

	/**
	 * 
	 * @return
	 */
	public Long getGrupoIdGrupo() {
		return grupoIdGrupo;
	}

	/**
	 * 
	 * @param grupoIdGrupo
	 */
	public void setGrupoIdGrupo(Long grupoIdGrupo) {
		this.grupoIdGrupo = grupoIdGrupo;
	}

	/**
	 * 
	 * @return
	 */
	public Long getGeneroIdGenero() {
		return generoIdGenero;
	}

	/**
	 * 
	 * @param generoIdGenero
	 */
	public void setGeneroIdGenero(Long generoIdGenero) {
		this.generoIdGenero = generoIdGenero;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		GruGenId that = (GruGenId) o;
		return java.util.Objects.equals(grupoIdGrupo, that.grupoIdGrupo)
				&& java.util.Objects.equals(generoIdGenero, that.generoIdGenero);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(grupoIdGrupo, generoIdGenero);
	}
}