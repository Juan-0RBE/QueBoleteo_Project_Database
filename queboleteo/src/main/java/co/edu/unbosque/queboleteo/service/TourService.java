package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.TourDTO;
import co.edu.unbosque.queboleteo.entity.Tour;
import co.edu.unbosque.queboleteo.repository.TourRepository;

@Service
public class TourService {

	@Autowired
	private TourRepository tourRepo;

	/**
	 * Crea un nuevo tour.
	 * 
	 * @param dto Datos del tour
	 * @return 0 si creó correctamente, 1 si ya existe
	 */
	public int create(TourDTO dto) {

		Tour found = tourRepo.findByNombreTour(
				dto.getNombreTour());

		if (found != null) {
			return 1;
		}

		Tour entity = dtoToEntity(dto);

		tourRepo.save(entity);

		return 0;
	}

	/**
	 * Obtiene todos los tours.
	 * 
	 * @return Lista de tours
	 */
	public List<TourDTO> getAll() {

		List<Tour> entities = tourRepo.findAll();

		List<TourDTO> dtos = new ArrayList<>();

		for (Tour t : entities) {
			dtos.add(entityToDTO(t));
		}

		return dtos;
	}

	/**
	 * Busca un tour por ID.
	 * 
	 * @param id ID del tour
	 * @return DTO encontrado o null
	 */
	public TourDTO getById(Long id) {

		Tour found = tourRepo.findById(id)
				.orElse(null);

		if (found == null) {
			return null;
		}

		return entityToDTO(found);
	}

	/**
	 * Actualiza un tour existente.
	 * 
	 * @param id ID del tour
	 * @param dto Nuevos datos
	 * @return 0 si actualizó, 1 si no existe
	 */
	public int updateById(Long id,
			TourDTO dto) {

		Tour found = tourRepo.findById(id)
				.orElse(null);

		if (found == null) {
			return 1;
		}

		found.setNombreTour(dto.getNombreTour());
		found.setDescripcionTour(
				dto.getDescripcionTour());
		found.setImagenTour(dto.getImagenTour());
		found.setFechaInicial(
				dto.getFechaInicial());
		found.setFechaFinal(
				dto.getFechaFinal());

		tourRepo.save(found);

		return 0;
	}

	/**
	 * Elimina un tour por ID.
	 * 
	 * @param id ID del tour
	 * @return 0 si eliminó, 1 si no existe
	 */
	public int deleteById(Long id) {

		Tour found = tourRepo.findById(id)
				.orElse(null);

		if (found == null) {
			return 1;
		}

		tourRepo.delete(found);

		return 0;
	}

	/**
	 * Cuenta los tours registrados.
	 * 
	 * @return Cantidad total
	 */
	public long count() {
		return tourRepo.count();
	}

	/**
	 * Convierte DTO a entidad.
	 * 
	 * @param dto DTO origen
	 * @return Entidad convertida
	 */
	private Tour dtoToEntity(TourDTO dto) {

		return new Tour(
				dto.getNombreTour(),
				dto.getDescripcionTour(),
				dto.getImagenTour(),
				dto.getFechaInicial(),
				dto.getFechaFinal());
	}

	/**
	 * Convierte entidad a DTO.
	 * 
	 * @param entity Entidad origen
	 * @return DTO convertido
	 */
	private TourDTO entityToDTO(Tour entity) {

		TourDTO dto = new TourDTO(
				entity.getNombreTour(),
				entity.getDescripcionTour(),
				entity.getImagenTour(),
				entity.getFechaInicial(),
				entity.getFechaFinal());

		dto.setIdTour(entity.getIdTour());

		return dto;
	}

}