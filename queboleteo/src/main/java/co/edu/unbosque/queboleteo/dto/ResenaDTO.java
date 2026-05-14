package co.edu.unbosque.queboleteo.dto;

import java.util.Objects;

public class ResenaDTO {

    private Long idResena;
    private String comentario;
    private Integer calificacion;

    private String correoUsuario;
    private Long idConcierto;

    public ResenaDTO() {
    }

    /**
     * @param comentario
     * @param calificacion
     * @param correoUsuario
     * @param idConcierto
     */
    public ResenaDTO(String comentario, Integer calificacion, String correoUsuario, Long idConcierto) {
        super();
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.correoUsuario = correoUsuario;
        this.idConcierto = idConcierto;
    }

    /**
     * @return the idResena
     */
    public Long getIdResena() { return idResena; }

    /**
     * @param idResena the idResena to set
     */
    public void setIdResena(Long idResena) { this.idResena = idResena; }

    /**
     * @return the comentario
     */
    public String getComentario() { return comentario; }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) { this.comentario = comentario; }

    /**
     * @return the calificacion
     */
    public Integer getCalificacion() { return calificacion; }

    /**
     * @param calificacion the calificacion to set
     */
    public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }

    /**
     * @return the correoUsuario
     */
    public String getCorreoUsuario() { return correoUsuario; }

    /**
     * @param correoUsuario the correoUsuario to set
     */
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }

    /**
     * @return the idConcierto
     */
    public Long getIdConcierto() { return idConcierto; }

    /**
     * @param idConcierto the idConcierto to set
     */
    public void setIdConcierto(Long idConcierto) { this.idConcierto = idConcierto; }

    @Override
    public int hashCode() {
        return Objects.hash(idResena, comentario, calificacion, correoUsuario, idConcierto);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ResenaDTO other = (ResenaDTO) obj;
        return Objects.equals(idResena, other.idResena)
                && Objects.equals(comentario, other.comentario)
                && Objects.equals(calificacion, other.calificacion)
                && Objects.equals(correoUsuario, other.correoUsuario)
                && Objects.equals(idConcierto, other.idConcierto);
    }

    @Override
    public String toString() {
        return "ResenaDTO [idResena=" + idResena + ", comentario=" + comentario
                + ", calificacion=" + calificacion + ", correoUsuario=" + correoUsuario
                + ", idConcierto=" + idConcierto + "]";
    }
}