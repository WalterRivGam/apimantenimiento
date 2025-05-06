package pe.edu.uni.apiordentrabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.uni.apiordentrabajo.entity.PersonalParticipa;

public interface PersonalParticipaRepository extends JpaRepository<PersonalParticipa, Integer> {
	List<PersonalParticipa> findByIdOrdenTrabajo(Integer idOrdenTrabajo);
}
