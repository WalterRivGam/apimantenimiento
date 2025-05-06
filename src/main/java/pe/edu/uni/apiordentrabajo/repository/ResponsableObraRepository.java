package pe.edu.uni.apiordentrabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.uni.apiordentrabajo.entity.ResponsableObra;

public interface ResponsableObraRepository extends JpaRepository<ResponsableObra, Integer> {
	List<ResponsableObra> findByIdOrdenTrabajo(Integer idOrdenTrabajo);
}
