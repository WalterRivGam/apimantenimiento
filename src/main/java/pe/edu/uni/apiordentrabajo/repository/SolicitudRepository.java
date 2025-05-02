package pe.edu.uni.apiordentrabajo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.uni.apiordentrabajo.entity.Solicitud;

public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {

}
