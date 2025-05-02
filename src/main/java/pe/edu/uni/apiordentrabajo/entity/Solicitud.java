package pe.edu.uni.apiordentrabajo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_solicitud")
@NoArgsConstructor
@Data
public class Solicitud {

	@Id
	@Column(name = "i_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "i_anio")
	private String anio;

	@Column(name = "v_nro_ticket")
	private String nroTicket;

	@Column(name = "v_bien_servicio")
	private String bienServicio;

	@Column(name = "v_servicio_requerido")
	private String servicioRequerido;

	@Column(name = "v_prioridad")
	private String prioridad;

	@Column(name = "v_descripcion")
	private String descripcion;

	@Column(name = "v_estado")
	private String estado;

	@Column(name = "i_id_usuario")
	private Integer idUsuario;

	@Column(name = "v_sede")
	private String sede;

	@Column(name = "v_sector")
	private String sector;

	@Column(name = "v_edificio")
	private String edificio;

	@Column(name = "v_nivel")
	private String nivel;

	@Column(name = "v_espacio")
	private String espacio;

	@Column(name = "v_desc_esp")
	private String descespacio;

	@Column(name = "v_tipo_accion")
	private String tipoAccion;

	@Column(name = "v_tipo_documento")
	private String tipoDocumento;

	@Column(name = "c_ip")
	private String ip;

	@Column(name = "v_nro_expediente")
	private String nroExpediente;

	@Column(name = "d_fecha_creacion")
	private LocalDate fechaCreacion;

	@Column(name = "d_fecha_actualizacion")
	private LocalDate fechaActualizacion;

	@Column(name = "v_usuario_creacion")
	private String usuarioCreacion;

	@Column(name = "v_usuario_actualizacion")
	private String usuarioActualizacion;

	@Column(name = "esta_en_sitio")
	private Boolean estaEnSitio;

	@Column(name = "longitud")
	private String longitud;

	@Column(name = "latitud")
	private String latitud;

	@Column(name = "img1_ubicacion")
	private String img1Ubicacion;

	@Column(name = "img1_descripcion")
	private String img1Descripcion;

	@Column(name = "img1_nombre_real")
	private String img1NombreReal;

	@Column(name = "img1_nombre_generado")
	private String img1NombreGenerado;

	@Column(name = "img1_tipo")
	private String img1Tipo;

	@Column(name = "img2_ubicacion")
	private String img2Ubicacion;

	@Column(name = "img2_descripcion")
	private String img2Descripcion;

	@Column(name = "img2_nombre_real")
	private String img2NombreReal;

	@Column(name = "img2_nombre_generado")
	private String img2NombreGenerado;

	@Column(name = "img2_tipo")
	private String img2Tipo;

	@Column(name = "img3_ubicacion")
	private String img3Ubicacion;

	@Column(name = "img3_descripcion")
	private String img3Descripcion;

	@Column(name = "img3_nombre_real")
	private String img3NombreReal;

	@Column(name = "img3_nombre_generado")
	private String img3NombreGenerado;

	@Column(name = "img3_tipo")
	private String img3Tipo;

	@Column(name = "i_id_registrador")
	private int idRegistrador;
	
}
