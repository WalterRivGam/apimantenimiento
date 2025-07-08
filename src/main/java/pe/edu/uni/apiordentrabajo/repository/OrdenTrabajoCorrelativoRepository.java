package pe.edu.uni.apiordentrabajo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.uni.apiordentrabajo.entity.OrdenTrabajoCorrelativo;
import pe.edu.uni.apiordentrabajo.entity.OrdenTrabajoCorrelativoId;

public interface OrdenTrabajoCorrelativoRepository extends JpaRepository<OrdenTrabajoCorrelativo, OrdenTrabajoCorrelativoId> {
    Optional<OrdenTrabajoCorrelativo> findByAnioAndTallerCodigo(int anio, String tallerCodigo);
}

