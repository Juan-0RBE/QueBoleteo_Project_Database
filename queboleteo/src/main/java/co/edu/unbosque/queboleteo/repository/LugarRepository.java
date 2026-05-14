package co.edu.unbosque.queboleteo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.queboleteo.entity.Lugar;
import co.edu.unbosque.queboleteo.entity.Zona;

public interface LugarRepository extends JpaRepository<Lugar, Long> {

    /**
     * Busca todos los lugares de una zona.
     *
     * @param zona Objeto Zona
     * @return Lista de lugares de esa zona
     */
    List<Lugar> findByZona(Zona zona);

    /**
     * Busca un lugar por su número de asiento y zona.
     * Útil para verificar duplicados en create.
     *
     * @param numeroAsiento Número del asiento
     * @param zona          Objeto Zona
     * @return Optional con el lugar si existe
     */
    Optional<Lugar> findByNumeroAsientoAndZona(Integer numeroAsiento, Zona zona);
}