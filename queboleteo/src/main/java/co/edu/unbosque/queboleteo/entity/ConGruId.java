package co.edu.unbosque.queboleteo.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ConGruId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CONCIERTO_IdConcierto")
	private Long conciertoIdConcierto;

	@Column(name = "GRUPO_IdGrupo")
	private Long grupoIdGrupo;

	public ConGruId() {
	}

	/**
	 * Constructor
	 * 
	 * @param conciertoIdConcierto
	 * @param grupoIdGrupo
	 */
	public ConGruId(Long conciertoIdConcierto, Long grupoIdGrupo) {
		this.conciertoIdConcierto = conciertoIdConcierto;
		this.grupoIdGrupo = grupoIdGrupo;
	}

	/**
	 * 
	 * @return
	 */
	public Long getConciertoIdConcierto() {
		return conciertoIdConcierto;
	}

	/**
	 * 
	 * @param conciertoIdConcierto
	 */
	public void setConciertoIdConcierto(Long conciertoIdConcierto) {
		this.conciertoIdConcierto = conciertoIdConcierto;
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ConGruId that = (ConGruId) o;
		return java.util.Objects.equals(conciertoIdConcierto, that.conciertoIdConcierto)
				&& java.util.Objects.equals(grupoIdGrupo, that.grupoIdGrupo);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(conciertoIdConcierto, grupoIdGrupo);
	}
}