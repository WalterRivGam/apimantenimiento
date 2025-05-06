package pe.edu.uni.apiordentrabajo.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
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

@Service
public class OrdenTrabajoServiceImpl implements OrdenTrabajoService {

	private OrdenTrabajoRespository ordenTrabajoRespository;
	private PersonalParticipaRepository personalParticipaRepository;
	private ResponsableObraRepository responsableObraRepository;
	private MaterialRepository materialRepository;

	private OrdenTrabajoMapper ordenTrabajoMapper = Mappers.getMapper(OrdenTrabajoMapper.class);
	private PersonalParticipaMapper personalParticipaMapper = Mappers.getMapper(PersonalParticipaMapper.class);
	private ResponsableObraMapper responsableObraMapper = Mappers.getMapper(ResponsableObraMapper.class);
	private MaterialMapper materialMapper = Mappers.getMapper(MaterialMapper.class);

	public OrdenTrabajoServiceImpl(OrdenTrabajoRespository ordenTrabajoRespository,
			PersonalParticipaRepository personalParticipaRepository,
			ResponsableObraRepository responsableObraRepository, MaterialRepository materialRepository) {
		this.ordenTrabajoRespository = ordenTrabajoRespository;
		this.personalParticipaRepository = personalParticipaRepository;
		this.responsableObraRepository = responsableObraRepository;
		this.materialRepository = materialRepository;
	}

	@Override
	@Transactional
	public OrdenTrabajoDTO registrarOrden(OrdenTrabajoDTO ordenTrabajoDTO) {
		
		if(ordenTrabajoDTO.getIdOrdenTrabajo() == null) {
			ordenTrabajoDTO.setFechaRegistro(LocalDateTime.now());
		} else {
			ordenTrabajoDTO.setFechaActualizacion(LocalDateTime.now());
		}

		List<PersonalParticipaDTO> personalParticipaDTO = ordenTrabajoDTO.getPersonalParticipa();
		List<ResponsableObraDTO> responsablesObraDTO = ordenTrabajoDTO.getResponsablesObra();
		List<MaterialDTO> materialesDTO = ordenTrabajoDTO.getMateriales();

		OrdenTrabajo ordenTrabajo = ordenTrabajoMapper.convertirDTOAEntity(ordenTrabajoDTO);
		List<PersonalParticipa> personalParticipa = personalParticipaMapper.convertirDTOAEntity(personalParticipaDTO);
		List<ResponsableObra> responsablesObra = responsableObraMapper.convertirDTOAEntity(responsablesObraDTO);
		List<Material> materiales = materialMapper.convertirDTOAEntity(materialesDTO);
		
		if(ordenTrabajoDTO.getIdOrdenTrabajo() != null) {
			Optional<OrdenTrabajo> ordenTrabajoOpt = ordenTrabajoRespository.findById(ordenTrabajoDTO.getIdOrdenTrabajo());
			if(ordenTrabajoOpt.isPresent()) {
				OrdenTrabajo ordenTrabajoActual = ordenTrabajoOpt.get();
				llenarDatos(ordenTrabajo, ordenTrabajoActual);
			}
		}
		
		ordenTrabajo = ordenTrabajoRespository.save(ordenTrabajo);
		
		if(personalParticipa != null) {
			for(PersonalParticipa persona : personalParticipa) {
				persona.setIdOrdenTrabajo(ordenTrabajo.getIdOrdenTrabajo());
			}
			personalParticipaRepository.saveAll(personalParticipa);
		}
		
		if(responsablesObra != null) {
			for(ResponsableObra responsableObra : responsablesObra) {
				responsableObra.setIdOrdenTrabajo(ordenTrabajo.getIdOrdenTrabajo());
			}
			responsableObraRepository.saveAll(responsablesObra);
		}
		
		if(materiales != null) {
			for(Material material : materiales) {
				material.setIdOrdenTrabajo(ordenTrabajo.getIdOrdenTrabajo());
			}
			materialRepository.saveAll(materiales);
		}

		ordenTrabajoDTO = ordenTrabajoMapper.convertirEntityADTO(ordenTrabajo);
		personalParticipaDTO = personalParticipaMapper.convertirEntityADTO(personalParticipa);
		responsablesObraDTO = responsableObraMapper.convertitEntityADTO(responsablesObra);
		materialesDTO = materialMapper.convertirEntityADTO(materiales);

		ordenTrabajoDTO.setPersonalParticipa(personalParticipaDTO);
		ordenTrabajoDTO.setResponsablesObra(responsablesObraDTO);
		ordenTrabajoDTO.setMateriales(materialesDTO);

		return ordenTrabajoDTO;

	}
	
	/**
	 * Llena los datos null de ordenTrabajo con los datos de ordenTrabajoActual
	 * @param ordenTrabajo orden de trabajo con los nuevos datos
	 * @param ordenTrabajoActual orden de trabajo con los datos almacenados actualmente en la BD
	 */
	private void llenarDatos(OrdenTrabajo ordenTrabajo, OrdenTrabajo ordenTrabajoActual) {
		if(ordenTrabajo.getNroExpediente() == null) {
			ordenTrabajo.setNroExpediente(ordenTrabajoActual.getNroExpediente());
		}
		if(ordenTrabajo.getAnioExpediente() == null) {
			ordenTrabajo.setAnioExpediente(ordenTrabajoActual.getAnioExpediente());
		}
		if(ordenTrabajo.getEsEmergencia() == null) {
			ordenTrabajo.setEsEmergencia(ordenTrabajoActual.getEsEmergencia());
		}
		if(ordenTrabajo.getDependencia() == null) {
			ordenTrabajo.setDependencia(ordenTrabajoActual.getDependencia());
		}
		if(ordenTrabajo.getSubdependencia() == null) {
			ordenTrabajo.setSubdependencia(ordenTrabajoActual.getSubdependencia());
		}
		if(ordenTrabajo.getNroDocumento() == null) {
			ordenTrabajo.setNroDocumento(ordenTrabajoActual.getNroDocumento());
		}
		if(ordenTrabajo.getFechaDependencia() == null) {
			ordenTrabajo.setFechaDependencia(ordenTrabajoActual.getFechaDependencia());
		}
		if(ordenTrabajo.getTaller() == null) {
			ordenTrabajo.setTaller(ordenTrabajoActual.getTaller());
		}
		if(ordenTrabajo.getNroOrdenTrabajo() == null) {
			ordenTrabajo.setNroOrdenTrabajo(ordenTrabajoActual.getNroOrdenTrabajo());
		}
		if(ordenTrabajo.getFechaRecepcionAm() == null) {
			ordenTrabajo.setFechaRecepcionAm(ordenTrabajoActual.getFechaRecepcionAm());
		}
		if(ordenTrabajo.getFechaRecepcionUsg() == null) {
			ordenTrabajo.setFechaRecepcionUsg(ordenTrabajoActual.getFechaRecepcionUsg());
		}
		if(ordenTrabajo.getTipoTrabajo() == null) {
			ordenTrabajo.setTipoTrabajo(ordenTrabajoActual.getTipoTrabajo());
		}
		if(ordenTrabajo.getTrabajoSolicitado() == null) {
			ordenTrabajo.setTrabajoSolicitado(ordenTrabajoActual.getTrabajoSolicitado());
		}
		if(ordenTrabajo.getFechaRecepcionTaller() == null) {
			ordenTrabajo.setFechaRecepcionTaller(ordenTrabajoActual.getFechaRecepcionTaller());
		}
		if(ordenTrabajo.getFechaAtencionTaller() == null) {
			ordenTrabajo.setFechaAtencionTaller(ordenTrabajoActual.getFechaAtencionTaller());
		}
		if(ordenTrabajo.getFechaTrabajoRealizado() == null) {
			ordenTrabajo.setFechaTrabajoRealizado(ordenTrabajoActual.getFechaTrabajoRealizado());
		}
		if(ordenTrabajo.getDiagnostico() == null) {
			ordenTrabajo.setDiagnostico(ordenTrabajoActual.getDiagnostico());
		}
		if(ordenTrabajo.getObservaciones() == null) {
			ordenTrabajo.setObservaciones(ordenTrabajoActual.getObservaciones());
		}
		if(ordenTrabajo.getIpRegistro() == null) {
			ordenTrabajo.setIpRegistro(ordenTrabajoActual.getIpRegistro());
		}
		if(ordenTrabajo.getUsuarioRegistro() == null) {
			ordenTrabajo.setUsuarioRegistro(ordenTrabajoActual.getUsuarioRegistro());
		}
		if(ordenTrabajo.getFechaRegistro() == null) {
			ordenTrabajo.setFechaRegistro(ordenTrabajoActual.getFechaRegistro());
		}
		if(ordenTrabajo.getIpActualizacion() == null) {
			ordenTrabajo.setIpActualizacion(ordenTrabajoActual.getIpActualizacion());
		}
		if(ordenTrabajo.getUsuarioActualizacion() == null) {
			ordenTrabajo.setUsuarioActualizacion(ordenTrabajoActual.getUsuarioActualizacion());
		}
		if(ordenTrabajo.getFechaActualizacion() == null) {
			ordenTrabajo.setFechaActualizacion(ordenTrabajoActual.getFechaActualizacion());
		}
		if(ordenTrabajo.getFlagRegistroEliminado() == null) {
			ordenTrabajo.setFlagRegistroEliminado(ordenTrabajoActual.getFlagRegistroEliminado());
		}
		if(ordenTrabajo.getIdSolicitud() == null) {
			ordenTrabajo.setIdSolicitud(ordenTrabajoActual.getIdSolicitud());
		}
	}
}
