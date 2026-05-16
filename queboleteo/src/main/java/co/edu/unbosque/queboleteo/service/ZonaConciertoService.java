package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.ZonaConciertoDTO;
import co.edu.unbosque.queboleteo.entity.Concierto;
import co.edu.unbosque.queboleteo.entity.Zona;
import co.edu.unbosque.queboleteo.entity.ZonaConcierto;
import co.edu.unbosque.queboleteo.repository.ConciertoRepository;
import co.edu.unbosque.queboleteo.repository.ZonaConciertoRepository;
import co.edu.unbosque.queboleteo.repository.ZonaRepository;

import co.edu.unbosque.queboleteo.repository.LugarRepository;

@Service
public class ZonaConciertoService implements CRUDOperation<ZonaConciertoDTO> {

	@Autowired
	private ZonaConciertoRepository zonaConciertoRepo;

	@Autowired
	private ZonaRepository zonaRepo;

	@Autowired
	private ConciertoRepository conciertoRepo;

	@Autowired
	private LugarRepository lugarRepo;

	public ZonaConciertoService() {
	}

	/**
	 * Convierte un DTO a entidad. Resuelve las relaciones con Zona y Concierto a
	 * partir de sus IDs.
	 *
	 * @param dto DTO de ZonaConcierto
	 * @return Entidad ZonaConcierto lista para persistir
	 */
	private ZonaConcierto toEntity(ZonaConciertoDTO dto) {
		ZonaConcierto entity = new ZonaConcierto();
		entity.setPrecio(dto.getPrecio());
		entity.setCantidadDisponible(dto.getCantidadDisponible());

		if (dto.getIdZona() != null) {
			Optional<Zona> zona = zonaRepo.findById(dto.getIdZona());
			zona.ifPresent(entity::setZona);
		}

		if (dto.getIdConcierto() != null) {
			Optional<Concierto> concierto = conciertoRepo.findById(dto.getIdConcierto());
			concierto.ifPresent(entity::setConcierto);
		}

		return entity;
	}

	/**
	 * Convierte una entidad a DTO. Extrae solo los IDs de Zona y Concierto.
	 *
	 * @param entity Entidad ZonaConcierto
	 * @return DTO de ZonaConcierto
	 */
	private ZonaConciertoDTO toDTO(ZonaConcierto entity) {
		ZonaConciertoDTO dto = new ZonaConciertoDTO();
		dto.setIdPrecio(entity.getIdPrecio());
		dto.setPrecio(entity.getPrecio());
		dto.setCantidadDisponible(entity.getCantidadDisponible());

		if (entity.getZona() != null) {
			dto.setIdZona(entity.getZona().getIdZona());
		}

		if (entity.getConcierto() != null) {
			dto.setIdConcierto(entity.getConcierto().getIdConcierto());
		}

		return dto;
	}

	/**
	 * Crea un nuevo registro ZonaConcierto. Verifica que no exista ya esa
	 * combinación zona+concierto.
	 *
	 * @param newData DTO con los datos
	 * @return 0 si fue exitoso, 1 si ya existe esa combinación
	 */
	/*
	 * @Override public int create(ZonaConciertoDTO newData) { // Verificamos
	 * duplicado por combinación zona+concierto if (newData.getIdZona() != null &&
	 * newData.getIdConcierto() != null) { Optional<Zona> zona =
	 * zonaRepo.findById(newData.getIdZona()); Optional<Concierto> concierto =
	 * conciertoRepo.findById(newData.getIdConcierto());
	 * 
	 * if (zona.isPresent() && concierto.isPresent()) { Optional<ZonaConcierto>
	 * found = zonaConciertoRepo.findByZonaAndConcierto(zona.get(),
	 * concierto.get()); if (found.isPresent()) { return 1; } } }
	 * 
	 * zonaConciertoRepo.save(toEntity(newData)); return 0; }
	 */

	/**
	 * Obtiene todos los registros ZonaConcierto.
	 *
	 * @return Lista de DTOs
	 */
	@Override
	public List<ZonaConciertoDTO> getAll() {
		List<ZonaConcierto> entityList = zonaConciertoRepo.findAll();
		List<ZonaConciertoDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(toDTO(entity)));
		return dtoList;
	}

	/**
	 * Obtiene un registro ZonaConcierto por su ID.
	 *
	 * @param id ID del registro
	 * @return DTO si existe, null en caso contrario
	 */
	@Override
	public ZonaConciertoDTO getById(Long id) {
		Optional<ZonaConcierto> found = zonaConciertoRepo.findById(id);
		if (found.isPresent()) {
			return toDTO(found.get());
		}
		return null;
	}

	/**
	 * Elimina un registro ZonaConcierto por su ID.
	 *
	 * @param id ID del registro
	 * @return 0 si fue eliminado correctamente, 1 si no existe
	 */
	@Override
	public int deleteById(Long id) {
		Optional<ZonaConcierto> found = zonaConciertoRepo.findById(id);
		if (found.isPresent()) {
			zonaConciertoRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	/**
	 * Actualiza un registro ZonaConcierto existente.
	 *
	 * @param id      ID del registro a actualizar
	 * @param newData Nuevos datos
	 * @return 0 si actualizó correctamente, 1 si no existe
	 */
	@Override
	public int updateById(Long id, ZonaConciertoDTO newData) {
		Optional<ZonaConcierto> found = zonaConciertoRepo.findById(id);
		if (found.isPresent()) {
			ZonaConcierto entity = found.get();
			entity.setPrecio(newData.getPrecio());
			entity.setCantidadDisponible(newData.getCantidadDisponible());

			if (newData.getIdZona() != null) {
				Optional<Zona> zona = zonaRepo.findById(newData.getIdZona());
				zona.ifPresent(entity::setZona);
			}

			if (newData.getIdConcierto() != null) {
				Optional<Concierto> concierto = conciertoRepo.findById(newData.getIdConcierto());
				concierto.ifPresent(entity::setConcierto);
			}

			zonaConciertoRepo.save(entity);
			return 0;
		}
		return 1;
	}

	/**
	 * Cuenta el total de registros ZonaConcierto.
	 *
	 * @return Cantidad total de registros
	 */
	@Override
	public Long count() {
		return zonaConciertoRepo.count();
	}

	/**
	 * Verifica si existe un registro con el ID dado.
	 *
	 * @param id ID del registro
	 * @return true si existe, false en caso contrario
	 */
	@Override
	public boolean exist(Long id) {
		return zonaConciertoRepo.existsById(id);
	}

	/**
	 * Crea un nuevo registro ZonaConcierto. La CantidadDisponible se calcula
	 * automáticamente contando los lugares libres que ya existen en la zona — no se
	 * recibe desde el request.
	 *
	 * @param newData DTO con precio, idZona e idConcierto
	 * @return 0 si fue exitoso 1 si ya existe esa combinación zona+concierto 2 si
	 *         la zona no tiene lugares configurados aún
	 */
	@Override
	public int create(ZonaConciertoDTO newData) {

		// 1. Verificar duplicado por combinación zona+concierto
		if (newData.getIdZona() != null && newData.getIdConcierto() != null) {
			Optional<Zona> zona = zonaRepo.findById(newData.getIdZona());
			Optional<Concierto> concierto = conciertoRepo.findById(newData.getIdConcierto());

			if (zona.isPresent() && concierto.isPresent()) {
				Optional<ZonaConcierto> found = zonaConciertoRepo.findByZonaAndConcierto(zona.get(), concierto.get());
				if (found.isPresent()) {
					return 1;
				}
			}
		}

		// 2. Calcular CantidadDisponible contando los lugares libres de la zona
		Integer lugaresDisponibles = lugarRepo.countByZona_IdZona(newData.getIdZona());

		if (lugaresDisponibles == null || lugaresDisponibles == 0) {
			return 2; // La zona no tiene lugares configurados
		}

		// 3. Construir entidad con la cantidad calculada
		ZonaConcierto entity = toEntity(newData);
		entity.setCantidadDisponible(lugaresDisponibles);

		zonaConciertoRepo.save(entity);
		return 0;
	}
}