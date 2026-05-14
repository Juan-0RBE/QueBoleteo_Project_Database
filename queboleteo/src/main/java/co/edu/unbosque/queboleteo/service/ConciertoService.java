package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.ConciertoDTO;
import co.edu.unbosque.queboleteo.entity.Concierto;
import co.edu.unbosque.queboleteo.entity.Sede;
import co.edu.unbosque.queboleteo.entity.Tour;
import co.edu.unbosque.queboleteo.repository.ConciertoRepository;
import co.edu.unbosque.queboleteo.repository.SedeRepository;
import co.edu.unbosque.queboleteo.repository.TourRepository;

@Service
public class ConciertoService implements CRUDOperation<ConciertoDTO> {

    @Autowired
    private ConciertoRepository conciertoRepo;

    // ⚠️ Necesitamos los repos de Tour y Sede para poder buscar
    // los objetos completos a partir del ID/nombre que llega en el DTO
    @Autowired
    private TourRepository tourRepo;

    @Autowired
    private SedeRepository sedeRepo;

    public ConciertoService() {
    }

    // ─────────────────────────────────────────────
    // Métodos auxiliares de mapeo manual
    // ─────────────────────────────────────────────

    /**
     * Convierte un DTO a entidad.
     * ⚠️ No usamos ModelMapper porque los campos de relación
     * (idTour → Tour, nombreSede → Sede) no se mapean automáticamente.
     *
     * @param dto DTO del concierto
     * @return Entidad Concierto lista para persistir
     */
    private Concierto toEntity(ConciertoDTO dto) {
        Concierto entity = new Concierto();
        entity.setNombreConcierto(dto.getNombreConcierto());
        entity.setDescripcionConcierto(dto.getDescripcionConcierto());
        entity.setImagenConcierto(dto.getImagenConcierto());
        entity.setEdadMinima(dto.getEdadMinima());
        entity.setRecomendacion(dto.getRecomendacion());
        entity.setFechaConcierto(dto.getFechaConcierto());
        entity.setEstadoConcierto(dto.getEstadoConcierto());

        // Resolvemos la relación con Tour usando su ID
        if (dto.getIdTour() != null) {
            Optional<Tour> tour = tourRepo.findById(dto.getIdTour());
            tour.ifPresent(entity::setTour);
        }

        // Resolvemos la relación con Sede usando su nombre (PK de Sede)
        if (dto.getNombreSede() != null) {
            Optional<Sede> sede = sedeRepo.findById(dto.getNombreSede());
            sede.ifPresent(entity::setSede);
        }

        return entity;
    }

    /**
     * Convierte una entidad a DTO.
     * ⚠️ Extraemos solo las claves primarias de Tour y Sede,
     * no los objetos completos, para no exponer datos innecesarios.
     *
     * @param entity Entidad Concierto
     * @return DTO del concierto
     */
    private ConciertoDTO toDTO(Concierto entity) {
        ConciertoDTO dto = new ConciertoDTO();
        dto.setIdConcierto(entity.getIdConcierto());
        dto.setNombreConcierto(entity.getNombreConcierto());
        dto.setDescripcionConcierto(entity.getDescripcionConcierto());
        dto.setImagenConcierto(entity.getImagenConcierto());
        dto.setEdadMinima(entity.getEdadMinima());
        dto.setRecomendacion(entity.getRecomendacion());
        dto.setFechaConcierto(entity.getFechaConcierto());
        dto.setEstadoConcierto(entity.getEstadoConcierto());

        // Extraemos solo el ID del tour, no el objeto completo
        if (entity.getTour() != null) {
            dto.setIdTour(entity.getTour().getIdTour());
        }

        // Extraemos solo el nombre de la sede
        if (entity.getSede() != null) {
            dto.setNombreSede(entity.getSede().getNombreSede());
        }

        return dto;
    }

    // ─────────────────────────────────────────────
    // Operaciones CRUD
    // ─────────────────────────────────────────────

    /**
     * Crea un nuevo concierto en la base de datos.
     *
     * @param newData DTO con los datos del concierto
     * @return 0 si fue exitoso, 1 si ya existe un concierto con el mismo nombre
     */
    @Override
    public int create(ConciertoDTO newData) {
        Optional<Concierto> found = conciertoRepo.findByNombreConcierto(newData.getNombreConcierto());
        if (found.isPresent()) {
            return 1;
        }
        conciertoRepo.save(toEntity(newData));
        return 0;
    }

    /**
     * Obtiene todos los conciertos registrados.
     *
     * @return Lista de DTOs de conciertos
     */
    @Override
    public List<ConciertoDTO> getAll() {
        List<Concierto> entityList = conciertoRepo.findAll();
        List<ConciertoDTO> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));
        return dtoList;
    }

    /**
     * Obtiene un concierto por su ID.
     *
     * @param id ID del concierto
     * @return DTO del concierto si existe, null en caso contrario
     */
    @Override
    public ConciertoDTO getById(Long id) {
        Optional<Concierto> found = conciertoRepo.findById(id);
        if (found.isPresent()) {
            return toDTO(found.get());
        }
        return null;
    }

    /**
     * Elimina un concierto por su ID.
     *
     * @param id ID del concierto
     * @return 0 si fue eliminado correctamente, 1 si no existe
     */
    @Override
    public int deleteById(Long id) {
        Optional<Concierto> found = conciertoRepo.findById(id);
        if (found.isPresent()) {
            conciertoRepo.delete(found.get());
            return 0;
        }
        return 1;
    }

    /**
     * Actualiza un concierto existente.
     *
     * @param id      ID del concierto a actualizar
     * @param newData Nuevos datos del concierto
     * @return 0 si actualizó correctamente, 1 si no existe
     */
    @Override
    public int updateById(Long id, ConciertoDTO newData) {
        Optional<Concierto> found = conciertoRepo.findById(id);
        if (found.isPresent()) {
            Concierto entity = found.get();
            entity.setNombreConcierto(newData.getNombreConcierto());
            entity.setDescripcionConcierto(newData.getDescripcionConcierto());
            entity.setImagenConcierto(newData.getImagenConcierto());
            entity.setEdadMinima(newData.getEdadMinima());
            entity.setRecomendacion(newData.getRecomendacion());
            entity.setFechaConcierto(newData.getFechaConcierto());
            entity.setEstadoConcierto(newData.getEstadoConcierto());

            // Actualizamos la relación con Tour si viene un nuevo ID
            if (newData.getIdTour() != null) {
                Optional<Tour> tour = tourRepo.findById(newData.getIdTour());
                tour.ifPresent(entity::setTour);
            }

            // Actualizamos la relación con Sede si viene un nuevo nombre
            if (newData.getNombreSede() != null) {
                Optional<Sede> sede = sedeRepo.findById(newData.getNombreSede());
                sede.ifPresent(entity::setSede);
            }

            conciertoRepo.save(entity);
            return 0;
        }
        return 1;
    }

    /**
     * Cuenta el total de conciertos registrados.
     *
     * @return Cantidad total de conciertos
     */
    @Override
    public Long count() {
        return conciertoRepo.count();
    }

    /**
     * Verifica si existe un concierto con el ID dado.
     *
     * @param id ID del concierto
     * @return true si existe, false en caso contrario
     */
    @Override
    public boolean exist(Long id) {
        return conciertoRepo.existsById(id);
    }
}