package pe.edu.uni.apiordentrabajo.service;

import java.util.Optional;

import pe.edu.uni.apiordentrabajo.dto.OrdenTrabajoDTO;

public interface OrdenTrabajoService {
	OrdenTrabajoDTO registrarOrden(OrdenTrabajoDTO ordenTrabajoDTO);
	Optional<OrdenTrabajoDTO> obtenerOrden(Integer id);
	void eliminarOrden(Integer id);
}
