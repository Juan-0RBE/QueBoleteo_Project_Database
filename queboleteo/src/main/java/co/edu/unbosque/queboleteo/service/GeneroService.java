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

	@Override
	public int deleteById(Long id) {

		Optional<Genero> found = generoRepo.findById(id);

		if (found.isPresent()) {
			generoRepo.delete(found.get());
			return 0;
		}

		return 1;
	}

	public int deleteByNombreGenero(String nombreGenero) {

		Optional<Genero> found = generoRepo.findByNombreGenero(nombreGenero);

		if (found.isPresent()) {
			generoRepo.delete(found.get());
			return 0;
		}

		return 1;
	}

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

	@Override
	public Long count() {
		return generoRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return generoRepo.existsById(id);
	}

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