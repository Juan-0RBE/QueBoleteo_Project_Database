package co.edu.unbosque.queboleteo.dto;

import java.math.BigDecimal;
import java.util.List;

public class CompraResponseDto {

	private Long idVenta;
	private BigDecimal valorTotal;
	private List<Long> codigosBoletos;
	private List<Long> idsLugares;

	public CompraResponseDto() {
	}

	public Long getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(Long idVenta) {
		this.idVenta = idVenta;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<Long> getCodigosBoletos() {
		return codigosBoletos;
	}

	public void setCodigosBoletos(List<Long> codigosBoletos) {
		this.codigosBoletos = codigosBoletos;
	}

	public List<Long> getIdsLugares() {
		return idsLugares;
	}

	public void setIdsLugares(List<Long> idsLugares) {
		this.idsLugares = idsLugares;
	}
}