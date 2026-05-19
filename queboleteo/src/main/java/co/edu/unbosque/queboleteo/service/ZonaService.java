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
import co.edu.unbosque.queboleteo.dto.ConfiguracionLugarDto;
import co.edu.unbosque.queboleteo.entity.Lugar;
import co.edu.unbosque.queboleteo.repository.LugarRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ZonaService implements CRUDOperation<ZonaDTO> {

	@Autowired
	private ZonaRepository zonaRepo;

	@Autowired
	private SedeRepository sedeRepo;

	@Autowired
	private LugarRepository lugarRepo;

	public ZonaService() {
	}

	/**
	 * Convierte un DTO a entidad.
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
			if (sede.isPresent()) {
				entity.setSede(sede.get());
			}
		}

		return entity;
	}

	/**
	 * Convierte una entidad a DTO.
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

	/**
	 * Crea una nueva zona en la base de datos.
	 *
	 * @param newData DTO con los datos de la zona
	 * @return 0 si fue exitoso, 1 si ya existe una zona con el mismo nombre
	 */
	@Override
	public int create(ZonaDTO newData) {
		Optional<Zona> found = zonaRepo.findByNombreZonaAndSede_NombreSede(newData.getNombreZona(),
				newData.getNombreSede());
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
				if (sede.isPresent()) {
					entity.setSede(sede.get());
				}
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

	/**
	 * Genera los lugares físicos de una zona según su tipo (con o sin asientos).
	 * Para zona con asientos: genera filas (A, B, C...) con N asientos cada una.
	 * Para zona general: genera N lugares sin fila ni número de asiento.
	 *
	 * @param idZona ID de la zona a configurar
	 * @param dto    DTO con filas y asientosPorFila, o capacidadGeneral
	 * @return 0 si fue exitoso, 1 si la zona no existe, 2 si los datos son
	 *         inválidos
	 */
	@Transactional
	public int configurarLugares(Long idZona, ConfiguracionLugarDto dto) {

		// 1. Verificar que la zona existe
		Optional<Zona> found = zonaRepo.findById(idZona);
		if (found.isEmpty()) {
			return 1;
		}
		Zona zona = found.get();

		long lugaresExistentes = lugarRepo.countByZona(zona);
		if (lugaresExistentes > 0)
			return 3; // nuevo código: ya configurada

		List<Lugar> lugares = new ArrayList<>();

		if (zona.getTieneAsiento()) {

			if (dto.getFilas() == null || dto.getFilas() <= 0 || dto.getAsientosPorFila() == null
					|| dto.getAsientosPorFila() <= 0) {
				return 2;
			}

			for (int f = 0; f < dto.getFilas(); f++) {
				String letraFila = String.valueOf((char) ('A' + f));
				for (int a = 1; a <= dto.getAsientosPorFila(); a++) {
					Lugar lugar = new Lugar();
					lugar.setFila(letraFila);
					lugar.setNumeroAsiento(a);
					lugar.setZona(zona);
					lugares.add(lugar);
				}
			}

		} else {

			if (dto.getCapacidadGeneral() == null || dto.getCapacidadGeneral() <= 0) {
				return 2;
			}

			for (int i = 0; i < dto.getCapacidadGeneral(); i++) {
				Lugar lugar = new Lugar();
				lugar.setFila(null);
				lugar.setNumeroAsiento(null);
				lugar.setZona(zona);
				lugares.add(lugar);
			}
		}

		lugarRepo.saveAll(lugares);
		return 0;
	}
}