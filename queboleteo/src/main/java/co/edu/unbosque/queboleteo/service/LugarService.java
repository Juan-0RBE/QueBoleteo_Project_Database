package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.LugarDTO;
import co.edu.unbosque.queboleteo.entity.Boleto;
import co.edu.unbosque.queboleteo.entity.Lugar;
import co.edu.unbosque.queboleteo.entity.Zona;
import co.edu.unbosque.queboleteo.repository.BoletoRepository;
import co.edu.unbosque.queboleteo.repository.LugarRepository;
import co.edu.unbosque.queboleteo.repository.ZonaRepository;

@Service
public class LugarService implements CRUDOperation<LugarDTO> {

    @Autowired
    private LugarRepository lugarRepo;

    @Autowired
    private ZonaRepository zonaRepo;

    @Autowired
    private BoletoRepository boletoRepo;

    public LugarService() {
    }

    /**
     * Convierte un DTO a entidad.
     * No asigna boleto — los lugares se crean libres (disponibles).
     * La asignación de boleto se hace mediante asignarBoleto().
     *
     * @param dto DTO del lugar
     * @return Entidad Lugar lista para persistir
     */
    private Lugar toEntity(LugarDTO dto) {
        Lugar entity = new Lugar();
        entity.setNumeroAsiento(dto.getNumeroAsiento());
        entity.setFila(dto.getFila());

        if (dto.getIdZona() != null) {
            Optional<Zona> zona = zonaRepo.findById(dto.getIdZona());
            zona.ifPresent(entity::setZona);
        }

        // boleto queda null — lugar disponible
        return entity;
    }

    /**
     * Convierte una entidad a DTO.
     * codigoBoleto será null si el lugar está disponible.
     *
     * @param entity Entidad Lugar
     * @return DTO del lugar
     */
    private LugarDTO toDTO(Lugar entity) {
        LugarDTO dto = new LugarDTO();
        dto.setIdLugar(entity.getIdLugar());
        dto.setNumeroAsiento(entity.getNumeroAsiento());
        dto.setFila(entity.getFila());

        if (entity.getZona() != null) {
            dto.setIdZona(entity.getZona().getIdZona());
        }

        // null si el lugar está disponible, valor si está ocupado
        if (entity.getBoleto() != null) {
            dto.setCodigoBoleto(entity.getBoleto().getCodigoBoleto());
        }

        return dto;
    }

    /**
     * Crea un nuevo lugar libre (sin boleto asignado).
     * Verifica que no exista ya ese número de asiento en la misma zona.
     *
     * @param newData DTO con los datos del lugar
     * @return 0 si fue exitoso, 1 si ya existe ese asiento en esa zona
     */
    @Override
    public int create(LugarDTO newData) {
        if (newData.getIdZona() != null && newData.getNumeroAsiento() != null) {
            Optional<Zona> zona = zonaRepo.findById(newData.getIdZona());
            if (zona.isPresent()) {
                Optional<Lugar> found = lugarRepo
                        .findByNumeroAsientoAndZona(newData.getNumeroAsiento(), zona.get());
                if (found.isPresent()) {
                    return 1;
                }
            }
        }

        lugarRepo.save(toEntity(newData));
        return 0;
    }

    /**
     * Obtiene todos los lugares registrados.
     *
     * @return Lista de DTOs de lugares
     */
    @Override
    public List<LugarDTO> getAll() {
        List<Lugar> entityList = lugarRepo.findAll();
        List<LugarDTO> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity)));
        return dtoList;
    }

    /**
     * Obtiene un lugar por su ID.
     *
     * @param id ID del lugar
     * @return DTO del lugar si existe, null en caso contrario
     */
    @Override
    public LugarDTO getById(Long id) {
        Optional<Lugar> found = lugarRepo.findById(id);
        if (found.isPresent()) {
            return toDTO(found.get());
        }
        return null;
    }

    /**
     * Elimina un lugar por su ID.
     *
     * @param id ID del lugar
     * @return 0 si fue eliminado correctamente, 1 si no existe
     */
    @Override
    public int deleteById(Long id) {
        Optional<Lugar> found = lugarRepo.findById(id);
        if (found.isPresent()) {
            lugarRepo.delete(found.get());
            return 0;
        }
        return 1;
    }

    /**
     * Actualiza los datos físicos de un lugar (asiento, fila, zona).
     * No toca el boleto asignado — eso se gestiona con asignarBoleto().
     *
     * @param id      ID del lugar a actualizar
     * @param newData Nuevos datos del lugar
     * @return 0 si actualizó correctamente, 1 si no existe
     */
    @Override
    public int updateById(Long id, LugarDTO newData) {
        Optional<Lugar> found = lugarRepo.findById(id);
        if (found.isPresent()) {
            Lugar entity = found.get();
            entity.setNumeroAsiento(newData.getNumeroAsiento());
            entity.setFila(newData.getFila());

            if (newData.getIdZona() != null) {
                Optional<Zona> zona = zonaRepo.findById(newData.getIdZona());
                zona.ifPresent(entity::setZona);
            }

            // boleto no se toca aquí
            lugarRepo.save(entity);
            return 0;
        }
        return 1;
    }

    /**
     * Asigna un boleto a un lugar (marca el lugar como ocupado).
     * Usado internamente por el flujo de compra.
     * Verifica que el lugar exista, que el boleto exista,
     * y que el lugar no tenga ya un boleto asignado.
     *
     * @param idLugar      ID del lugar a ocupar
     * @param codigoBoleto Código del boleto a asignar
     * @return 0 si fue exitoso,
     *         1 si el lugar no existe,
     *         2 si el lugar ya tiene un boleto asignado
     */
    public int asignarBoleto(Long idLugar, Long codigoBoleto) {
        Optional<Lugar> found = lugarRepo.findById(idLugar);
        if (found.isEmpty()) {
            return 1;
        }

        Lugar lugar = found.get();

        if (lugar.getBoleto() != null) {
            return 2; // lugar ya ocupado
        }

        Optional<Boleto> boleto = boletoRepo.findById(codigoBoleto);
        boleto.ifPresent(lugar::setBoleto);

        lugarRepo.save(lugar);
        return 0;
    }

    /**
     * Cuenta el total de lugares registrados.
     *
     * @return Cantidad total de lugares
     */
    @Override
    public Long count() {
        return lugarRepo.count();
    }

    /**
     * Verifica si existe un lugar con el ID dado.
     *
     * @param id ID del lugar
     * @return true si existe, false en caso contrario
     */
    @Override
    public boolean exist(Long id) {
        return lugarRepo.existsById(id);
    }
}