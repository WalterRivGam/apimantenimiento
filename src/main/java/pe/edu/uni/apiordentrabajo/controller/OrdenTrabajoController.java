package pe.edu.uni.apiordentrabajo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.apiordentrabajo.dto.OrdenTrabajoDTO;
import pe.edu.uni.apiordentrabajo.service.OrdenTrabajoService;

@RestController
@RequestMapping("api/mantenimiento/ordenes")
public class OrdenTrabajoController {
	
	private OrdenTrabajoService ordenTrabajoService;
	
	public OrdenTrabajoController(OrdenTrabajoService ordenTrabajoService) {
		this.ordenTrabajoService = ordenTrabajoService;
	}
	
	@PostMapping
	public ResponseEntity<OrdenTrabajoDTO> registrarOrden(@RequestBody OrdenTrabajoDTO ordenTrabajoDTO) {
		try {
			ordenTrabajoDTO = ordenTrabajoService.registrarOrden(ordenTrabajoDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(ordenTrabajoDTO);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
