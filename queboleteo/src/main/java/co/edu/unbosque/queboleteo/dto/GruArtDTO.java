package co.edu.unbosque.queboleteo.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GruArtDTO {

    @JsonProperty("idGrupo")
    private Long idGrupo;

    @JsonProperty("idArtista")
    private Long idArtista;

    @JsonProperty("rol")
    private String rol;
	public GruArtDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param idGrupo
	 * @param idArtista
	 * @param rol
	 */
	public GruArtDTO(Long idGrupo, Long idArtista, String rol) {
		super();
		this.idGrupo = idGrupo;
		this.idArtista = idArtista;
		this.rol = rol;
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
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idArtista, idGrupo, rol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GruArtDTO other = (GruArtDTO) obj;
		return Objects.equals(idArtista, other.idArtista) && Objects.equals(idGrupo, other.idGrupo)
				&& Objects.equals(rol, other.rol);
	}

	@Override
	public String toString() {
		return "GruArtDTO [idGrupo=" + idGrupo + ", idArtista=" + idArtista + ", rol=" + rol + "]";
	}
	
	

}
