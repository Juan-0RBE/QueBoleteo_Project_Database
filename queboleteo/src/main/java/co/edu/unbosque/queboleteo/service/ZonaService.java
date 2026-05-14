package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.ZonaDTO;
import co.edu.unbosque.queboleteo.entity.Sede;
import co.edu.unbosque.queboleteo.entity.Zona;
import co.edu.unbosque.queboleteo.repository.SedeRepository;
import co.edu.unbosque.queboleteo.repository.ZonaRepository;

@Service
public class ZonaService implements CRUDOperation<ZonaDTO> {

    @Autowired
    private ZonaRepository zonaRepo;

    @Autowired
    private SedeRepository sedeRepo;

    public ZonaService() {
    }

    // ─────────────────────────────────────────────
    // Métodos auxiliares de mapeo manual
    // ─────────────────────────────────────────────

    /**
     * Convierte un DTO a entidad.
     * Resuelve la relación Sede a partir del nombreSede recibido en el DTO.
     *
     * @param dto DTO de la zona
     * @return Entidad Zona lista para persistir
     */
    private Zona toEntity(ZonaDTO dto) {
        Zona entity = new Zona();
        entity.setNombreZona(dto.getNombreZona());
        entity.setTieneAsiento(dto.getTieneAsiento());

        if (dto.getNombreSede() != null) {
            Optional<Sede> sede = sedeRepo.findById(dto.getNombreSede());
            sede.ifPresent(entity::setSede);
        }

        return entity;
    }

    /**
     * Convierte una entidad a DTO.
     * Extrae solo el nombreSede, no el objeto Sede completo.
     *
     * @param entity Entidad Zona
     * @return DTO de la zona
     */
    private ZonaDTO toDTO(Zona entity) {
        ZonaDTO dto = new ZonaDTO();
        dto.setIdZona(entity.getIdZona());
        dto.setNombreZona(entity.getNombreZona());
        dto.setTieneAsiento(entity.getTieneAsiento());

        if (entity.getSede() != null) {
            dto.setNombreSede(entity.getSede().getNombreSede());
        }

        return dto;
    }

    // ─────────────────────────────────────────────
    // Operaciones CRUD
    // ─────────────────────────────────────────────

    /**
     * Crea una nueva zona en la base de datos.
     *
     * @param newData DTO con los datos de la zona
     * @return 0 si fue exitoso, 1 si ya existe una zona con el mismo nombre
     */
    @Override
    public int create(ZonaDTO newData) {
        Optional<Zona> found = zonaRepo.findByNombreZona(newData.getNombreZona());
        if (found.isPresent()) {
            return 1;
        }
        zonaRepo.save(toEntity(newData));
        return 0;
    }

    /**
     * Obtiene todas las zonas registradas.
     *
     * @return Lista de DTOs de zonas
     */
    @Override
    public List<ZonaDTO> getAll() {
        List<Zona> entityList = zonaRepo.findAll();
        List<ZonaDTO> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));
        return dtoList;
    }

    /**
     * Obtiene una zona por su ID.
     *
     * @param id ID de la zona
     * @return DTO de la zona si existe, null en caso contrario
     */
    @Override
    public ZonaDTO getById(Long id) {
        Optional<Zona> found = zonaRepo.findById(id);
        if (found.isPresent()) {
            return toDTO(found.get());
        }
        return null;
    }

    /**
     * Elimina una zona por su ID.
     *
     * @param id ID de la zona
     * @return 0 si fue eliminada correctamente, 1 si no existe
     */
    @Override
    public int deleteById(Long id) {
        Optional<Zona> found = zonaRepo.findById(id);
        if (found.isPresent()) {
            zonaRepo.delete(found.get());
            return 0;
        }
        return 1;
    }

    /**
     * Actualiza una zona existente.
     *
     * @param id      ID de la zona a actualizar
     * @param newData Nuevos datos de la zona
     * @return 0 si actualizó correctamente, 1 si no existe
     */
    @Override
    public int updateById(Long id, ZonaDTO newData) {
        Optional<Zona> found = zonaRepo.findById(id);
        if (found.isPresent()) {
            Zona entity = found.get();
            entity.setNombreZona(newData.getNombreZona());
            entity.setTieneAsiento(newData.getTieneAsiento());

            if (newData.getNombreSede() != null) {
                Optional<Sede> sede = sedeRepo.findById(newData.getNombreSede());
                sede.ifPresent(entity::setSede);
            }

            zonaRepo.save(entity);
            return 0;
        }
        return 1;
    }

    /**
     * Cuenta el total de zonas registradas.
     *
     * @return Cantidad total de zonas
     */
    @Override
    public Long count() {
        return zonaRepo.count();
    }

    /**
     * Verifica si existe una zona con el ID dado.
     *
     * @param id ID de la zona
     * @return true si existe, false en caso contrario
     */
    @Override
    public boolean exist(Long id) {
        return zonaRepo.existsById(id);
    }
}