package co.edu.unbosque.queboleteo.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "VENTA")
public class Venta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IdVenta")
	private Long idVenta;

	@Column(name = "ValorTotal", precision = 28, scale = 2)
	private BigDecimal valorTotal;

	@Column(name = "FechaVenta")
	private LocalDateTime fechaVenta;

	@ManyToOne
	@JoinColumn(name = "USUARIO_Correo", nullable = false)
	private Usuario usuario;

	public Venta() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param valorTotal
	 * @param fechaVenta
	 * @param usuario
	 */
	public Venta(BigDecimal valorTotal, LocalDateTime fechaVenta, Usuario usuario) {
		super();
		this.valorTotal = valorTotal;
		this.fechaVenta = fechaVenta;
		this.usuario = usuario;
	}

	/**
	 * @return the idVenta
	 */
	public Long getIdVenta() {
		return idVenta;
	}

	/**
	 * @param idVenta the idVenta to set
	 */
	public void setIdVenta(Long idVenta) {
		this.idVenta = idVenta;
	}

	/**
	 * @return the valorTotal
	 */
	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	/**
	 * @param valorTotal the valorTotal to set
	 */
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	/**
	 * @return the fechaVenta
	 */
	public LocalDateTime getFechaVenta() {
		return fechaVenta;
	}

	/**
	 * @param fechaVenta the fechaVenta to set
	 */
	public void setFechaVenta(LocalDateTime fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
