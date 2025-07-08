package pe.edu.uni.apiordentrabajo.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenTrabajoCorrelativoId implements Serializable {
	private int anio;
    private String tallerCodigo;

    
}

