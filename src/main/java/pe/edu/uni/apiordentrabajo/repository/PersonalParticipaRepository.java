package pe.edu.uni.apiordentrabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.uni.apiordentrabajo.entity.PersonalParticipa;

public interface PersonalParticipaRepository extends JpaRepository<PersonalParticipa, Integer> {
	List<PersonalParticipa> findByIdOrdenTrabajo(Integer idOrdenTrabajo);
	
	@Modifying
	@Query("UPDATE PersonalParticipa p SET p.flagRegistroEliminado=1 WHERE p.idOrdenTrabajo=:idOrdenTrabajo")
	void eliminarPorIdOrdenTrabajo(@Param("idOrdenTrabajo") Integer idOrdenTrabajo);
}
