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

import co.edu.unbosque.queboleteo.dto.ZonaDTO;
import co.edu.unbosque.queboleteo.service.ZonaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;


import co.edu.unbosque.queboleteo.dto.ConfiguracionLugarDto;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/zona" })
@Tag(name = "Gestión de Zonas",
    description = "Endpoints para la gestión de zonas")
@SecurityRequirement(name = "bearerAuth")
public class ZonaController {

    @Autowired
    private ZonaService zonaService;

    /**
     * Crea una nueva zona.
     *
     * @param zona DTO de la zona
     * @return Mensaje de éxito o error
     */
    @Operation(summary = "Crear zona")
    @PostMapping("/crear")
    public ResponseEntity<String> create(@RequestBody ZonaDTO zona) {
        int status = zonaService.create(zona);
        if (status == 0) {
            return new ResponseEntity<>("Zona creada correctamente", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Ya existe una zona con ese nombre", HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Obtiene todas las zonas.
     *
     * @return Lista de zonas
     */
    @Operation(summary = "Obtener todas las zonas")
    @GetMapping("/all")
    public ResponseEntity<List<ZonaDTO>> getAll() {
        List<ZonaDTO> lista = zonaService.getAll();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    /**
     * Obtiene una zona por ID.
     *
     * @param id ID de la zona
     * @return Zona encontrada
     */
    @Operation(summary = "Obtener zona por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ZonaDTO> getById(@PathVariable Long id) {
        ZonaDTO found = zonaService.getById(id);
        if (found != null) {
            return new ResponseEntity<>(found, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Actualiza una zona existente.
     *
     * @param id   ID de la zona
     * @param zona Nuevos datos
     * @return Mensaje de éxito o error
     */
    @Operation(summary = "Actualizar zona")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateById(@PathVariable Long id, @RequestBody ZonaDTO zona) {
        int status = zonaService.updateById(id, zona);
        if (status == 0) {
            return new ResponseEntity<>("Zona actualizada correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("Zona no encontrada", HttpStatus.NOT_FOUND);
    }

    /**
     * Elimina una zona por ID.
     *
     * @param id ID de la zona
     * @return Mensaje de éxito o error
     */
    @Operation(summary = "Eliminar zona")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        int status = zonaService.deleteById(id);
        if (status == 0) {
            return new ResponseEntity<>("Zona eliminada correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("Zona no encontrada", HttpStatus.NOT_FOUND);
    }

    /**
     * Cuenta el total de zonas.
     *
     * @return Cantidad total de zonas
     */
    @Operation(summary = "Contar zonas")
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<>(zonaService.count(), HttpStatus.OK);
    }
    
    /**
     * Genera los lugares físicos de una zona.
     * Si la zona tiene asientos, requiere filas y asientosPorFila.
     * Si es zona general, requiere capacidadGeneral.
     *
     * @param idZona ID de la zona
     * @param dto    Datos de configuración
     * @return Mensaje de éxito o error
     */
    @Operation(summary = "Configurar lugares de una zona")
    @PostMapping("/configurar-lugares/{idZona}")
    public ResponseEntity<String> configurarLugares(
            @PathVariable Long idZona,
            @RequestBody ConfiguracionLugarDto dto) {

        int status = zonaService.configurarLugares(idZona, dto);

        if (status == 0) {
            return new ResponseEntity<>("Lugares generados correctamente", HttpStatus.CREATED);
        } else if (status == 1) {
            return new ResponseEntity<>("Zona no encontrada", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(
                "Datos inválidos. Para zona con asientos envía filas y asientosPorFila. " +
                "Para zona general envía capacidadGeneral.",
                HttpStatus.BAD_REQUEST);
        }
    }
}