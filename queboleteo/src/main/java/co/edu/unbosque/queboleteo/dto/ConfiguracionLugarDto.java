package co.edu.unbosque.queboleteo.dto;

public class ConfiguracionLugarDto {

	// Para zona con asientos
	private Integer filas; // ej: 3 → genera A, B, C
	private Integer asientosPorFila; // ej: 10 → genera 1..10 por fila

	// Para zona general (sin asientos)
	private Integer capacidadGeneral; // ej: 200 → genera 200 lugares sin fila ni número

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