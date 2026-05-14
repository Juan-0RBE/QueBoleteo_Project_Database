package co.edu.unbosque.queboleteo.dto;

import java.util.Objects;

public class GruArtDTO {

	private Long idGrupo;
	private Long idArtista;
	private String Rol;

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
		Rol = rol;
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
		return Rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(String rol) {
		Rol = rol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Rol, idArtista, idGrupo);
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
		return Objects.equals(Rol, other.Rol) && Objects.equals(idArtista, other.idArtista)
				&& Objects.equals(idGrupo, other.idGrupo);
	}

}
