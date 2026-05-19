package co.edu.unbosque.queboleteo.dto;

import java.util.Objects;

public class LugarDTO {

    private Long idLugar;
    private Integer numeroAsiento;
    private String fila;

    private Long idZona;
    private Long codigoBoleto;

    public LugarDTO() {
    }

    /**
     * 
     * Constructor
     * 
     * @param numeroAsiento
     * @param fila
     * @param idZona
     */
    public LugarDTO(Integer numeroAsiento, String fila, Long idZona) {
        super();
        this.numeroAsiento = numeroAsiento;
        this.fila = fila;
        this.idZona = idZona;
    }

    /**
     * @return the idLugar
     */
    public Long getIdLugar() { return idLugar; }

    /**
     * @param idLugar the idLugar to set
     */
    public void setIdLugar(Long idLugar) { this.idLugar = idLugar; }

    /**
     * @return the numeroAsiento
     */
    public Integer getNumeroAsiento() { return numeroAsiento; }

    /**
     * @param numeroAsiento the numeroAsiento to set
     */
    public void setNumeroAsiento(Integer numeroAsiento) { this.numeroAsiento = numeroAsiento; }

    /**
     * @return the fila
     */
    public String getFila() { return fila; }

    /**
     * @param fila the fila to set
     */
    public void setFila(String fila) { this.fila = fila; }

    /**
     * @return the idZona
     */
    public Long getIdZona() { return idZona; }

    /**
     * @param idZona the idZona to set
     */
    public void setIdZona(Long idZona) { this.idZona = idZona; }

    /**
     * @return the codigoBoleto
     */
    public Long getCodigoBoleto() { return codigoBoleto; }

    /**
     * @param codigoBoleto the codigoBoleto to set
     */
    public void setCodigoBoleto(Long codigoBoleto) { this.codigoBoleto = codigoBoleto; }

    @Override
    public int hashCode() {
        return Objects.hash(idLugar, numeroAsiento, fila, idZona, codigoBoleto);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        LugarDTO other = (LugarDTO) obj;
        return Objects.equals(idLugar, other.idLugar)
                && Objects.equals(numeroAsiento, other.numeroAsiento)
                && Objects.equals(fila, other.fila)
                && Objects.equals(idZona, other.idZona)
                && Objects.equals(codigoBoleto, other.codigoBoleto);
    }

    @Override
    public String toString() {
        return "LugarDTO [idLugar=" + idLugar + ", numeroAsiento=" + numeroAsiento
                + ", fila=" + fila + ", idZona=" + idZona
                + ", codigoBoleto=" + codigoBoleto + "]";
    }
}