package pe.edu.uni.apiordentrabajo.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.apiordentrabajo.dto.MaterialDTO;
import pe.edu.uni.apiordentrabajo.dto.OrdenTrabajoDTO;
import pe.edu.uni.apiordentrabajo.dto.PersonalParticipaDTO;
import pe.edu.uni.apiordentrabajo.entity.Material;
import pe.edu.uni.apiordentrabajo.entity.OrdenTrabajo;
import pe.edu.uni.apiordentrabajo.entity.OrdenTrabajoCorrelativo;
import pe.edu.uni.apiordentrabajo.entity.PersonalParticipa;
import pe.edu.uni.apiordentrabajo.mapper.MaterialMapper;
import pe.edu.uni.apiordentrabajo.mapper.OrdenTrabajoMapper;
import pe.edu.uni.apiordentrabajo.mapper.PersonalParticipaMapper;
import pe.edu.uni.apiordentrabajo.repository.MaterialRepository;
import pe.edu.uni.apiordentrabajo.repository.OrdenTrabajoCorrelativoRepository;
import pe.edu.uni.apiordentrabajo.repository.OrdenTrabajoRespository;
import pe.edu.uni.apiordentrabajo.repository.PersonalParticipaRepository;
import pe.edu.uni.apiordentrabajo.service.OrdenTrabajoService;

@Service
public class OrdenTrabajoServiceImpl implements OrdenTrabajoService {

	private OrdenTrabajoRespository ordenTrabajoRespository;
	private PersonalParticipaRepository personalParticipaRepository;
	private MaterialRepository materialRepository;
	private OrdenTrabajoCorrelativoRepository ordenTrabajoCorrelativoRepository;

	private OrdenTrabajoMapper ordenTrabajoMapper = Mappers.getMapper(OrdenTrabajoMapper.class);
	private PersonalParticipaMapper personalParticipaMapper = Mappers.getMapper(PersonalParticipaMapper.class);
	private MaterialMapper materialMapper = Mappers.getMapper(MaterialMapper.class);

	public OrdenTrabajoServiceImpl(OrdenTrabajoRespository ordenTrabajoRespository,
			PersonalParticipaRepository personalParticipaRepository, MaterialRepository materialRepository,
			OrdenTrabajoCorrelativoRepository ordenTrabajoCorrelativoRepository) {
		this.ordenTrabajoRespository = ordenTrabajoRespository;
		this.personalParticipaRepository = personalParticipaRepository;
		this.materialRepository = materialRepository;
		this.ordenTrabajoCorrelativoRepository = ordenTrabajoCorrelativoRepository;
	}

	@Override
	@Transactional
	public OrdenTrabajoDTO registrarOrden(OrdenTrabajoDTO ordenTrabajoDTO) {
		List<PersonalParticipaDTO> personalParticipaDTO = ordenTrabajoDTO.getPersonalParticipa();
		List<MaterialDTO> materialesDTO = ordenTrabajoDTO.getMateriales();

		OrdenTrabajo ordenTrabajo = ordenTrabajoMapper.convertirDTOAEntity(ordenTrabajoDTO);
		List<PersonalParticipa> personalParticipa = personalParticipaMapper.convertirDTOAEntity(personalParticipaDTO);
		List<Material> materiales = materialMapper.convertirDTOAEntity(materialesDTO);

		if (ordenTrabajoDTO.getIdOrdenTrabajo() != null) {
			Optional<OrdenTrabajo> ordenTrabajoOpt = ordenTrabajoRespository
					.findById(ordenTrabajoDTO.getIdOrdenTrabajo());
			if (ordenTrabajoOpt.isPresent()) {
				OrdenTrabajo ordenTrabajoActual = ordenTrabajoOpt.get();
				llenarDatos(ordenTrabajo, ordenTrabajoActual);
			}
		}

		String nroOrdenTrabajo = ordenTrabajo.getNroOrdenTrabajo();

		if (nroOrdenTrabajo == null || nroOrdenTrabajo.trim().equals("")) {
			nroOrdenTrabajo = generarNroOrdenTrabajo(ordenTrabajo.getTaller());
		}

		ordenTrabajo.setNroOrdenTrabajo(nroOrdenTrabajo);

		ordenTrabajo = ordenTrabajoRespository.save(ordenTrabajo);

		if (personalParticipa != null) {
			for (PersonalParticipa persona : personalParticipa) {
				if (persona.getFlagRegistroEliminado() == null) {
					persona.setFlagRegistroEliminado(false);
				}
				persona.setIdOrdenTrabajo(ordenTrabajo.getIdOrdenTrabajo());
			}
			personalParticipaRepository.saveAll(personalParticipa);
		}

		if (materiales != null) {
			for (Material material : materiales) {
				if (material.getFlagRegistroEliminado() == null) {
					material.setFlagRegistroEliminado(false);
				}
				material.setIdOrdenTrabajo(ordenTrabajo.getIdOrdenTrabajo());
			}
			materialRepository.saveAll(materiales);
		}

		ordenTrabajoDTO = ordenTrabajoMapper.convertirEntityADTO(ordenTrabajo);
		personalParticipaDTO = personalParticipaMapper.convertirEntityADTO(personalParticipa);
		materialesDTO = materialMapper.convertirEntityADTO(materiales);

		ordenTrabajoDTO.setPersonalParticipa(personalParticipaDTO);
		ordenTrabajoDTO.setMateriales(materialesDTO);

		return ordenTrabajoDTO;
	}

	@Transactional
	public String generarNroOrdenTrabajo(String taller) {
		int anio = LocalDate.now().getYear();
		String tallerCodigo = taller.toUpperCase().substring(0, 4); // Modificar por siglas de taller

		Integer ultimoNumero;

		// Buscar el correlativo actual para ese año y taller
		Optional<OrdenTrabajoCorrelativo> correlativoOpt = ordenTrabajoCorrelativoRepository
				.findByAnioAndTallerCodigo(anio, tallerCodigo);

		if (correlativoOpt.isPresent()) {
			OrdenTrabajoCorrelativo correlativo = correlativoOpt.get();
			ultimoNumero = correlativo.getUltimoNumero() + 1;
			correlativo.setUltimoNumero(ultimoNumero);
			ordenTrabajoCorrelativoRepository.save(correlativo);
		} else {
			ultimoNumero = 1;
			OrdenTrabajoCorrelativo nuevo = new OrdenTrabajoCorrelativo(anio, tallerCodigo, ultimoNumero);
			ordenTrabajoCorrelativoRepository.save(nuevo);
		}

		// Formato final: 2025-TALL-0001
		return String.format("%d-%s-%07d", anio, tallerCodigo, ultimoNumero);
	}

	/**
	 * Llena los datos null de ordenTrabajo con los datos de ordenTrabajoActual
	 * 
	 * @param ordenTrabajo       orden de trabajo con los nuevos datos
	 * @param ordenTrabajoActual orden de trabajo con los datos almacenados
	 *                           actualmente en la BD
	 */
	private void llenarDatos(OrdenTrabajo ordenTrabajo, OrdenTrabajo ordenTrabajoActual) {
		if (ordenTrabajo.getNroExpediente() == null) {
			ordenTrabajo.setNroExpediente(ordenTrabajoActual.getNroExpediente());
		}
		if (ordenTrabajo.getAnioExpediente() == null) {
			ordenTrabajo.setAnioExpediente(ordenTrabajoActual.getAnioExpediente());
		}
		if (ordenTrabajo.getEsEmergencia() == null) {
			ordenTrabajo.setEsEmergencia(ordenTrabajoActual.getEsEmergencia());
		}
		if (ordenTrabajo.getDependencia() == null) {
			ordenTrabajo.setDependencia(ordenTrabajoActual.getDependencia());
		}
		if (ordenTrabajo.getSubdependencia() == null) {
			ordenTrabajo.setSubdependencia(ordenTrabajoActual.getSubdependencia());
		}
		if (ordenTrabajo.getNroDocumento() == null) {
			ordenTrabajo.setNroDocumento(ordenTrabajoActual.getNroDocumento());
		}
		if (ordenTrabajo.getFechaDependencia() == null) {
			ordenTrabajo.setFechaDependencia(ordenTrabajoActual.getFechaDependencia());
		}
		if (ordenTrabajo.getTaller() == null) {
			ordenTrabajo.setTaller(ordenTrabajoActual.getTaller());
		}
		if (ordenTrabajo.getNroOrdenTrabajo() == null) {
			ordenTrabajo.setNroOrdenTrabajo(ordenTrabajoActual.getNroOrdenTrabajo());
		}
		if (ordenTrabajo.getFechaRecepcionAm() == null) {
			ordenTrabajo.setFechaRecepcionAm(ordenTrabajoActual.getFechaRecepcionAm());
		}
		if (ordenTrabajo.getFechaRecepcionUsg() == null) {
			ordenTrabajo.setFechaRecepcionUsg(ordenTrabajoActual.getFechaRecepcionUsg());
		}
		if (ordenTrabajo.getTipoTrabajo() == null) {
			ordenTrabajo.setTipoTrabajo(ordenTrabajoActual.getTipoTrabajo());
		}
		if (ordenTrabajo.getTrabajoSolicitado() == null) {
			ordenTrabajo.setTrabajoSolicitado(ordenTrabajoActual.getTrabajoSolicitado());
		}
		if (ordenTrabajo.getFechaRecepcionTaller() == null) {
			ordenTrabajo.setFechaRecepcionTaller(ordenTrabajoActual.getFechaRecepcionTaller());
		}
		if (ordenTrabajo.getFechaAtencionTaller() == null) {
			ordenTrabajo.setFechaAtencionTaller(ordenTrabajoActual.getFechaAtencionTaller());
		}
		if (ordenTrabajo.getFechaTrabajoRealizado() == null) {
			ordenTrabajo.setFechaTrabajoRealizado(ordenTrabajoActual.getFechaTrabajoRealizado());
		}
		if (ordenTrabajo.getDiagnostico() == null) {
			ordenTrabajo.setDiagnostico(ordenTrabajoActual.getDiagnostico());
		}
		if (ordenTrabajo.getObservaciones() == null) {
			ordenTrabajo.setObservaciones(ordenTrabajoActual.getObservaciones());
		}
		if (ordenTrabajo.getIpRegistro() == null) {
			ordenTrabajo.setIpRegistro(ordenTrabajoActual.getIpRegistro());
		}
		if (ordenTrabajo.getUsuarioRegistro() == null) {
			ordenTrabajo.setUsuarioRegistro(ordenTrabajoActual.getUsuarioRegistro());
		}
		if (ordenTrabajo.getFechaRegistro() == null) {
			ordenTrabajo.setFechaRegistro(ordenTrabajoActual.getFechaRegistro());
		}
		if (ordenTrabajo.getIpActualizacion() == null) {
			ordenTrabajo.setIpActualizacion(ordenTrabajoActual.getIpActualizacion());
		}
		if (ordenTrabajo.getUsuarioActualizacion() == null) {
			ordenTrabajo.setUsuarioActualizacion(ordenTrabajoActual.getUsuarioActualizacion());
		}
		if (ordenTrabajo.getFechaActualizacion() == null) {
			ordenTrabajo.setFechaActualizacion(ordenTrabajoActual.getFechaActualizacion());
		}
		if (ordenTrabajo.getFlagRegistroEliminado() == null) {
			ordenTrabajo.setFlagRegistroEliminado(ordenTrabajoActual.getFlagRegistroEliminado());
		}
		if (ordenTrabajo.getIdSolicitud() == null) {
			ordenTrabajo.setIdSolicitud(ordenTrabajoActual.getIdSolicitud());
		}
	}

	@Override
	public Optional<OrdenTrabajoDTO> obtenerOrden(Integer idOrdenTrabajo) {
		Optional<OrdenTrabajo> ordenTrabajoOpt = ordenTrabajoRespository.findById(idOrdenTrabajo);

		if (ordenTrabajoOpt.isPresent()) {
			OrdenTrabajo ordenTrabajo = ordenTrabajoOpt.get();
			OrdenTrabajoDTO ordenTrabajoDTO = ordenTrabajoMapper.convertirEntityADTO(ordenTrabajo);

			List<Material> materiales = materialRepository.findByIdOrdenTrabajo(idOrdenTrabajo);
			List<MaterialDTO> materialesDTO = materialMapper.convertirEntityADTO(materiales);

			List<PersonalParticipa> personalParticipa = personalParticipaRepository
					.findByIdOrdenTrabajo(idOrdenTrabajo);
			List<PersonalParticipaDTO> personalParticipaDTO = personalParticipaMapper
					.convertirEntityADTO(personalParticipa);

			ordenTrabajoDTO.setMateriales(materialesDTO);
			ordenTrabajoDTO.setPersonalParticipa(personalParticipaDTO);

			return Optional.of(ordenTrabajoDTO);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<OrdenTrabajoDTO> obtenerOrden(String nroOrden) {
		Optional<OrdenTrabajo> ordenTrabajoOpt = ordenTrabajoRespository.findByNroOrdenTrabajo(nroOrden);

		if (ordenTrabajoOpt.isPresent()) {

			OrdenTrabajo ordenTrabajo = ordenTrabajoOpt.get();
			Integer idOrdenTrabajo = ordenTrabajo.getIdOrdenTrabajo();
			OrdenTrabajoDTO ordenTrabajoDTO = ordenTrabajoMapper.convertirEntityADTO(ordenTrabajo);

			List<Material> materiales = materialRepository.findByIdOrdenTrabajo(idOrdenTrabajo);
			List<MaterialDTO> materialesDTO = materialMapper.convertirEntityADTO(materiales);

			List<PersonalParticipa> personalParticipa = personalParticipaRepository
					.findByIdOrdenTrabajo(idOrdenTrabajo);
			List<PersonalParticipaDTO> personalParticipaDTO = personalParticipaMapper
					.convertirEntityADTO(personalParticipa);

			ordenTrabajoDTO.setMateriales(materialesDTO);
			ordenTrabajoDTO.setPersonalParticipa(personalParticipaDTO);

			return Optional.of(ordenTrabajoDTO);
		} else {
			return Optional.empty();
		}
	}

	@Override
	@Transactional
	public void eliminarOrden(Integer id) {
		ordenTrabajoRespository.eliminarPorIdOrdenTrabajo(id);
		materialRepository.eliminarPorIdOrdenTrabajo(id);
		personalParticipaRepository.eliminarPorIdOrdenTrabajo(id);
	}

	@Override
	public List<OrdenTrabajoDTO> obtenerOrdenes() {
		List<OrdenTrabajo> ordenesTrabajo = ordenTrabajoRespository.findAll();

		List<OrdenTrabajoDTO> ordenesTrabajoDTO = new ArrayList<>();

		for (OrdenTrabajo ordenTrabajo : ordenesTrabajo) {
			Integer idOrdenTrabajo = ordenTrabajo.getIdOrdenTrabajo();
			OrdenTrabajoDTO ordenTrabajoDTO = ordenTrabajoMapper.convertirEntityADTO(ordenTrabajo);

			List<Material> materiales = materialRepository.findByIdOrdenTrabajo(idOrdenTrabajo);
			List<MaterialDTO> materialesDTO = materialMapper.convertirEntityADTO(materiales);

			List<PersonalParticipa> personalParticipa = personalParticipaRepository
					.findByIdOrdenTrabajo(idOrdenTrabajo);
			List<PersonalParticipaDTO> personalParticipaDTO = personalParticipaMapper
					.convertirEntityADTO(personalParticipa);

			ordenTrabajoDTO.setMateriales(materialesDTO);
			ordenTrabajoDTO.setPersonalParticipa(personalParticipaDTO);

			ordenesTrabajoDTO.add(ordenTrabajoDTO);
		}

		return ordenesTrabajoDTO;
	}
}
