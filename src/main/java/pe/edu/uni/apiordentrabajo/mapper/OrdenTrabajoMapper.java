package pe.edu.uni.apiordentrabajo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import pe.edu.uni.apiordentrabajo.dto.OrdenTrabajoDTO;
import pe.edu.uni.apiordentrabajo.entity.OrdenTrabajo;

@Mapper
public interface OrdenTrabajoMapper {
	OrdenTrabajoDTO convertirEntityADTO(OrdenTrabajo ordenTrabajo);
	OrdenTrabajo convertirDTOAEntity(OrdenTrabajoDTO ordenTrabajoDTO);
	List<OrdenTrabajoDTO> convertirEntityADTO(List<OrdenTrabajo> ordenesTrabajo);
	List<OrdenTrabajo> convertirDTOAEntity(List<OrdenTrabajoDTO> ordenTrabajoDTOList);
}
