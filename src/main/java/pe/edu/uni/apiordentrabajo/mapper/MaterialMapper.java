package pe.edu.uni.apiordentrabajo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import pe.edu.uni.apiordentrabajo.dto.MaterialDTO;
import pe.edu.uni.apiordentrabajo.entity.Material;

@Mapper
public interface MaterialMapper {
	MaterialDTO convertirEntityADTO(Material material);
	Material convertirDTOAEntity(MaterialDTO materialDTO);
	List<MaterialDTO> convertirEntityADTO(List<Material> materiales);
	List<Material> convertirDTOAEntity(List<MaterialDTO> materialDTOList);
}
