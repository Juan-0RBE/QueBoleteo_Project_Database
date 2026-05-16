package co.edu.unbosque.queboleteo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.queboleteo.entity.Sede;
import co.edu.unbosque.queboleteo.entity.Zona;

public interface ZonaRepository extends JpaRepository<Zona, Long> {

    /**
     * Busca una zona por su nombre.
     *
     * @param nombreZona Nombre de la zona
     * @return Optional con la zona si existe
     */
    Optional<Zona> findByNombreZona(String nombreZona);
    
    /**
     * Busca una zona por su nombre y el nombre de la sede.
     *
     * @param nombreZona Nombre de la zona
     * @return Optional con la zona si existe
     */
    Optional<Zona> findByNombreZonaAndSede_NombreSede(String nombreZona, String nombreSede);

    /**
     * Elimina una zona por su nombre.
     *
     * @param nombreZona Nombre de la zona
     */
    void deleteByNombreZona(String nombreZona);

    /**
     * Busca todas las zonas de una sede.
     *
     * @param sede Objeto Sede
     * @return Lista de zonas de esa sede
     */
    List<Zona> findBySede(Sede sede);
}