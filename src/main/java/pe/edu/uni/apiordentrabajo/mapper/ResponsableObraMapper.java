package pe.edu.uni.apiordentrabajo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import pe.edu.uni.apiordentrabajo.dto.ResponsableObraDTO;
import pe.edu.uni.apiordentrabajo.entity.ResponsableObra;

@Mapper
public interface ResponsableObraMapper {
	ResponsableObraDTO convertirEntityADTO(ResponsableObra responsableObraEntity);
	ResponsableObra convertirDTOAEntity(ResponsableObraDTO responsableObraDTO);
	List<ResponsableObraDTO> convertitEntityADTO(List<ResponsableObra> responsableObraEntityList);
	List<ResponsableObra> convertirDTOAEntity(List<ResponsableObraDTO> responsableObraDTOList);
}
