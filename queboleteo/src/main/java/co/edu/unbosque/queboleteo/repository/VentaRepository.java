package co.edu.unbosque.queboleteo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.queboleteo.entity.Usuario;
import co.edu.unbosque.queboleteo.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    /**
     * Busca todas las ventas de un usuario.
     *
     * @param usuario Objeto Usuario
     * @return Lista de ventas del usuario
     */
    List<Venta> findByUsuario(Usuario usuario);

}