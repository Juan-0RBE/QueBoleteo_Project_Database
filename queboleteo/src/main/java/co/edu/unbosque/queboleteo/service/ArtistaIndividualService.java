package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.ArtistaIndividualDTO;
import co.edu.unbosque.queboleteo.entity.ArtistaIndividual;
import co.edu.unbosque.queboleteo.repository.ArtistaIndividualRepository;

@Service
public class ArtistaIndividualService implements CRUDOperation<ArtistaIndividualDTO> {

	@Autowired
	private ArtistaIndividualRepository artistaRepo;

	@Autowired
	private ModelMapper modelMapper;

	public ArtistaIndividualService() {
	}

	/**
	 * Crea un nuevo artista en la base de datos.
	 * 
	 * @param newData DTO con los datos del artista
	 * @return 0 si fue exitoso, 1 si ya existe un artista con el mismo nombre
	 */
	@Override
	public int create(ArtistaIndividualDTO newData) {
		Optional<ArtistaIndividual> found = artistaRepo.findByNombreArtista(newData.getNombreArtista());
		if (found.isPresent()) {
			return 1;
		}
		ArtistaIndividual entity = modelMapper.map(newData, ArtistaIndividual.class);
		artistaRepo.save(entity);
		return 0;
	}

	/**
	 * Obtiene todos los artistas registrados.
	 * 
	 * @return Lista de DTOs de artistas
	 */
	@Override
	public List<ArtistaIndividualDTO> getAll() {
		List<ArtistaIndividual> entityList = artistaRepo.findAll();
		List<ArtistaIndividualDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			ArtistaIndividualDTO dto = modelMapper.map(entity, ArtistaIndividualDTO.class);
			dtoList.add(dto);
		});
		return dtoList;
	}

	/**
	 * Obtiene un artista por su ID.
	 * 
	 * @param id ID del artista
	 * @return DTO del artista si existe, null en caso contrario
	 */
	@Override
	public ArtistaIndividualDTO getById(Long id) {
		Optional<ArtistaIndividual> found = artistaRepo.findById(id);
		if (found.isPresent()) {
			return modelMapper.map(found.get(), ArtistaIndividualDTO.class);
		}
		return null;
	}

	/**
	 * Elimina un artista por su ID.
	 * 
	 * @param id ID del artista
	 * @return 0 si fue eliminado correctamente, 1 si no existe
	 */
	@Override
	public int deleteById(Long id) {
		Optional<ArtistaIndividual> found = artistaRepo.findById(id);
		if (found.isPresent()) {
			artistaRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	/**
	 * Actualiza un artista existente.
	 * 
	 * @param id      ID del artista a actualizar
	 * @param newData Nuevos datos del artista
	 * @return 0 si actualizó correctamente, 1 si no existe
	 */
	@Override
	public int updateById(Long id, ArtistaIndividualDTO newData) {
		Optional<ArtistaIndividual> found = artistaRepo.findById(id);
		if (found.isPresent()) {
			ArtistaIndividual entity = found.get();
			entity.setNombreArtista(newData.getNombreArtista());
			entity.setDescripcionArtista(newData.getDescripcionArtista());
			entity.setImagenArtista(newData.getImagenArtista());
			entity.setPaisOrigenArtista(newData.getPaisOrigenArtista());
			entity.setEdadArtista(newData.getEdadArtista());
			entity.setLenguajeArtista(newData.getLenguajeArtista());
			artistaRepo.save(entity);
			return 0;
		}
		return 1;
	}

	/**
	 * Cuenta el total de artistas registrados.
	 * 
	 * @return Cantidad total de artistas
	 */
	@Override
	public Long count() {
		return artistaRepo.count();
	}

	/**
	 * Verifica si existe un artista con el ID dado.
	 * 
	 * @param id ID del artista
	 * @return true si existe, false en caso contrario
	 */
	@Override
	public boolean exist(Long id) {
		return artistaRepo.existsById(id);
	}

}