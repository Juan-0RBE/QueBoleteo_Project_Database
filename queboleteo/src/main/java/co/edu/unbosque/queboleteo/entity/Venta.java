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
	 * @param idVenta
	 * @param valorTotal
	 * @param fechaVenta
	 * @param usuario
	 */
	public Venta(Long idVenta, BigDecimal valorTotal, LocalDateTime fechaVenta, Usuario usuario) {
		super();
		this.idVenta = idVenta;
		this.valorTotal = valorTotal;
		this.fechaVenta = fechaVenta;
		this.usuario = usuario;
	}

}
