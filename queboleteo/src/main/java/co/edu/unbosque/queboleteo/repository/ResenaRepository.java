package co.edu.unbosque.queboleteo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.queboleteo.entity.Concierto;
import co.edu.unbosque.queboleteo.entity.Resena;
import co.edu.unbosque.queboleteo.entity.Usuario;

public interface ResenaRepository extends JpaRepository<Resena, Long> {

    /**
     * Busca todas las reseñas de un usuario.
     *
     * @param usuario Objeto Usuario
     * @return Lista de reseñas del usuario
     */
    List<Resena> findByUsuario(Usuario usuario);

    /**
     * Busca todas las reseñas de un concierto.
     *
     * @param concierto Objeto Concierto
     * @return Lista de reseñas del concierto
     */
    List<Resena> findByConcierto(Concierto concierto);

    /**
     * Busca la reseña de un usuario en un concierto específico.
     * Un usuario solo puede dejar una reseña por concierto.
     *
     * @param usuario   Objeto Usuario
     * @param concierto Objeto Concierto
     * @return Optional con la reseña si existe
     */
    Optional<Resena> findByUsuarioAndConcierto(Usuario usuario, Concierto concierto);
}