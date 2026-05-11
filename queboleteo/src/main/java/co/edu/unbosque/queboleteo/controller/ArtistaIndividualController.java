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

import co.edu.unbosque.queboleteo.dto.ArtistaIndividualDTO;
import co.edu.unbosque.queboleteo.service.ArtistaIndividualService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/artistaindividual" })
@Tag(name = "Gestión de Artistas Individuales", 
	description = "Endpoints para la gestión de artistas individuales")
public class ArtistaIndividualController {

	@Autowired
	private ArtistaIndividualService artistaService;

	/**
	 * Crea un nuevo artista individual.
	 * 
	 * @param artista DTO del artista
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Crear artista individual")
	@PostMapping("/crear")
	public ResponseEntity<String> create(
			@RequestBody ArtistaIndividualDTO artista) {

		int status = artistaService.create(artista);

		if (status == 0) {

			return new ResponseEntity<>(
					"Artista creado correctamente",
					HttpStatus.CREATED);

		} else {

			return new ResponseEntity<>(
					"Ya existe un artista con ese nombre",
					HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * Obtiene todos los artistas.
	 * 
	 * @return Lista de artistas
	 */
	@Operation(summary = "Obtener todos los artistas")
	@GetMapping("/all")
	public ResponseEntity<List<ArtistaIndividualDTO>> getAll() {

		List<ArtistaIndividualDTO> lista = artistaService.getAll();

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
	 * Obtiene un artista por ID.
	 * 
	 * @param id ID del artista
	 * @return Artista encontrado
	 */
	@Operation(summary = "Obtener artista por ID")
	@GetMapping("/{id}")
	public ResponseEntity<ArtistaIndividualDTO> getById(
			@PathVariable Long id) {

		ArtistaIndividualDTO found = artistaService.getById(id);

		if (found != null) {

			return new ResponseEntity<>(
					found,
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Actualiza un artista existente.
	 * 
	 * @param id ID del artista
	 * @param artista Nuevos datos
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Actualizar artista")
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateById(
			@PathVariable Long id,
			@RequestBody ArtistaIndividualDTO artista) {

		int status = artistaService.updateById(id, artista);

		if (status == 0) {

			return new ResponseEntity<>(
					"Artista actualizado correctamente",
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				"Artista no encontrado",
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Elimina un artista por ID.
	 * 
	 * @param id ID del artista
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Eliminar artista")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteById(
			@PathVariable Long id) {

		int status = artistaService.deleteById(id);

		if (status == 0) {

			return new ResponseEntity<>(
					"Artista eliminado correctamente",
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				"Artista no encontrado",
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Cuenta el total de artistas.
	 * 
	 * @return Cantidad total de artistas
	 */
	@Operation(summary = "Contar artistas")
	@GetMapping("/count")
	public ResponseEntity<Long> count() {

		return new ResponseEntity<>(
				artistaService.count(),
				HttpStatus.OK);
	}

}