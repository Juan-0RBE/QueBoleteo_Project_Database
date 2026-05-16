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

import co.edu.unbosque.queboleteo.dto.GrupoDTO;
import co.edu.unbosque.queboleteo.service.GrupoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para la gestión de grupos musicales.
 */
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/grupo" })
@Tag(name = "Gestión de Grupos",
	description = "Endpoints para la gestión de grupos musicales")
@SecurityRequirement(name = "bearerAuth")
public class GrupoController {

	@Autowired
	private GrupoService grupoService;

	/**
	 * Crea un nuevo grupo.
	 * 
	 * @param grupo DTO del grupo
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Crear grupo")
	@PostMapping("/crear")
	public ResponseEntity<String> create(
			@RequestBody GrupoDTO grupo) {

		int status = grupoService.create(grupo);

		if (status == 0) {

			return new ResponseEntity<>(
					"Grupo creado correctamente",
					HttpStatus.CREATED);

		} else {

			return new ResponseEntity<>(
					"Ya existe un grupo con ese nombre",
					HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * Obtiene todos los grupos.
	 * 
	 * @return Lista de grupos
	 */
	@Operation(summary = "Obtener todos los grupos")
	@GetMapping("/all")
	public ResponseEntity<List<GrupoDTO>> getAll() {

		List<GrupoDTO> lista = grupoService.getAll();

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
	 * Obtiene un grupo por ID.
	 * 
	 * @param id ID del grupo
	 * @return Grupo encontrado
	 */
	@Operation(summary = "Obtener grupo por ID")
	@GetMapping("/{id}")
	public ResponseEntity<GrupoDTO> getById(
			@PathVariable Long id) {

		GrupoDTO found = grupoService.getById(id);

		if (found != null) {

			return new ResponseEntity<>(
					found,
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Actualiza un grupo existente.
	 * 
	 * @param id ID del grupo
	 * @param grupo Nuevos datos
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Actualizar grupo")
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateById(
			@PathVariable Long id,
			@RequestBody GrupoDTO grupo) {

		int status = grupoService.updateById(id, grupo);

		if (status == 0) {

			return new ResponseEntity<>(
					"Grupo actualizado correctamente",
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				"Grupo no encontrado",
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Elimina un grupo por ID.
	 * 
	 * @param id ID del grupo
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Eliminar grupo")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteById(
			@PathVariable Long id) {

		int status = grupoService.deleteById(id);

		if (status == 0) {

			return new ResponseEntity<>(
					"Grupo eliminado correctamente",
					HttpStatus.OK);
		}

		return new ResponseEntity<>(
				"Grupo no encontrado",
				HttpStatus.NOT_FOUND);
	}

	/**
	 * Cuenta el total de grupos.
	 * 
	 * @return Cantidad total de grupos
	 */
	@Operation(summary = "Contar grupos")
	@GetMapping("/count")
	public ResponseEntity<Long> count() {

		return new ResponseEntity<>(
				grupoService.count(),
				HttpStatus.OK);
	}

}