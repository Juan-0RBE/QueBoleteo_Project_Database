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

import co.edu.unbosque.queboleteo.dto.ConciertoDTO;
import co.edu.unbosque.queboleteo.service.ConciertoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/concierto" })
@Tag(name = "Gestión de Conciertos",
    description = "Endpoints para la gestión de conciertos")
public class ConciertoController {

    @Autowired
    private ConciertoService conciertoService;

    /**
     * Crea un nuevo concierto.
     *
     * @param concierto DTO del concierto
     * @return Mensaje de éxito o error
     */
    @Operation(summary = "Crear concierto")
    @PostMapping("/crear")
    public ResponseEntity<String> create(@RequestBody ConciertoDTO concierto) {
        int status = conciertoService.create(concierto);
        if (status == 0) {
            return new ResponseEntity<>("Concierto creado correctamente", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Ya existe un concierto con ese nombre", HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Obtiene todos los conciertos.
     *
     * @return Lista de conciertos
     */
    @Operation(summary = "Obtener todos los conciertos")
    @GetMapping("/all")
    public ResponseEntity<List<ConciertoDTO>> getAll() {
        List<ConciertoDTO> lista = conciertoService.getAll();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    /**
     * Obtiene un concierto por ID.
     *
     * @param id ID del concierto
     * @return Concierto encontrado
     */
    @Operation(summary = "Obtener concierto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ConciertoDTO> getById(@PathVariable Long id) {
        ConciertoDTO found = conciertoService.getById(id);
        if (found != null) {
            return new ResponseEntity<>(found, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Actualiza un concierto existente.
     *
     * @param id        ID del concierto
     * @param concierto Nuevos datos
     * @return Mensaje de éxito o error
     */
    @Operation(summary = "Actualizar concierto")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateById(@PathVariable Long id, @RequestBody ConciertoDTO concierto) {
        int status = conciertoService.updateById(id, concierto);
        if (status == 0) {
            return new ResponseEntity<>("Concierto actualizado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("Concierto no encontrado", HttpStatus.NOT_FOUND);
    }

    /**
     * Elimina un concierto por ID.
     *
     * @param id ID del concierto
     * @return Mensaje de éxito o error
     */
    @Operation(summary = "Eliminar concierto")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        int status = conciertoService.deleteById(id);
        if (status == 0) {
            return new ResponseEntity<>("Concierto eliminado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("Concierto no encontrado", HttpStatus.NOT_FOUND);
    }

    /**
     * Cuenta el total de conciertos.
     *
     * @return Cantidad total de conciertos
     */
    @Operation(summary = "Contar conciertos")
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<>(conciertoService.count(), HttpStatus.OK);
    }
}