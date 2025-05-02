package pe.edu.uni.apiordentrabajo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponsableObraDTO {
	private Integer idResponsableObra;
	private String nombresCompletos;
	private Integer idOrdenTrabajo;
	private String tipoDocumento;
	private String nroDocumento;
}
