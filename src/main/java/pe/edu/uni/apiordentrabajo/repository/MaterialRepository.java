package pe.edu.uni.apiordentrabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.uni.apiordentrabajo.entity.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer>{
	List<Material> findByIdOrdenTrabajo(Integer idOrdenTrabajo);
	
	@Modifying
	@Query("UPDATE Material m SET m.flagRegistroEliminado=1 WHERE m.idOrdenTrabajo=:idOrdenTrabajo")
	void eliminarPorIdOrdenTrabajo(@Param("idOrdenTrabajo") Integer idOrdenTrabajo);
}
