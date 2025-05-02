package pe.edu.uni.apiordentrabajo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.uni.apiordentrabajo.entity.OrdenTrabajo;

@Data
@NoArgsConstructor
public class MaterialDTO {
	private Integer idMaterial;
	private String descripcion;
	private String unidad;
	private Integer cantidad;
	private String valor;
	private Integer idOrdenTrabajo;
}
