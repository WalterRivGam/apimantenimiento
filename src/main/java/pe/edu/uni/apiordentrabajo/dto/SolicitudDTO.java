package pe.edu.uni.apiordentrabajo.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolicitudDTO {
	private Integer id;
	private String anio;
	private String nroTicket;
	private String bienServicio;
	private String servicioRequerido;
	private String prioridad;
	private String descripcion;
	private String estado;
	private Integer idUsuario;
	private String sede;
	private String sector;
	private String edificio;
	private String nivel;
	private String espacio;
	private String descespacio;
	private String tipoAccion;
	private String tipoDocumento;
	private String ip;
	private String nroExpediente;
	private LocalDate fechaCreacion;
	private LocalDate fechaActualizacion;
	private String usuarioCreacion;
	private String usuarioActualizacion;
	private Boolean estaEnSitio;
	private String longitud;
	private String latitud;
	private String img1Ubicacion;
	private String img1Descripcion;
	private String img1NombreReal;
	private String img1NombreGenerado;
	private String img1Tipo;
	private String img2Ubicacion;
	private String img2Descripcion;
	private String img2NombreReal;
	private String img2NombreGenerado;
	private String img2Tipo;
	private String img3Ubicacion;
	private String img3Descripcion;
	private String img3NombreReal;
	private String img3NombreGenerado;
	private String img3Tipo;
	private int idRegistrador;
}
