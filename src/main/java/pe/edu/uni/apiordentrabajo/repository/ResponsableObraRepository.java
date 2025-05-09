package pe.edu.uni.apiordentrabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.uni.apiordentrabajo.entity.ResponsableObra;

public interface ResponsableObraRepository extends JpaRepository<ResponsableObra, Integer> {
	List<ResponsableObra> findByIdOrdenTrabajo(Integer idOrdenTrabajo);
	
	@Modifying
	@Query("UPDATE ResponsableObra r SET r.flagRegistroEliminado=1 WHERE r.idOrdenTrabajo=:idOrdenTrabajo")
	void eliminarPorIdOrdenTrabajo(@Param("idOrdenTrabajo") Integer idOrdenTrabajo);
}
