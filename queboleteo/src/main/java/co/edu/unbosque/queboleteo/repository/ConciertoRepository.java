package co.edu.unbosque.queboleteo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

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
}