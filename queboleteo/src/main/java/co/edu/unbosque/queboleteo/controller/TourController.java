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

import co.edu.unbosque.queboleteo.dto.TourDTO;
import co.edu.unbosque.queboleteo.service.TourService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/tour" })
@Tag(name = "Gestión de Tours",
	description = "Endpoints para la gestión de tours")
@SecurityRequirement(name = "bearerAuth")
public class TourController {

	@Autowired
	private TourService tourService;

	/**
	 * Crea un nuevo tour.
	 * 
	 * @param tour DTO del tour
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Crear tour")
	@PostMapping("/crear")
	public ResponseEntity<String> create(
			@RequestBody TourDTO tour) {

		int status = tourService.create(tour);

		if (status == 0) {

			return new ResponseEntity<>(
					"Tour creado correctamente",
					HttpStatus.CREATED);
		}

		return new ResponseEntity<>(
				"Ya existe un tour con ese nombre",
				HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Obtiene todos los tours.
	 * 
	 * @return Lista de tours
	 */
	@Operation(summary = "Obtener todos los tours")
	@GetMapping("/all")
	public ResponseEntity<List<TourDTO>> getAll() {

		List<TourDTO> lista = tourService.getAll();

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
	 * Obtiene un tour por ID.
	 * 
	 * @param id ID del tour
	 * @return Tour encontrado
	 */
	@Operation(summary = "Obtener tour por ID")
	@GetMapping("/{id}")
	public ResponseEntity<TourDTO> getById(
			@PathVariable Long id) {

		TourDTO found = tourService.getById(id);

		if (found != null) {

			return new ResponseEntity<>(
					found,
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Actualiza un tour existente.
	 * 
	 * @param id ID del tour
	 * @param tour Nuevos datos
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Actualizar tour")
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateById(
			@PathVariable Long id,
			@RequestBody TourDTO tour) {

		int status = tourService.updateById(
				id,
				tour);

		if (status == 0) {

			return new ResponseEntity<>(
					"Tour actualizado correctamente",
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				"Tour no encontrado",
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Elimina un tour por ID.
	 * 
	 * @param id ID del tour
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Eliminar tour")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteById(
			@PathVariable Long id) {

		int status = tourService.deleteById(id);

		if (status == 0) {

			return new ResponseEntity<>(
					"Tour eliminado correctamente",
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				"Tour no encontrado",
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Cuenta los tours registrados.
	 * 
	 * @return Cantidad total
	 */
	@Operation(summary = "Contar tours")
	@GetMapping("/count")
	public ResponseEntity<Long> count() {

		return new ResponseEntity<>(
				tourService.count(),
				HttpStatus.OK);
	}

}