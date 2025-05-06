package pe.edu.uni.apiordentrabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.uni.apiordentrabajo.entity.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer>{
	List<Material> findByIdOrdenTrabajo(Integer idOrdenTrabajo);
}
