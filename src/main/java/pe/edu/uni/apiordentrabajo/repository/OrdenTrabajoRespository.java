package pe.edu.uni.apiordentrabajo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.uni.apiordentrabajo.entity.OrdenTrabajo;

public interface OrdenTrabajoRespository extends JpaRepository<OrdenTrabajo, Integer> {

	@Modifying
	@Query("UPDATE OrdenTrabajo ot SET ot.flagRegistroEliminado=1 WHERE ot.idOrdenTrabajo=:idOrdenTrabajo")
	void eliminarPorIdOrdenTrabajo(@Param("idOrdenTrabajo") Integer idOrdenTrabajo);
	
	Optional<OrdenTrabajo> findByNroOrdenTrabajo(String nroOrdenTrabajo);
	

}
