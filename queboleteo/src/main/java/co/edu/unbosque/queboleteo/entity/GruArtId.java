package co.edu.unbosque.queboleteo.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class GruArtId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "GRUPO_IdGrupo")
	private Long grupoIdGrupo;

	@Column(name = "ARTISTA_INDIVIDUAL_IdArtista")
	private Long artistaIndividualIdArtista;

	public GruArtId() {
	}

	/**
	 * Constructor
	 * 
	 * @param grupoIdGrupo
	 * @param artistaIndividualIdArtista
	 */
	public GruArtId(Long grupoIdGrupo, Long artistaIndividualIdArtista) {
		this.grupoIdGrupo = grupoIdGrupo;
		this.artistaIndividualIdArtista = artistaIndividualIdArtista;
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
	public Long getArtistaIndividualIdArtista() {
		return artistaIndividualIdArtista;
	}

	/**
	 * 
	 * @param artistaIndividualIdArtista
	 */
	public void setArtistaIndividualIdArtista(Long artistaIndividualIdArtista) {
		this.artistaIndividualIdArtista = artistaIndividualIdArtista;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		GruArtId that = (GruArtId) o;
		return java.util.Objects.equals(grupoIdGrupo, that.grupoIdGrupo)
				&& java.util.Objects.equals(artistaIndividualIdArtista, that.artistaIndividualIdArtista);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(grupoIdGrupo, artistaIndividualIdArtista);
	}
}