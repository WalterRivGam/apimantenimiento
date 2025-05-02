package pe.edu.uni.apiordentrabajo.service.impl;

import java.util.List;



import org.mapstruct.factory.Mappers;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.apiordentrabajo.dto.MaterialDTO;
import pe.edu.uni.apiordentrabajo.dto.OrdenTrabajoDTO;
import pe.edu.uni.apiordentrabajo.dto.PersonalParticipaDTO;
import pe.edu.uni.apiordentrabajo.dto.ResponsableObraDTO;
import pe.edu.uni.apiordentrabajo.entity.Material;
import pe.edu.uni.apiordentrabajo.entity.OrdenTrabajo;
import pe.edu.uni.apiordentrabajo.entity.PersonalParticipa;
import pe.edu.uni.apiordentrabajo.entity.ResponsableObra;
import pe.edu.uni.apiordentrabajo.mapper.MaterialMapper;
import pe.edu.uni.apiordentrabajo.mapper.OrdenTrabajoMapper;
import pe.edu.uni.apiordentrabajo.mapper.PersonalParticipaMapper;
import pe.edu.uni.apiordentrabajo.mapper.ResponsableObraMapper;
import pe.edu.uni.apiordentrabajo.repository.MaterialRepository;
import pe.edu.uni.apiordentrabajo.repository.OrdenTrabajoRespository;
import pe.edu.uni.apiordentrabajo.repository.PersonalParticipaRepository;
import pe.edu.uni.apiordentrabajo.repository.ResponsableObraRepository;
import pe.edu.uni.apiordentrabajo.service.OrdenTrabajoService;

public class OrdenTrabajoServiceImpl implements OrdenTrabajoService{
	
	private OrdenTrabajoRespository ordenTrabajoRespository;
	private PersonalParticipaRepository personalParticipaRepository;
	private ResponsableObraRepository responsableObraRepository;
	private MaterialRepository materialRepository;
	
	private OrdenTrabajoMapper ordenTrabajoMapper = Mappers.getMapper(OrdenTrabajoMapper.class);
	private PersonalParticipaMapper personalParticipaMapper = Mappers.getMapper(PersonalParticipaMapper.class);
	private ResponsableObraMapper responsableObraMapper = Mappers.getMapper(ResponsableObraMapper.class);
	private MaterialMapper materialMapper = Mappers.getMapper(MaterialMapper.class);
	
	public OrdenTrabajoServiceImpl(OrdenTrabajoRespository ordenTrabajoRespository, PersonalParticipaRepository personalParticipaRepository,
			ResponsableObraRepository responsableObraRepository, MaterialRepository materialRepository) {
		this.ordenTrabajoRespository = ordenTrabajoRespository;
		this.personalParticipaRepository = personalParticipaRepository;
		this.responsableObraRepository = responsableObraRepository;
		this.materialRepository = materialRepository;
	}

	@Override
	@Transactional
	public OrdenTrabajoDTO registrarOrden(OrdenTrabajoDTO ordenTrabajoDTO) {
		
		List<PersonalParticipaDTO> personalParticipaDTO = ordenTrabajoDTO.getPersonalParticipa();
		List<ResponsableObraDTO> responsablesObraDTO = ordenTrabajoDTO.getResponsablesObra();
		List<MaterialDTO> materialesDTO = ordenTrabajoDTO.getMateriales();
		
		OrdenTrabajo ordenTrabajo = ordenTrabajoMapper.convertirDTOAEntity(ordenTrabajoDTO);
		List<PersonalParticipa> personalParticipa = personalParticipaMapper.convertirDTOAEntity(personalParticipaDTO);
		List<ResponsableObra> responsablesObra = responsableObraMapper.convertirDTOAEntity(responsablesObraDTO);
		List<Material> materiales = materialMapper.convertirDTOAEntity(materialesDTO);
		
		
		ordenTrabajo = ordenTrabajoRespository.save(ordenTrabajo);
		personalParticipa = personalParticipaRepository.saveAll(personalParticipa);
		responsablesObra = responsableObraRepository.saveAll(responsablesObra);
		materiales = materialRepository.saveAll(materiales);
		
		ordenTrabajoDTO = ordenTrabajoMapper.convertirEntityADTO(ordenTrabajo);
		personalParticipaDTO = personalParticipaMapper.convertirEntityADTO(personalParticipa);
		responsablesObraDTO = responsableObraMapper.convertitEntityADTO(responsablesObra);
		materialesDTO = materialMapper.convertirEntityADTO(materiales);
		
		ordenTrabajoDTO.setPersonalParticipa(personalParticipaDTO);
		ordenTrabajoDTO.setResponsablesObra(responsablesObraDTO);
		ordenTrabajoDTO.setMateriales(materialesDTO);
		
		return ordenTrabajoDTO;
	}

}
