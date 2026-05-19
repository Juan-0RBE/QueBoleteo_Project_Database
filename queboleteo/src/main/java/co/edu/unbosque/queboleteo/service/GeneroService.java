package co.edu.unbosque.queboleteo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.queboleteo.dto.GeneroDTO;
import co.edu.unbosque.queboleteo.entity.Genero;
import co.edu.unbosque.queboleteo.repository.GeneroRepository;

@Service
public class GeneroService implements CRUDOperation<GeneroDTO> {

	@Autowired
	private GeneroRepository generoRepo;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Crea un nuevo genero en la base de datos.
	 */
	@Override
	public int create(GeneroDTO newData) {
		Optional<Genero> found = generoRepo.findByNombreGenero(newData.getNombreGenero());
		if (found.isPresent()) {
			return 1;
		}
		Genero entity = modelMapper.map(newData, Genero.class);
		generoRepo.save(entity);

		return 0;
	}
	
	/**
	 * Obtiene todos los generos
	 */
	@Override
	public List<GeneroDTO> getAll() {
		List<Genero> entityList = generoRepo.findAll();
		List<GeneroDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			GeneroDTO dto = modelMapper.map(entity, GeneroDTO.class);
			dtoList.add(dto);
		});
		return dtoList;
	}

	/**
	 * Elimina un genero por su id
	 */
	@Override
	public int deleteById(Long id) {
		Optional<Genero> found = generoRepo.findById(id);
		if (found.isPresent()) {
			generoRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	/**
	 * Elimina un genero por su nombre
	 * 
	 * @param nombreGenero
	 * @return
	 */
	public int deleteByNombreGenero(String nombreGenero) {
		Optional<Genero> found = generoRepo.findByNombreGenero(nombreGenero);

		if (found.isPresent()) {
			generoRepo.delete(found.get());
			return 0;
		}
		return 1;
	}

	/**
	 * Actualiza un genero por su id
	 */
	@Override
	public int updateById(Long id, GeneroDTO newData) {
		Optional<Genero> found = generoRepo.findById(id);
		if (found.isPresent()) {
			Genero entity = found.get();
			entity.setNombreGenero(newData.getNombreGenero());
			generoRepo.save(entity);
			return 0;
		}
		return 1;
	}

	/**
	 * Cuenta cuantos generos hay
	 */
	@Override
	public Long count() {
		return generoRepo.count();
	}

	/**
	 * Verifica que un genero exista
	 */
	@Override
	public boolean exist(Long id) {
		return generoRepo.existsById(id);
	}

	/**
	 * Obtiene un genero por su id
	 */
	public GeneroDTO getById(Long id) {
		Optional<Genero> found = generoRepo.findById(id);
		if (found.isPresent()) {
			return modelMapper.map(found.get(), GeneroDTO.class);
		}
		return null;
	}

	public GeneroDTO getByNombreGenero(String nombreGenero) {
		Optional<Genero> found = generoRepo.findByNombreGenero(nombreGenero);
		if (found.isPresent()) {
			return modelMapper.map(found.get(), GeneroDTO.class);
		}
		return null;
	}

}