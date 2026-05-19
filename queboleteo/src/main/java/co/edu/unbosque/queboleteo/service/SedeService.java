package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.SedeDTO;
import co.edu.unbosque.queboleteo.entity.Sede;
import co.edu.unbosque.queboleteo.repository.SedeRepository;

@Service
public class SedeService {

	@Autowired
	private SedeRepository sedeRepo;

	/**
	 * Crea una nueva sede.
	 * 
	 * @param dto Datos de la sede
	 * @return 0 si se creó correctamente, 1 si ya existe
	 */
	public int create(SedeDTO dto) {

		Sede found = sedeRepo.findByNombreSede(dto.getNombreSede());

		if (found != null) {
			return 1;
		}

		Sede entity = dtoToEntity(dto);

		sedeRepo.save(entity);

		return 0;
	}

	/**
	 * Obtiene todas las sedes.
	 * 
	 * @return Lista de sedes
	 */
	public List<SedeDTO> getAll() {

		List<Sede> entities = sedeRepo.findAll();

		List<SedeDTO> dtos = new ArrayList<>();

		for (Sede s : entities) {
			dtos.add(entityToDTO(s));
		}

		return dtos;
	}

	/**
	 * Busca una sede por nombre.
	 * 
	 * @param nombre Nombre de la sede
	 * @return DTO encontrado o null
	 */
	public SedeDTO getById(String nombre) {

		Sede found = sedeRepo.findById(nombre).orElse(null);

		if (found == null) {
			return null;
		}

		return entityToDTO(found);
	}

	/**
	 * Actualiza una sede existente.
	 * 
	 * @param nombre Nombre actual
	 * @param dto    Nuevos datos
	 * @return 0 si actualizó, 1 si no existe
	 */
	public int updateById(String nombre, SedeDTO dto) {

		Sede found = sedeRepo.findById(nombre).orElse(null);

		if (found == null) {
			return 1;
		}

		found.setCalle(dto.getCalle());
		found.setCarrera(dto.getCarrera());
		found.setCiudad(dto.getCiudad());
		found.setTieneAccesibilidad(dto.getTieneAccesibilidad());
		found.setImagenSede(dto.getImagenSede());
		found.setImagenSeccion(dto.getImagenSeccion());

		sedeRepo.save(found);

		return 0;
	}

	/**
	 * Elimina una sede.
	 * 
	 * @param nombre Nombre de la sede
	 * @return 0 si eliminó, 1 si no existe
	 */
	public int deleteById(String nombre) {

		Sede found = sedeRepo.findById(nombre).orElse(null);

		if (found == null) {
			return 1;
		}

		sedeRepo.delete(found);

		return 0;
	}

	/**
	 * Cuenta las sedes registradas.
	 * 
	 * @return Cantidad total
	 */
	public long count() {
		return sedeRepo.count();
	}

	/**
	 * Convierte DTO a entidad.
	 * 
	 * @param dto DTO origen
	 * @return Entidad convertida
	 */
	private Sede dtoToEntity(SedeDTO dto) {

		return new Sede(dto.getNombreSede(), dto.getCalle(), dto.getCarrera(), dto.getCiudad(),
				dto.getTieneAccesibilidad(), dto.getImagenSede(), dto.getImagenSeccion());
	}

	/**
	 * Convierte entidad a DTO.
	 * 
	 * @param entity Entidad origen
	 * @return DTO convertido
	 */
	private SedeDTO entityToDTO(Sede entity) {

		return new SedeDTO(entity.getNombreSede(), entity.getCalle(), entity.getCarrera(), entity.getCiudad(),
				entity.getTieneAccesibilidad(), entity.getImagenSede(), entity.getImagenSeccion());
	}

}