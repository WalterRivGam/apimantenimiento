package pe.edu.uni.apiordentrabajo.service;

import java.util.List;
import java.util.Optional;

import pe.edu.uni.apiordentrabajo.dto.OrdenTrabajoDTO;

public interface OrdenTrabajoService {
	OrdenTrabajoDTO registrarOrden(OrdenTrabajoDTO ordenTrabajoDTO);
	Optional<OrdenTrabajoDTO> obtenerOrden(Integer id);
	Optional<OrdenTrabajoDTO> obtenerOrden(String nroOrden);
	List<OrdenTrabajoDTO> obtenerOrdenes();
	void eliminarOrden(Integer id);
}
