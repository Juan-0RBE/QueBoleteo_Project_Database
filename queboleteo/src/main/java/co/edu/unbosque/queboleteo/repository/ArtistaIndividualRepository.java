package co.edu.unbosque.queboleteo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.queboleteo.entity.ArtistaIndividual;

public interface ArtistaIndividualRepository extends JpaRepository<ArtistaIndividual, Long> {

	/**
	 * Busca un artista por su nombre.
	 * 
	 * @param nombreArtista Nombre del artista
	 * @return Optional con el artista si existe
	 */
	public Optional<ArtistaIndividual> findByNombreArtista(String nombreArtista);

	/**
	 * Elimina un artista por su nombre.
	 * 
	 * @param nombreArtista Nombre del artista
	 */
	public void deleteByNombreArtista(String nombreArtista);

}
