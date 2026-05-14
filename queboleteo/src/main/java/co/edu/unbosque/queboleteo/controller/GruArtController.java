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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.queboleteo.dto.GruArtDTO;
import co.edu.unbosque.queboleteo.service.GruArtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/gruart" })
@Tag(name = "Grupo-Artista", description = "Endpoints para la relación entre grupos y artistas individuales")
public class GruArtController {

	@Autowired
	private GruArtService gruArtService;

	/**
	 * Asocia un artista a un grupo con un rol específico.
	 *
	 * @param dto DTO con idGrupo, idArtista y rol
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Asociar artista a grupo con rol")
	@PostMapping("/crear")
	public ResponseEntity<String> create(@RequestBody GruArtDTO dto) {
		int status = gruArtService.create(dto);
		if (status == 0)
			return new ResponseEntity<>("Asociación creada correctamente", HttpStatus.CREATED);
		if (status == 1)
			return new ResponseEntity<>("Grupo o artista no encontrado", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>("Esa asociación ya existe", HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Obtiene todas las asociaciones grupo-artista.
	 *
	 * @return Lista de asociaciones
	 */
	@Operation(summary = "Obtener todas las asociaciones grupo-artista")
	@GetMapping("/all")
	public ResponseEntity<List<GruArtDTO>> getAll() {
		List<GruArtDTO> lista = gruArtService.getAll();
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene todos los artistas de un grupo.
	 *
	 * @param idGrupo ID del grupo
	 * @return Lista de asociaciones del grupo
	 */
	@Operation(summary = "Obtener artistas de un grupo")
	@GetMapping("/grupo/{idGrupo}")
	public ResponseEntity<List<GruArtDTO>> getByGrupoId(@PathVariable Long idGrupo) {
		List<GruArtDTO> lista = gruArtService.getByGrupoId(idGrupo);
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene todos los grupos de un artista.
	 *
	 * @param idArtista ID del artista
	 * @return Lista de asociaciones del artista
	 */
	@Operation(summary = "Obtener grupos de un artista")
	@GetMapping("/artista/{idArtista}")
	public ResponseEntity<List<GruArtDTO>> getByArtistaId(@PathVariable Long idArtista) {
		List<GruArtDTO> lista = gruArtService.getByArtistaId(idArtista);
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene todas las asociaciones con un rol específico.
	 *
	 * @param rol Rol a buscar
	 * @return Lista de asociaciones con ese rol
	 */
	@Operation(summary = "Obtener asociaciones por rol")
	@GetMapping("/rol/{rol}")
	public ResponseEntity<List<GruArtDTO>> getByRol(@PathVariable String rol) {
		List<GruArtDTO> lista = gruArtService.getByRol(rol);
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Actualiza el rol de un artista en un grupo.
	 *
	 * @param idGrupo   ID del grupo
	 * @param idArtista ID del artista
	 * @param dto       DTO con el nuevo rol
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Actualizar rol de artista en grupo")
	@PutMapping("/update/{idGrupo}/{idArtista}")
	public ResponseEntity<String> updateByIds(@PathVariable Long idGrupo, @PathVariable Long idArtista,
			@RequestBody GruArtDTO dto) {
		int status = gruArtService.updateByIds(idGrupo, idArtista, dto);
		if (status == 0)
			return new ResponseEntity<>("Rol actualizado correctamente", HttpStatus.OK);
		return new ResponseEntity<>("Asociación no encontrada", HttpStatus.NOT_FOUND);
	}

	/**
	 * Elimina la asociación entre un grupo y un artista.
	 *
	 * @param idGrupo   ID del grupo
	 * @param idArtista ID del artista
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Eliminar asociación grupo-artista")
	@DeleteMapping("/{idGrupo}/{idArtista}")
	public ResponseEntity<String> deleteByIds(@PathVariable Long idGrupo, @PathVariable Long idArtista) {
		int status = gruArtService.deleteByIds(idGrupo, idArtista);
		if (status == 0)
			return new ResponseEntity<>("Asociación eliminada correctamente", HttpStatus.OK);
		return new ResponseEntity<>("Asociación no encontrada", HttpStatus.NOT_FOUND);
	}

	/**
	 * Cuenta el total de asociaciones grupo-artista.
	 *
	 * @return Cantidad total de asociaciones
	 */
	@Operation(summary = "Contar asociaciones grupo-artista")
	@GetMapping("/count")
	public ResponseEntity<Long> count() {
		return new ResponseEntity<>(gruArtService.count(), HttpStatus.OK);
	}

}
