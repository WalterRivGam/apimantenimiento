package pe.edu.uni.apiordentrabajo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import pe.edu.uni.apiordentrabajo.dto.SolicitudDTO;
import pe.edu.uni.apiordentrabajo.entity.Solicitud;

@Mapper
public interface SolicitudMapper {
	SolicitudDTO convertirEntityADTO(Solicitud solicitudEntity);
	Solicitud convertiyDTOAEntity(SolicitudDTO solicitudDTO);
	List<SolicitudDTO> convertitEntity(List<Solicitud> solicitudEntityList);
	List<Solicitud> convertitDTOAEntity(List<SolicitudDTO> solicitudDTOList);
}
