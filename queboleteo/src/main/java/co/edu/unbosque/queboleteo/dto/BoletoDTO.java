package co.edu.unbosque.queboleteo.dto;

import java.util.Objects;

public class BoletoDTO {

    private Long codigoBoleto;
    private String estadoBoleto;

    private Long idLugar;
    private Long idPrecio;
    private Long idVenta;

    public BoletoDTO() {
    }

    /**
     * @param estadoBoleto
     * @param idLugar
     * @param idPrecio
     * @param idVenta
     */
    public BoletoDTO(String estadoBoleto, Long idLugar, Long idPrecio, Long idVenta) {
        super();
        this.estadoBoleto = estadoBoleto;
        this.idLugar = idLugar;
        this.idPrecio = idPrecio;
        this.idVenta = idVenta;
    }

    /**
     * @return the codigoBoleto
     */
    public Long getCodigoBoleto() { return codigoBoleto; }

    /**
     * @param codigoBoleto the codigoBoleto to set
     */
    public void setCodigoBoleto(Long codigoBoleto) { this.codigoBoleto = codigoBoleto; }

    /**
     * @return the estadoBoleto
     */
    public String getEstadoBoleto() { return estadoBoleto; }

    /**
     * @param estadoBoleto the estadoBoleto to set
     */
    public void setEstadoBoleto(String estadoBoleto) { this.estadoBoleto = estadoBoleto; }

    /**
     * @return the idLugar
     */
    public Long getIdLugar() { return idLugar; }

    /**
     * @param idLugar the idLugar to set
     */
    public void setIdLugar(Long idLugar) { this.idLugar = idLugar; }

    /**
     * @return the idPrecio
     */
    public Long getIdPrecio() { return idPrecio; }

    /**
     * @param idPrecio the idPrecio to set
     */
    public void setIdPrecio(Long idPrecio) { this.idPrecio = idPrecio; }

    /**
     * @return the idVenta
     */
    public Long getIdVenta() { return idVenta; }

    /**
     * @param idVenta the idVenta to set
     */
    public void setIdVenta(Long idVenta) { this.idVenta = idVenta; }

    @Override
    public int hashCode() {
        return Objects.hash(codigoBoleto, estadoBoleto, idLugar, idPrecio, idVenta);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BoletoDTO other = (BoletoDTO) obj;
        return Objects.equals(codigoBoleto, other.codigoBoleto)
                && Objects.equals(estadoBoleto, other.estadoBoleto)
                && Objects.equals(idLugar, other.idLugar)
                && Objects.equals(idPrecio, other.idPrecio)
                && Objects.equals(idVenta, other.idVenta);
    }

    @Override
    public String toString() {
        return "BoletoDTO [codigoBoleto=" + codigoBoleto + ", estadoBoleto=" + estadoBoleto
                + ", idLugar=" + idLugar + ", idPrecio=" + idPrecio
                + ", idVenta=" + idVenta + "]";
    }
}