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

import co.edu.unbosque.queboleteo.dto.ConArtDTO;
import co.edu.unbosque.queboleteo.service.ConArtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/conart" })
@Tag(name = "Concierto-Artista", description = "Endpoints para la relación entre conciertos y artistas individuales")
public class ConArtController {

	@Autowired
	private ConArtService conArtService;

	/**
	 * Asocia un artista a un concierto.
	 *
	 * @param dto DTO con idConcierto e idArtista
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Asociar artista a concierto")
	@PostMapping("/crear")
	public ResponseEntity<String> create(@RequestBody ConArtDTO dto) {
		int status = conArtService.create(dto);
		if (status == 0)
			return new ResponseEntity<>("Asociación creada correctamente", HttpStatus.CREATED);
		if (status == 1)
			return new ResponseEntity<>("Concierto o artista no encontrado", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>("Esa asociación ya existe", HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Obtiene todas las asociaciones concierto-artista.
	 *
	 * @return Lista de asociaciones
	 */
	@Operation(summary = "Obtener todas las asociaciones concierto-artista")
	@GetMapping("/all")
	public ResponseEntity<List<ConArtDTO>> getAll() {
		List<ConArtDTO> lista = conArtService.getAll();
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene todos los artistas de un concierto.
	 *
	 * @param idConcierto ID del concierto
	 * @return Lista de asociaciones del concierto
	 */
	@Operation(summary = "Obtener artistas de un concierto")
	@GetMapping("/concierto/{idConcierto}")
	public ResponseEntity<List<ConArtDTO>> getByConciertoId(@PathVariable Long idConcierto) {
		List<ConArtDTO> lista = conArtService.getByConciertoId(idConcierto);
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Obtiene todos los conciertos de un artista.
	 *
	 * @param idArtista ID del artista
	 * @return Lista de asociaciones del artista
	 */
	@Operation(summary = "Obtener conciertos de un artista")
	@GetMapping("/artista/{idArtista}")
	public ResponseEntity<List<ConArtDTO>> getByArtistaId(@PathVariable Long idArtista) {
		List<ConArtDTO> lista = conArtService.getByArtistaId(idArtista);
		if (lista.isEmpty())
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/**
	 * Elimina la asociación entre un concierto y un artista.
	 *
	 * @param idConcierto ID del concierto
	 * @param idArtista   ID del artista
	 * @return Mensaje de éxito o error
	 */
	@Operation(summary = "Eliminar asociación concierto-artista")
	@DeleteMapping("/{idConcierto}/{idArtista}")
	public ResponseEntity<String> deleteByIds(@PathVariable Long idConcierto, @PathVariable Long idArtista) {
		int status = conArtService.deleteByIds(idConcierto, idArtista);
		if (status == 0)
			return new ResponseEntity<>("Asociación eliminada correctamente", HttpStatus.OK);
		return new ResponseEntity<>("Asociación no encontrada", HttpStatus.NOT_FOUND);
	}

	/**
	 * Cuenta el total de asociaciones concierto-artista.
	 *
	 * @return Cantidad total de asociaciones
	 */
	@Operation(summary = "Contar asociaciones concierto-artista")
	@GetMapping("/count")
	public ResponseEntity<Long> count() {
		return new ResponseEntity<>(conArtService.count(), HttpStatus.OK);
	}
}