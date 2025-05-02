package pe.edu.uni.apiordentrabajo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import pe.edu.uni.apiordentrabajo.dto.PersonalParticipaDTO;
import pe.edu.uni.apiordentrabajo.entity.PersonalParticipa;

@Mapper
public interface PersonalParticipaMapper {
	PersonalParticipaDTO convertirEntityADTO(PersonalParticipa personalParticipaEntity);
	PersonalParticipa convertirDTOAEntity(PersonalParticipaDTO personalParticipaDTO);
	List<PersonalParticipaDTO> convertirEntityADTO(List<PersonalParticipa> personalParticipaEntityList);
	List<PersonalParticipa> convertirDTOAEntity(List<PersonalParticipaDTO> personalParticipaDTOList);
}
