package co.edu.unbosque.queboleteo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.queboleteo.entity.Genero;

public interface GeneroRepository extends JpaRepository<Genero, Long> {

	public Optional<Genero> findByNombreGenero(String nombreGenero);

	public void deleteByNombreGenero(String nombreGenero);

}