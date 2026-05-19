package co.edu.unbosque.queboleteo.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ZonaConciertoDTO {

    private Long idPrecio;
    private BigDecimal precio;
    private Integer cantidadDisponible;

    private Long idZona;

    private Long idConcierto;

    public ZonaConciertoDTO() {
    }

    /**
     * Constructor
     * 
     * @param precio
     * @param cantidadDisponible
     * @param idZona
     * @param idConcierto
     */
    public ZonaConciertoDTO(BigDecimal precio, Integer cantidadDisponible, Long idZona, Long idConcierto) {
        super();
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible;
        this.idZona = idZona;
        this.idConcierto = idConcierto;
    }

    /**
     * @return the idPrecio
     */
    public Long getIdPrecio() { return idPrecio; }

    /**
     * @param idPrecio the idPrecio to set
     */
    public void setIdPrecio(Long idPrecio) { this.idPrecio = idPrecio; }

    /**
     * @return the precio
     */
    public BigDecimal getPrecio() { return precio; }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    /**
     * @return the cantidadDisponible
     */
    public Integer getCantidadDisponible() { return cantidadDisponible; }

    /**
     * @param cantidadDisponible the cantidadDisponible to set
     */
    public void setCantidadDisponible(Integer cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }

    /**
     * @return the idZona
     */
    public Long getIdZona() { return idZona; }

    /**
     * @param idZona the idZona to set
     */
    public void setIdZona(Long idZona) { this.idZona = idZona; }

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
        return Objects.hash(idPrecio, precio, cantidadDisponible, idZona, idConcierto);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ZonaConciertoDTO other = (ZonaConciertoDTO) obj;
        return Objects.equals(idPrecio, other.idPrecio)
                && Objects.equals(precio, other.precio)
                && Objects.equals(cantidadDisponible, other.cantidadDisponible)
                && Objects.equals(idZona, other.idZona)
                && Objects.equals(idConcierto, other.idConcierto);
    }

    @Override
    public String toString() {
        return "ZonaConciertoDTO [idPrecio=" + idPrecio + ", precio=" + precio
                + ", cantidadDisponible=" + cantidadDisponible
                + ", idZona=" + idZona + ", idConcierto=" + idConcierto + "]";
    }
}