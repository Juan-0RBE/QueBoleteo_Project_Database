package co.edu.unbosque.queboleteo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.queboleteo.entity.Tour;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
	
	
	/**
	 * Encuentra un tour por el nombre del tour
	 * 
	 * @param nombreTour
	 * @return
	 */
	Tour findByNombreTour(String nombreTour);

}