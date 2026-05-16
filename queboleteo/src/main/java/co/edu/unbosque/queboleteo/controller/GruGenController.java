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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.queboleteo.dto.GruGenDTO;
import co.edu.unbosque.queboleteo.service.GruGenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/grugen" })
@Tag(name = "Grupo-Género", description = "Endpoints para la relación entre grupos y géneros")
@SecurityRequirement(name = "bearerAuth")
public class GruGenController {

	@Autowired
	private GruGenService gruGenService;

	/**
	 * Asocia un género a un grupo.
	 *
	 * @param dto DTO con idGrupo e idGenero
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Asociar género a grupo")
	@PostMapping("/crear")
	public ResponseEntity<String> create(@RequestBody GruGenDTO dto) {
		int status = gruGenService.create(dto);
		if (status == 0)
			return new ResponseEntity<>("Asociación creada correctamente", HttpStatus.CREATED);
		if (status == 1)
			return new ResponseEntity<>("Grupo o género no encontrado", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>("Esa asociación ya existe", HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Obtiene todas las asociaciones grupo-género.
	 *
	 * @return Lista de asociaciones
	 */
	@Operation(summary = "Obtener todas las asociaciones grupo-género")
	@GetMapping("/all")
	public ResponseEntity<List<GruGenDTO>> getAll() {
		List<GruGenDTO> lista = gruGenService.getAll();
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene todos los géneros de un grupo.
	 *
	 * @param idGrupo ID del grupo
	 * @return Lista de asociaciones del grupo
	 */
	@Operation(summary = "Obtener géneros de un grupo")
	@GetMapping("/grupo/{idGrupo}")
	public ResponseEntity<List<GruGenDTO>> getByGrupoId(@PathVariable Long idGrupo) {
		List<GruGenDTO> lista = gruGenService.getByGrupoId(idGrupo);
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene todos los grupos de un género.
	 *
	 * @param idGenero ID del género
	 * @return Lista de asociaciones del género
	 */
	@Operation(summary = "Obtener grupos de un género")
	@GetMapping("/genero/{idGenero}")
	public ResponseEntity<List<GruGenDTO>> getByGeneroId(@PathVariable Long idGenero) {
		List<GruGenDTO> lista = gruGenService.getByGeneroId(idGenero);
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Elimina la asociación entre un grupo y un género.
	 *
	 * @param idGrupo  ID del grupo
	 * @param idGenero ID del género
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Eliminar asociación grupo-género")
	@DeleteMapping("/{idGrupo}/{idGenero}")
	public ResponseEntity<String> deleteByIds(@PathVariable Long idGrupo, @PathVariable Long idGenero) {
		int status = gruGenService.deleteByIds(idGrupo, idGenero);
		if (status == 0)
			return new ResponseEntity<>("Asociación eliminada correctamente", HttpStatus.OK);
		return new ResponseEntity<>("Asociación no encontrada", HttpStatus.NOT_FOUND);
	}

	/**
	 * Cuenta el total de asociaciones grupo-género.
	 *
	 * @return Cantidad total de asociaciones
	 */
	@Operation(summary = "Contar asociaciones grupo-género")
	@GetMapping("/count")
	public ResponseEntity<Long> count() {
		return new ResponseEntity<>(gruGenService.count(), HttpStatus.OK);
	}
}