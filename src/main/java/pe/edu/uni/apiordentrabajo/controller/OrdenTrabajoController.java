package pe.edu.uni.apiordentrabajo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.apiordentrabajo.dto.OrdenTrabajoDTO;
import pe.edu.uni.apiordentrabajo.service.OrdenTrabajoService;

@RestController
@RequestMapping("ordenes")
public class OrdenTrabajoController {

	private OrdenTrabajoService ordenTrabajoService;

	public OrdenTrabajoController(OrdenTrabajoService ordenTrabajoService) {
		this.ordenTrabajoService = ordenTrabajoService;
	}

	@GetMapping
	public String prueba() {
		return "prueba";
	}

	@PostMapping
	public ResponseEntity<String> registrarOrden(@RequestBody OrdenTrabajoDTO ordenTrabajoDTO) {
		
		String accion = (ordenTrabajoDTO.getIdOrdenTrabajo() == null) ? "registró" : "actualizó";
		
		try {
			ordenTrabajoDTO = ordenTrabajoService.registrarOrden(ordenTrabajoDTO);
			String nroOrdenTrabajo = ordenTrabajoDTO.getNroOrdenTrabajo();
			int idOrdenTrabajo = ordenTrabajoDTO.getIdOrdenTrabajo();

			return ResponseEntity.status(HttpStatus.OK)
					.body(String.format("Se %s orden de trabajo: ID=%d, NRO. ORDEN DE TRABAJO=%s", accion,
							idOrdenTrabajo, nroOrdenTrabajo));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en el registro de la orden de trabajo");
		}
	}
}
