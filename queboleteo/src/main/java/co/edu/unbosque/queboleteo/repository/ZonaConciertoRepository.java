package co.edu.unbosque.queboleteo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.queboleteo.entity.Concierto;
import co.edu.unbosque.queboleteo.entity.Zona;
import co.edu.unbosque.queboleteo.entity.ZonaConcierto;

public interface ZonaConciertoRepository extends JpaRepository<ZonaConcierto, Long> {

    /**
     * Busca todos los registros de una zona específica.
     *
     * @param zona Objeto Zona
     * @return Lista de ZonaConcierto para esa zona
     */
    List<ZonaConcierto> findByZona(Zona zona);

    /**
     * Busca todos los registros de un concierto específico.
     *
     * @param concierto Objeto Concierto
     * @return Lista de ZonaConcierto para ese concierto
     */
    List<ZonaConcierto> findByConcierto(Concierto concierto);

    /**
     * Busca el registro de una zona en un concierto específico.
     * Útil para verificar duplicados en create.
     *
     * @param zona      Objeto Zona
     * @param concierto Objeto Concierto
     * @return Optional con el registro si existe
     */
    java.util.Optional<ZonaConcierto> findByZonaAndConcierto(Zona zona, Concierto concierto);
    
}