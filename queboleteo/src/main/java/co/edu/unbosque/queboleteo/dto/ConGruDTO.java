package co.edu.unbosque.queboleteo.dto;

import java.util.Objects;

public class ConGruDTO {

    private Long idConcierto;
    private Long idGrupo;

    public ConGruDTO() {
    }

    /**
     * Constructor
     * 
     * @param idConcierto
     * @param idGrupo
     */
    public ConGruDTO(Long idConcierto, Long idGrupo) {
        super();
        this.idConcierto = idConcierto;
        this.idGrupo = idGrupo;
    }

    /**
     * @return the idConcierto
     */
	public Long getIdConcierto() {
		return idConcierto; }

    /**
     * @param idConcierto the idConcierto to set
     */
    public void setIdConcierto(Long idConcierto) { this.idConcierto = idConcierto; }

    /**
     * @return the idGrupo
     */
    public Long getIdGrupo() { return idGrupo; }

    /**
     * @param idGrupo the idGrupo to set
     */
    public void setIdGrupo(Long idGrupo) { this.idGrupo = idGrupo; }

    @Override
    public int hashCode() {
        return Objects.hash(idConcierto, idGrupo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ConGruDTO other = (ConGruDTO) obj;
        return Objects.equals(idConcierto, other.idConcierto)
                && Objects.equals(idGrupo, other.idGrupo);
    }

    @Override
    public String toString() {
        return "ConGruDTO [idConcierto=" + idConcierto + ", idGrupo=" + idGrupo + "]";
    }
}