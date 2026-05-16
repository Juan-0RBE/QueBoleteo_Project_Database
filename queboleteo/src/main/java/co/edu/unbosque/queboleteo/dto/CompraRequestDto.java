package co.edu.unbosque.queboleteo.dto;

import java.util.List;

public class CompraRequestDto {

	private String correoUsuario;
	private Long idZonaConcierto;
	private Integer cantidad;

	// Opcional — solo se usa cuando la zona tiene asientos
	// Si la zona es general, se ignora aunque venga en el request
	private List<Long> idsLugaresElegidos;

	public CompraRequestDto() {
	}

	public String getCorreoUsuario() {
		return correoUsuario;
	}

	public void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}

	public Long getIdZonaConcierto() {
		return idZonaConcierto;
	}

	public void setIdZonaConcierto(Long idZonaConcierto) {
		this.idZonaConcierto = idZonaConcierto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public List<Long> getIdsLugaresElegidos() {
		return idsLugaresElegidos;
	}

	public void setIdsLugaresElegidos(List<Long> idsLugaresElegidos) {
		this.idsLugaresElegidos = idsLugaresElegidos;
	}
}