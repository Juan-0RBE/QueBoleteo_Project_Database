package co.edu.unbosque.queboleteo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.queboleteo.entity.ArtGen;
import co.edu.unbosque.queboleteo.entity.ArtGenId;
import co.edu.unbosque.queboleteo.entity.ArtistaIndividual;
import co.edu.unbosque.queboleteo.entity.Genero;

public interface ArtGenRepository extends JpaRepository<ArtGen, ArtGenId> {

	/**
	 * Busca todas las asociaciones de un artista.
	 *
	 * @param artista Objeto ArtistaIndividual
	 * @return Lista de ArtGen para ese artista
	 */
	List<ArtGen> findByArtista(ArtistaIndividual artista);

	/**
	 * Busca todas las asociaciones de un género.
	 *
	 * @param genero Objeto Genero
	 * @return Lista de ArtGen para ese género
	 */
	List<ArtGen> findByGenero(Genero genero);
}