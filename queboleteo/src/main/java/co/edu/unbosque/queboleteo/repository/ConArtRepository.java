package co.edu.unbosque.queboleteo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.queboleteo.entity.ArtistaIndividual;
import co.edu.unbosque.queboleteo.entity.ConArt;
import co.edu.unbosque.queboleteo.entity.ConArtId;
import co.edu.unbosque.queboleteo.entity.Concierto;

public interface ConArtRepository extends JpaRepository<ConArt, ConArtId> {

	/**
	 * Busca todas las asociaciones de un concierto.
	 *
	 * @param concierto Objeto Concierto
	 * @return Lista de ConArt para ese concierto
	 */
	List<ConArt> findByConcierto(Concierto concierto);

	/**
	 * Busca todas las asociaciones de un artista.
	 *
	 * @param artista Objeto ArtistaIndividual
	 * @return Lista de ConArt para ese artista
	 */
	List<ConArt> findByArtista(ArtistaIndividual artista);
}