package pe.edu.uni.apiordentrabajo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_responsable_obra")
@NoArgsConstructor
@Data
public class ResponsableObra {
	@Id
	@Column(name = "n_id_responsable_obra")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idResponsableObra;
	
	@Column(name = "v_nombres_completos")
	private String nombresCompletos;
	
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
}
