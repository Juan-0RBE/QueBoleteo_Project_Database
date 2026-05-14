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

import co.edu.unbosque.queboleteo.dto.LugarDTO;
import co.edu.unbosque.queboleteo.service.LugarService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/lugar" })
@Tag(name = "Gestión de Lugares",
    description = "Endpoints para la gestión de lugares en zonas")
public class LugarController {

    @Autowired
    private LugarService lugarService;

    /**
     * Crea un nuevo lugar.
     *
     * @param lugar DTO del lugar
     * @return Mensaje de éxito o error
     */
    @Operation(summary = "Crear lugar")
    @PostMapping("/crear")
    public ResponseEntity<String> create(@RequestBody LugarDTO lugar) {
        int status = lugarService.create(lugar);
        if (status == 0) {
            return new ResponseEntity<>("Lugar creado correctamente", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Ya existe ese asiento en esa zona", HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Obtiene todos los lugares.
     *
     * @return Lista de lugares
     */
    @Operation(summary = "Obtener todos los lugares")
    @GetMapping("/all")
    public ResponseEntity<List<LugarDTO>> getAll() {
        List<LugarDTO> lista = lugarService.getAll();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    /**
     * Obtiene un lugar por ID.
     *
     * @param id ID del lugar
     * @return Lugar encontrado
     */
    @Operation(summary = "Obtener lugar por ID")
    @GetMapping("/{id}")
    public ResponseEntity<LugarDTO> getById(@PathVariable Long id) {
        LugarDTO found = lugarService.getById(id);
        if (found != null) {
            return new ResponseEntity<>(found, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Actualiza un lugar existente.
     *
     * @param id    ID del lugar
     * @param lugar Nuevos datos
     * @return Mensaje de éxito o error
     */
    @Operation(summary = "Actualizar lugar")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateById(@PathVariable Long id, @RequestBody LugarDTO lugar) {
        int status = lugarService.updateById(id, lugar);
        if (status == 0) {
            return new ResponseEntity<>("Lugar actualizado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("Lugar no encontrado", HttpStatus.NOT_FOUND);
    }

    /**
     * Elimina un lugar por ID.
     *
     * @param id ID del lugar
     * @return Mensaje de éxito o error
     */
    @Operation(summary = "Eliminar lugar")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        int status = lugarService.deleteById(id);
        if (status == 0) {
            return new ResponseEntity<>("Lugar eliminado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("Lugar no encontrado", HttpStatus.NOT_FOUND);
    }

    /**
     * Cuenta el total de lugares.
     *
     * @return Cantidad total de lugares
     */
    @Operation(summary = "Contar lugares")
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<>(lugarService.count(), HttpStatus.OK);
    }
}