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

import co.edu.unbosque.queboleteo.dto.ResenaDTO;
import co.edu.unbosque.queboleteo.service.ResenaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/resena" })
@Tag(name = "Gestión de Reseñas", description = "Endpoints para la gestión de reseñas de conciertos")
@SecurityRequirement(name = "bearerAuth")
public class ResenaController {

	@Autowired
	private ResenaService resenaService;

	/**
	 * Crea una nueva reseña.
	 *
	 * @param resena DTO de la reseña
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Crear reseña")
	@PostMapping("/crear")
	public ResponseEntity<String> create(@RequestBody ResenaDTO resena) {
		int status = resenaService.create(resena);
		if (status == 0) {
			return new ResponseEntity<>("Reseña creada correctamente", HttpStatus.CREATED);
		} else if (status == 2) {
			return new ResponseEntity<>("El usuario no tiene boletos para este concierto", HttpStatus.FORBIDDEN);

		}
		return new ResponseEntity<>("Este usuario ya tiene una reseña para ese concierto", HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Obtiene todas las reseñas.
	 *
	 * @return Lista de reseñas
	 */
	@Operation(summary = "Obtener todas las reseñas")
	@GetMapping("/all")
	public ResponseEntity<List<ResenaDTO>> getAll() {
		List<ResenaDTO> lista = resenaService.getAll();
		if (lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene una reseña por ID.
	 *
	 * @param id ID de la reseña
	 * @return Reseña encontrada
	 */
	@Operation(summary = "Obtener reseña por ID")
	@GetMapping("/{id}")
	public ResponseEntity<ResenaDTO> getById(@PathVariable Long id) {
		ResenaDTO found = resenaService.getById(id);
		if (found != null) {
			return new ResponseEntity<>(found, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Obtiene una reseña por ID.
	 *
	 * @param id ID de la reseña
	 * @return Reseña encontrada
	 */
	@Operation(summary = "Obtener reseña por concierto")
	@GetMapping("/concierto/{nombreConcierto}")
	public ResponseEntity<List<ResenaDTO>> getByNombreConcierto(@PathVariable String nombreConcierto) {
		List<ResenaDTO> lista = resenaService.getAllByConcierto(nombreConcierto);
		if (lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Actualiza una reseña existente.
	 *
	 * @param id     ID de la reseña
	 * @param resena Nuevos datos
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Actualizar reseña")
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateById(@PathVariable Long id, @RequestBody ResenaDTO resena) {
		int status = resenaService.updateById(id, resena);
		if (status == 0) {
			return new ResponseEntity<>("Reseña actualizada correctamente", HttpStatus.OK);
		}
		return new ResponseEntity<>("Reseña no encontrada", HttpStatus.NOT_FOUND);
	}

	/**
	 * Elimina una reseña por ID.
	 *
	 * @param id ID de la reseña
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Eliminar reseña")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		int status = resenaService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Reseña eliminada correctamente", HttpStatus.OK);
		}
		return new ResponseEntity<>("Reseña no encontrada", HttpStatus.NOT_FOUND);
	}

	/**
	 * Cuenta el total de reseñas.
	 *
	 * @return Cantidad total de reseñas
	 */
	@Operation(summary = "Contar reseñas")
	@GetMapping("/count")
	public ResponseEntity<Long> count() {
		return new ResponseEntity<>(resenaService.count(), HttpStatus.OK);
	}
}