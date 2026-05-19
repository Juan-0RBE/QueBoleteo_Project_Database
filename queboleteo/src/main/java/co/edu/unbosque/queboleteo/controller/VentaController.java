package co.edu.unbosque.queboleteo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.queboleteo.dto.VentaDTO;
import co.edu.unbosque.queboleteo.entity.Lugar;
import co.edu.unbosque.queboleteo.entity.ZonaConcierto;
import co.edu.unbosque.queboleteo.service.EmailService;
import co.edu.unbosque.queboleteo.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import co.edu.unbosque.queboleteo.dto.CompraRequestDto;
import co.edu.unbosque.queboleteo.dto.CompraResponseDto;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/venta" })
@Tag(name = "Gestión de Ventas", description = "Endpoints para la gestión de ventas")
@SecurityRequirement(name = "bearerAuth")
public class VentaController {

	@Autowired
	private VentaService ventaService;

	/**
	 * Crea una nueva venta.
	 *
	 * @param venta DTO de la venta
	 * @return Mensaje de éxito
	 */
	@Operation(summary = "Crear venta")
	@PostMapping("/crear")
	public ResponseEntity<String> create(@RequestBody VentaDTO venta) {
		int status = ventaService.create(venta);
		if (status == 0) {
			return new ResponseEntity<>("Venta creada correctamente", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Error al crear la venta", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Obtiene todas las ventas.
	 *
	 * @return Lista de ventas
	 */
	@Operation(summary = "Obtener todas las ventas")
	@GetMapping("/all")
	public ResponseEntity<List<VentaDTO>> getAll() {
		List<VentaDTO> lista = ventaService.getAll();
		if (lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene una venta por ID.
	 *
	 * @param id ID de la venta
	 * @return Venta encontrada
	 */
	@Operation(summary = "Obtener venta por ID")
	@GetMapping("/{id}")
	public ResponseEntity<VentaDTO> getById(@PathVariable Long id) {
		VentaDTO found = ventaService.getById(id);
		if (found != null) {
			return new ResponseEntity<>(found, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Actualiza una venta existente.
	 *
	 * @param id    ID de la venta
	 * @param venta Nuevos datos
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Actualizar venta")
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateById(@PathVariable Long id, @RequestBody VentaDTO venta) {
		int status = ventaService.updateById(id, venta);
		if (status == 0) {
			return new ResponseEntity<>("Venta actualizada correctamente", HttpStatus.OK);
		}
		return new ResponseEntity<>("Venta no encontrada", HttpStatus.NOT_FOUND);
	}

	/**
	 * Elimina una venta por ID.
	 *
	 * @param id ID de la venta
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Eliminar venta")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		int status = ventaService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Venta eliminada correctamente", HttpStatus.OK);
		}
		return new ResponseEntity<>("Venta no encontrada", HttpStatus.NOT_FOUND);
	}

	/**
	 * Cuenta el total de ventas.
	 *
	 * @return Cantidad total de ventas
	 */
	@Operation(summary = "Contar ventas")
	@GetMapping("/count")
	public ResponseEntity<Long> count() {
		return new ResponseEntity<>(ventaService.count(), HttpStatus.OK);
	}

	/**
	 * Realiza la compra de boletos para un concierto. Crea la venta, genera los
	 * boletos y asigna lugares disponibles.
	 *
	 * @param dto DTO con correo del usuario, id de zona-concierto y cantidad
	 * @return Resumen de la compra con id de venta, valor total, boletos y lugares
	 */
	@Operation(summary = "Realizar compra de boletos")
	@PostMapping("/comprar")
	public ResponseEntity<?> comprar(@RequestBody CompraRequestDto dto) {

		try {

			CompraResponseDto response = ventaService.realizarCompra(dto);

			return ResponseEntity.status(HttpStatus.CREATED).body(response);

		} catch (RuntimeException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	/**
	 * Retorna los lugares disponibles (libres) de una zona-concierto. Útil para que
	 * el frontend muestre el mapa de asientos antes de comprar.
	 *
	 * @param idZonaConcierto ID de la zona-concierto
	 * @return Lista de lugares libres con fila y número de asiento
	 */
	@Operation(summary = "Consultar lugares disponibles de una zona-concierto")
	@GetMapping("/lugares-disponibles/{idZonaConcierto}")
	public ResponseEntity<List<Lugar>> lugaresDisponibles(@PathVariable Long idZonaConcierto) {
		try {
			ZonaConcierto zc = ventaService.getZonaConcierto(idZonaConcierto);
			List<Lugar> libres = ventaService.getLugaresLibres(zc.getZona().getIdZona(), zc.getIdPrecio());
			return new ResponseEntity<>(libres, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}