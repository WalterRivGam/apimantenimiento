package pe.edu.uni.apiordentrabajo.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonalParticipaDTO {
	private Integer idPersonalParticipa;
	private LocalDate fechaInicio;
	private String nombresCompletos;
	private Integer dias;
	private Integer horas;
	private Integer idOrdenTrabajo;
	private String tipoDocumento;
	private String nroDocumento;
	private Boolean flagRegistroEliminado;
	private Boolean esResponsable;
}
