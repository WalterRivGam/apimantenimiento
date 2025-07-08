package pe.edu.uni.apiordentrabajo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.uni.apiordentrabajo.dto.MaterialDTO;
import pe.edu.uni.apiordentrabajo.dto.PersonalParticipaDTO;

@Entity
@Table(name = "t_orden_trabajo")
@NoArgsConstructor
@Data
public class OrdenTrabajo {
	@Id
	@Column(name = "n_id_orden_trabajo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idOrdenTrabajo;

	@Column(name = "v_nro_expediente")
	private String nroExpediente;

	@Column(name = "v_anio_expediente")
	private String anioExpediente;

	@Column(name = "n_es_emergencia")
	private Boolean esEmergencia;

	@Column(name = "v_dependencia")
	private String dependencia;

	@Column(name = "v_subdependencia")
	private String subdependencia;

	@Column(name = "v_nro_documento")
	private String nroDocumento;

	@Column(name = "d_fecha_dependencia")
	private LocalDate fechaDependencia;

	@Column(name = "v_taller")
	private String taller;

	@Column(name = "v_nro_orden_trabajo")
	private String nroOrdenTrabajo;

	@Column(name = "d_fecha_recepcion_am")
	private LocalDate fechaRecepcionAm;

	@Column(name = "d_fecha_recepcion_usg")
	private LocalDate fechaRecepcionUsg;

	@Column(name = "v_tipo_trabajo")
	private String tipoTrabajo;

	@Column(name = "v_trabajo_solicitado")
	private String trabajoSolicitado;

	@Column(name = "d_fecha_recepcion_taller")
	private LocalDate fechaRecepcionTaller;

	@Column(name = "d_fecha_atencion_taller")
	private LocalDate fechaAtencionTaller;

	@Column(name = "d_fecha_trabajo_realizado")
	private LocalDate fechaTrabajoRealizado;

	@Column(name = "v_diagnostico")
	private String diagnostico;

	@Column(name = "v_observaciones")
	private String observaciones;

	@Column(name = "v_ip_registro")
	private String ipRegistro;

	@Column(name = "v_usuario_registro")
	private String usuarioRegistro;

	@Column(name = "d_fecha_registro")
	private LocalDateTime fechaRegistro;

	@Column(name = "v_ip_actualizacion")
	private String ipActualizacion;

	@Column(name = "v_usuario_actualizacion")
	private String usuarioActualizacion;

	@Column(name = "d_fecha_actualizacion")
	private LocalDateTime fechaActualizacion;

	@Column(name = "n_flag_registro_eliminado")
	private Boolean flagRegistroEliminado;

	@Column(name = "n_id_solicitud")
	private Integer idSolicitud;
	
	@Column(name = "v_tecnico")
	private String tecnico;
	
	@Transient
	private List<MaterialDTO> materiales;
	
	@Transient
	private List<PersonalParticipaDTO> personalParticipa;

}
