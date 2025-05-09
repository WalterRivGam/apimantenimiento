package pe.edu.uni.apiordentrabajo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_personal_participa")
@NoArgsConstructor
@Data
public class PersonalParticipa {
	
	@Id
	@Column(name = "n_id_personal_participa")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPersonalParticipa;
	
	@Column(name = "d_fecha_inicio")
	private LocalDate fechaInicio;
	
	@Column(name = "v_nombres_completos")
	private String nombresCompletos;
	
	@Column(name = "n_dias")
	private Integer dias;
	
	@Column(name = "n_horas")
	private Integer horas;
	
	@Column(name = "n_id_orden_trabajo")
	private Integer idOrdenTrabajo;
	
	/*
	@ManyToOne
	@JoinColumn(name = "n_id_orden_trabajo")
	private OrdenTrabajo ordenTrabajo;
	*/
	
	@Column(name = "v_tipo_documento")
	private String tipoDocumento;
	
	@Column(name = "v_nro_documento")
	private String nroDocumento;
	
	@Column(name = "n_flag_registro_eliminado")
	private Boolean flagRegistroEliminado;
}
