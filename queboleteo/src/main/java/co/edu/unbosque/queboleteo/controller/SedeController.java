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

import co.edu.unbosque.queboleteo.dto.SedeDTO;
import co.edu.unbosque.queboleteo.service.SedeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/sede" })
@Tag(name = "Gestión de Sedes",
	description = "Endpoints para la gestión de sedes")
@SecurityRequirement(name = "bearerAuth")
public class SedeController {

	@Autowired
	private SedeService sedeService;

	/**
	 * Crea una nueva sede.
	 * 
	 * @param sede DTO de la sede
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Crear sede")
	@PostMapping("/crear")
	public ResponseEntity<String> create(
			@RequestBody SedeDTO sede) {

		int status = sedeService.create(sede);

		if (status == 0) {

			return new ResponseEntity<>(
					"Sede creada correctamente",
					HttpStatus.CREATED);
		}

		return new ResponseEntity<>(
				"Ya existe una sede con ese nombre",
				HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Obtiene todas las sedes.
	 * 
	 * @return Lista de sedes
	 */
	@Operation(summary = "Obtener todas las sedes")
	@GetMapping("/all")
	public ResponseEntity<List<SedeDTO>> getAll() {

		List<SedeDTO> lista = sedeService.getAll();

		if (lista.isEmpty()) {

			return new ResponseEntity<>(
					lista,
					HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(
				lista,
				HttpStatus.OK);
	}

	/**
	 * Obtiene una sede por nombre.
	 * 
	 * @param nombre Nombre de la sede
	 * @return Sede encontrada
	 */
	@Operation(summary = "Obtener sede por nombre")
	@GetMapping("/{nombre}")
	public ResponseEntity<SedeDTO> getById(
			@PathVariable String nombre) {

		SedeDTO found = sedeService.getById(nombre);

		if (found != null) {

			return new ResponseEntity<>(
					found,
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Actualiza una sede existente.
	 * 
	 * @param nombre Nombre actual
	 * @param sede Nuevos datos
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Actualizar sede")
	@PutMapping("/update/{nombre}")
	public ResponseEntity<String> updateById(
			@PathVariable String nombre,
			@RequestBody SedeDTO sede) {

		int status = sedeService.updateById(
				nombre,
				sede);

		if (status == 0) {

			return new ResponseEntity<>(
					"Sede actualizada correctamente",
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				"Sede no encontrada",
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Elimina una sede.
	 * 
	 * @param nombre Nombre de la sede
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Eliminar sede")
	@DeleteMapping("/delete/{nombre}")
	public ResponseEntity<String> deleteById(
			@PathVariable String nombre) {

		int status = sedeService.deleteById(nombre);

		if (status == 0) {

			return new ResponseEntity<>(
					"Sede eliminada correctamente",
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				"Sede no encontrada",
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Cuenta el total de sedes.
	 * 
	 * @return Cantidad total
	 */
	@Operation(summary = "Contar sedes")
	@GetMapping("/count")
	public ResponseEntity<Long> count() {

		return new ResponseEntity<>(
				sedeService.count(),
				HttpStatus.OK);
	}

}