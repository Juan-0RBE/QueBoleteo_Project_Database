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

import co.edu.unbosque.queboleteo.dto.GeneroDTO;
import co.edu.unbosque.queboleteo.service.GeneroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para la gestión de géneros musicales.
 */
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/genero" })
@Tag(name = "Gestión de Géneros",
	description = "Endpoints para la gestión de géneros musicales")
@SecurityRequirement(name = "bearerAuth")
public class GeneroController {

	@Autowired
	private GeneroService generoService;

	/**
	 * Crea un nuevo género.
	 * 
	 * @param genero DTO del género
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Crear género")
	@PostMapping("/crear")
	public ResponseEntity<String> create(
			@RequestBody GeneroDTO genero) {

		int status = generoService.create(genero);

		if (status == 0) {

			return new ResponseEntity<>(
					"Género creado correctamente",
					HttpStatus.CREATED);

		} else {

			return new ResponseEntity<>(
					"Ya existe un género con ese nombre",
					HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * Obtiene todos los géneros.
	 * 
	 * @return Lista de géneros
	 */
	@Operation(summary = "Obtener todos los géneros")
	@GetMapping("/all")
	public ResponseEntity<List<GeneroDTO>> getAll() {

		List<GeneroDTO> lista = generoService.getAll();

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
	 * Obtiene un género por ID.
	 * 
	 * @param id ID del género
	 * @return Género encontrado
	 */
	@Operation(summary = "Obtener género por ID")
	@GetMapping("/{id}")
	public ResponseEntity<GeneroDTO> getById(
			@PathVariable Long id) {

		GeneroDTO found = generoService.getById(id);

		if (found != null) {

			return new ResponseEntity<>(
					found,
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Actualiza un género existente.
	 * 
	 * @param id ID del género
	 * @param genero Nuevos datos
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Actualizar género")
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateById(
			@PathVariable Long id,
			@RequestBody GeneroDTO genero) {

		int status = generoService.updateById(id, genero);

		if (status == 0) {

			return new ResponseEntity<>(
					"Género actualizado correctamente",
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				"Género no encontrado",
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Elimina un género por ID.
	 * 
	 * @param id ID del género
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Eliminar género")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteById(
			@PathVariable Long id) {

		int status = generoService.deleteById(id);

		if (status == 0) {

			return new ResponseEntity<>(
					"Género eliminado correctamente",
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				"Género no encontrado",
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Cuenta el total de géneros.
	 * 
	 * @return Cantidad total de géneros
	 */
	@Operation(summary = "Contar géneros")
	@GetMapping("/count")
	public ResponseEntity<Long> count() {

		return new ResponseEntity<>(
				generoService.count(),
				HttpStatus.OK);
	}

}