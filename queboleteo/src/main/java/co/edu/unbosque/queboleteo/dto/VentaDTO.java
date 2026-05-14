package co.edu.unbosque.queboleteo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class VentaDTO {

    private Long idVenta;
    private BigDecimal valorTotal;
    private LocalDateTime fechaVenta;

    private String correoUsuario;

    public VentaDTO() {
    }

    /**
     * @param valorTotal
     * @param fechaVenta
     * @param correoUsuario
     */
    public VentaDTO(BigDecimal valorTotal, LocalDateTime fechaVenta, String correoUsuario) {
        super();
        this.valorTotal = valorTotal;
        this.fechaVenta = fechaVenta;
        this.correoUsuario = correoUsuario;
    }

    /**
     * @return the idVenta
     */
    public Long getIdVenta() { return idVenta; }

    /**
     * @param idVenta the idVenta to set
     */
    public void setIdVenta(Long idVenta) { this.idVenta = idVenta; }

    /**
     * @return the valorTotal
     */
    public BigDecimal getValorTotal() { return valorTotal; }

    /**
     * @param valorTotal the valorTotal to set
     */
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    /**
     * @return the fechaVenta
     */
    public LocalDateTime getFechaVenta() { return fechaVenta; }

    /**
     * @param fechaVenta the fechaVenta to set
     */
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }

    /**
     * @return the correoUsuario
     */
    public String getCorreoUsuario() { return correoUsuario; }

    /**
     * @param correoUsuario the correoUsuario to set
     */
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }

    @Override
    public int hashCode() {
        return Objects.hash(idVenta, valorTotal, fechaVenta, correoUsuario);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        VentaDTO other = (VentaDTO) obj;
        return Objects.equals(idVenta, other.idVenta)
                && Objects.equals(valorTotal, other.valorTotal)
                && Objects.equals(fechaVenta, other.fechaVenta)
                && Objects.equals(correoUsuario, other.correoUsuario);
    }

    @Override
    public String toString() {
        return "VentaDTO [idVenta=" + idVenta + ", valorTotal=" + valorTotal
                + ", fechaVenta=" + fechaVenta + ", correoUsuario=" + correoUsuario + "]";
    }
}