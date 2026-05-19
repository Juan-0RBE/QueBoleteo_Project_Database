package co.edu.unbosque.queboleteo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unbosque.queboleteo.entity.Concierto;
import co.edu.unbosque.queboleteo.entity.Sede;
import co.edu.unbosque.queboleteo.entity.Tour;

public interface ConciertoRepository extends JpaRepository<Concierto, Long> {

	/**
	 * Busca un concierto por su nombre.
	 *
	 * @param nombreConcierto Nombre del concierto
	 * @return Optional con el concierto si existe
	 */
	Optional<Concierto> findByNombreConcierto(String nombreConcierto);

	/**
	 * Elimina un concierto por su nombre.
	 *
	 * @param nombreConcierto Nombre del concierto
	 */
	void deleteByNombreConcierto(String nombreConcierto);

	/**
	 * Busca todos los conciertos asociados a un tour.
	 *
	 * @param tour Objeto Tour
	 * @return Lista de conciertos del tour
	 */
	List<Concierto> findByTour(Tour tour);

	/**
	 * Busca todos los conciertos de una sede.
	 *
	 * @param sede Objeto Sede
	 * @return Lista de conciertos en esa sede
	 */
	List<Concierto> findBySede(Sede sede);

	/**
	 * Busca si un concierto ya está creado para una sede, en una fecha específica
	 * 
	 * @param nombreSede
	 * @param fecha
	 * @return
	 */
	@Query("SELECT COUNT(c) > 0 FROM Concierto c " + "WHERE c.sede.nombreSede = :nombreSede "
			+ "AND FUNCTION('DATE', c.fechaConcierto) = FUNCTION('DATE', :fecha)")
	boolean existsBySedeFecha(@Param("nombreSede") String nombreSede, @Param("fecha") LocalDateTime fecha);
}