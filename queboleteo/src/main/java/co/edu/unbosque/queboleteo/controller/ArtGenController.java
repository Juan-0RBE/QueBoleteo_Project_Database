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

import co.edu.unbosque.queboleteo.dto.ArtGenDTO;
import co.edu.unbosque.queboleteo.service.ArtGenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/artgen" })
@Tag(name = "Artista-Género", description = "Endpoints para la relación entre artistas individuales y géneros")
public class ArtGenController {

	@Autowired
	private ArtGenService artGenService;

	/**
	 * Asocia un género a un artista.
	 *
	 * @param dto DTO con idArtista e idGenero
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Asociar género a artista")
	@PostMapping("/crear")
	public ResponseEntity<String> create(@RequestBody ArtGenDTO dto) {
		int status = artGenService.create(dto);
		if (status == 0)
			return new ResponseEntity<>("Asociación creada correctamente", HttpStatus.CREATED);
		if (status == 1)
			return new ResponseEntity<>("Artista o género no encontrado", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>("Esa asociación ya existe", HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Obtiene todas las asociaciones artista-género.
	 *
	 * @return Lista de asociaciones
	 */
	@Operation(summary = "Obtener todas las asociaciones artista-género")
	@GetMapping("/all")
	public ResponseEntity<List<ArtGenDTO>> getAll() {
		List<ArtGenDTO> lista = artGenService.getAll();
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene todos los géneros de un artista.
	 *
	 * @param idArtista ID del artista
	 * @return Lista de asociaciones del artista
	 */
	@Operation(summary = "Obtener géneros de un artista")
	@GetMapping("/artista/{idArtista}")
	public ResponseEntity<List<ArtGenDTO>> getByArtistaId(@PathVariable Long idArtista) {
		List<ArtGenDTO> lista = artGenService.getByArtistaId(idArtista);
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene todos los artistas de un género.
	 *
	 * @param idGenero ID del género
	 * @return Lista de asociaciones del género
	 */
	@Operation(summary = "Obtener artistas de un género")
	@GetMapping("/genero/{idGenero}")
	public ResponseEntity<List<ArtGenDTO>> getByGeneroId(@PathVariable Long idGenero) {
		List<ArtGenDTO> lista = artGenService.getByGeneroId(idGenero);
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Elimina la asociación entre un artista y un género.
	 *
	 * @param idArtista ID del artista
	 * @param idGenero  ID del género
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Eliminar asociación artista-género")
	@DeleteMapping("/{idArtista}/{idGenero}")
	public ResponseEntity<String> deleteByIds(@PathVariable Long idArtista, @PathVariable Long idGenero) {
		int status = artGenService.deleteByIds(idArtista, idGenero);
		if (status == 0)
			return new ResponseEntity<>("Asociación eliminada correctamente", HttpStatus.OK);
		return new ResponseEntity<>("Asociación no encontrada", HttpStatus.NOT_FOUND);
	}

	/**
	 * Cuenta el total de asociaciones artista-género.
	 *
	 * @return Cantidad total de asociaciones
	 */
	@Operation(summary = "Contar asociaciones artista-género")
	@GetMapping("/count")
	public ResponseEntity<Long> count() {
		return new ResponseEntity<>(artGenService.count(), HttpStatus.OK);
	}
}