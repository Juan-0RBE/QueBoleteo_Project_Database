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

import co.edu.unbosque.queboleteo.dto.BoletoDTO;
import co.edu.unbosque.queboleteo.service.BoletoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/boleto" })
@Tag(name = "Gestión de Boletos",
    description = "Endpoints para la gestión de boletos")
@SecurityRequirement(name = "bearerAuth")
public class BoletoController {

    @Autowired
    private BoletoService boletoService;

    /**
     * Crea un nuevo boleto.
     *
     * @param boleto DTO del boleto
     * @return Mensaje de éxito o error
     */
    @Operation(summary = "Crear boleto")
    @PostMapping("/crear")
    public ResponseEntity<String> create(@RequestBody BoletoDTO boleto) {
        int status = boletoService.create(boleto);
            return new ResponseEntity<>("Boleto creado correctamente", HttpStatus.CREATED);
    }
    
    /**
     * Obtiene todos los boletos.
     *
     * @return Lista de boletos
     */
    @Operation(summary = "Obtener todos los boletos")
    @GetMapping("/all")
    public ResponseEntity<List<BoletoDTO>> getAll() {
        List<BoletoDTO> lista = boletoService.getAll();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    /**
     * Obtiene un boleto por su código.
     *
     * @param id Código del boleto
     * @return Boleto encontrado
     */
    @Operation(summary = "Obtener boleto por código")
    @GetMapping("/{id}")
    public ResponseEntity<BoletoDTO> getById(@PathVariable Long id) {
        BoletoDTO found = boletoService.getById(id);
        if (found != null) {
            return new ResponseEntity<>(found, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Actualiza un boleto existente.
     *
     * @param id     Código del boleto
     * @param boleto Nuevos datos
     * @return Mensaje de éxito o error
     */
    @Operation(summary = "Actualizar boleto")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateById(@PathVariable Long id,
            @RequestBody BoletoDTO boleto) {
        int status = boletoService.updateById(id, boleto);
        if (status == 0) {
            return new ResponseEntity<>("Boleto actualizado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("Boleto no encontrado", HttpStatus.NOT_FOUND);
    }

    /**
     * Elimina un boleto por su código.
     *
     * @param id Código del boleto
     * @return Mensaje de éxito o error
     */
    @Operation(summary = "Eliminar boleto")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        int status = boletoService.deleteById(id);
        if (status == 0) {
            return new ResponseEntity<>("Boleto eliminado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("Boleto no encontrado", HttpStatus.NOT_FOUND);
    }

    /**
     * Cuenta el total de boletos.
     *
     * @return Cantidad total de boletos
     */
    @Operation(summary = "Contar boletos")
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<>(boletoService.count(), HttpStatus.OK);
    }
}