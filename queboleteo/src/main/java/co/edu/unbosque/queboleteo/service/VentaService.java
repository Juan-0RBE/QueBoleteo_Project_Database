package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.VentaDTO;
import co.edu.unbosque.queboleteo.entity.Usuario;
import co.edu.unbosque.queboleteo.entity.Venta;
import co.edu.unbosque.queboleteo.repository.UsuarioRepository;
import co.edu.unbosque.queboleteo.repository.VentaRepository;

@Service
public class VentaService implements CRUDOperation<VentaDTO> {

    @Autowired
    private VentaRepository ventaRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    public VentaService() {
    }

    /**
     * Convierte un DTO a entidad.
     * Resuelve la relación Usuario a partir del correo recibido en el DTO.
     *
     * @param dto DTO de la venta
     * @return Entidad Venta lista para persistir
     */
    private Venta toEntity(VentaDTO dto) {
        Venta entity = new Venta();
        entity.setValorTotal(dto.getValorTotal());
        entity.setFechaVenta(dto.getFechaVenta());

        // Resolvemos la relación con Usuario usando su correo (PK)
        if (dto.getCorreoUsuario() != null) {
            Optional<Usuario> usuario = usuarioRepo.findById(dto.getCorreoUsuario());
            usuario.ifPresent(entity::setUsuario);
        }

        return entity;
    }

    /**
     * Convierte una entidad a DTO.
     * Extrae solo el correo del usuario, no el objeto completo.
     *
     * @param entity Entidad Venta
     * @return DTO de la venta
     */
    private VentaDTO toDTO(Venta entity) {
        VentaDTO dto = new VentaDTO();
        dto.setIdVenta(entity.getIdVenta());
        dto.setValorTotal(entity.getValorTotal());
        dto.setFechaVenta(entity.getFechaVenta());

        // ⚠️ Extraemos solo el correo, nunca la clave ni datos de seguridad
        if (entity.getUsuario() != null) {
            dto.setCorreoUsuario(entity.getUsuario().getCorreo());
        }

        return dto;
    }

    /**
     * Crea una nueva venta en la base de datos.
     * un campo de negocio único — cada venta es inherentemente nueva.
     *
     * @param newData DTO con los datos de la venta
     * @return 0 si fue exitoso
     */
    @Override
    public int create(VentaDTO newData) {
        ventaRepo.save(toEntity(newData));
        return 0;
    }

    /**
     * Obtiene todas las ventas registradas.
     *
     * @return Lista de DTOs de ventas
     */
    @Override
    public List<VentaDTO> getAll() {
        List<Venta> entityList = ventaRepo.findAll();
        List<VentaDTO> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));
        return dtoList;
    }

    /**
     * Obtiene una venta por su ID.
     *
     * @param id ID de la venta
     * @return DTO de la venta si existe, null en caso contrario
     */
    @Override
    public VentaDTO getById(Long id) {
        Optional<Venta> found = ventaRepo.findById(id);
        if (found.isPresent()) {
            return toDTO(found.get());
        }
        return null;
    }

    /**
     * Elimina una venta por su ID.
     *
     * @param id ID de la venta
     * @return 0 si fue eliminada correctamente, 1 si no existe
     */
    @Override
    public int deleteById(Long id) {
        Optional<Venta> found = ventaRepo.findById(id);
        if (found.isPresent()) {
            ventaRepo.delete(found.get());
            return 0;
        }
        return 1;
    }

    /**
     * Actualiza una venta existente.
     *
     * @param id      ID de la venta a actualizar
     * @param newData Nuevos datos de la venta
     * @return 0 si actualizó correctamente, 1 si no existe
     */
    @Override
    public int updateById(Long id, VentaDTO newData) {
        Optional<Venta> found = ventaRepo.findById(id);
        if (found.isPresent()) {
            Venta entity = found.get();
            entity.setValorTotal(newData.getValorTotal());
            entity.setFechaVenta(newData.getFechaVenta());

            if (newData.getCorreoUsuario() != null) {
                Optional<Usuario> usuario = usuarioRepo.findById(newData.getCorreoUsuario());
                usuario.ifPresent(entity::setUsuario);
            }

            ventaRepo.save(entity);
            return 0;
        }
        return 1;
    }

    /**
     * Cuenta el total de ventas registradas.
     *
     * @return Cantidad total de ventas
     */
    @Override
    public Long count() {
        return ventaRepo.count();
    }

    /**
     * Verifica si existe una venta con el ID dado.
     *
     * @param id ID de la venta
     * @return true si existe, false en caso contrario
     */
    @Override
    public boolean exist(Long id) {
        return ventaRepo.existsById(id);
    }
}