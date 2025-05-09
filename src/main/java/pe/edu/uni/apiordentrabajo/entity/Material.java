package pe.edu.uni.apiordentrabajo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_material")
@NoArgsConstructor
@Data
public class Material {
	@Id
	@Column(name = "n_id_material")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idMaterial;
	
	@Column(name = "v_descripcion")
	private String descripcion;
	
	@Column(name = "v_unidad")
	private String unidad;
	
	@Column(name = "n_cantidad")
	private Integer cantidad;
	
	@Column(name = "v_valor")
	private String valor;
	
	@Column(name = "n_id_orden_trabajo")
	private Integer idOrdenTrabajo;
	
	@Column(name = "n_flag_registro_eliminado")
	private Boolean flagRegistroEliminado;
	
	/*
	@ManyToOne
	@JoinColumn(name = "n_id_orden_trabajo")
	private OrdenTrabajo ordenTrabajo;
	*/
}
