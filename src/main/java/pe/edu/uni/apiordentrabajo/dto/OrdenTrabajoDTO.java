package pe.edu.uni.apiordentrabajo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.uni.apiordentrabajo.entity.Solicitud;

@Data
@NoArgsConstructor
public class OrdenTrabajoDTO {
	private Integer idOrdenTrabajo;
	private String nroExpediente;
	private String anioExpediente;
	private Boolean esEmergencia;
	private String dependencia;
	private String subdependencia;
	private String nroDocumento;
	private LocalDate fechaDependencia;
	private String taller;
	private String nroOrdenTrabajo;
	private LocalDate fechaRecepcionAm;
	private LocalDate fechaRecepcionUsg;
	private String tipoTrabajo;
	private String trabajoSolicitado;
	private LocalDate fechaRecepcionTaller;
	private LocalDate fechaAtencionTaller;
	private LocalDate fechaTrabajoRealizado;
	private String diagnostico;
	private String observaciones;
	private String ipRegistro;
	private String usuarioRegistro;
	private LocalDateTime fechaRegistro;
	private String ipActualizacion;
	private String usuarioActualizacion;
	private LocalDateTime fechaActualizacion;
	private Boolean flagRegistroEliminado;
	private Integer idSolicitud;
	private List<MaterialDTO> materiales;
	private List<PersonalParticipaDTO> personalParticipa;
	private String tecnico;
}
