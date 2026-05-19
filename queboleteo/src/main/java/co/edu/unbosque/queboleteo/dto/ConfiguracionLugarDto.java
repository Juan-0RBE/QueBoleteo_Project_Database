package co.edu.unbosque.queboleteo.dto;

public class ConfiguracionLugarDto {

	private Integer filas;
	private Integer asientosPorFila;
	private Integer capacidadGeneral;

	/**
	 * Constructor
	 */
	public ConfiguracionLugarDto() {
	}

	public Integer getFilas() {
		return filas;
	}

	public void setFilas(Integer filas) {
		this.filas = filas;
	}

	public Integer getAsientosPorFila() {
		return asientosPorFila;
	}

	public void setAsientosPorFila(Integer asientosPorFila) {
		this.asientosPorFila = asientosPorFila;
	}

	public Integer getCapacidadGeneral() {
		return capacidadGeneral;
	}

	public void setCapacidadGeneral(Integer capacidadGeneral) {
		this.capacidadGeneral = capacidadGeneral;
	}
}