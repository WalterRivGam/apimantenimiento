package pe.edu.uni.apiordentrabajo.controller;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@PostMapping("registrar")
	public ResponseEntity<?> registrarOrden(@RequestBody OrdenTrabajoDTO ordenTrabajoDTO) {
		
		String accion = (ordenTrabajoDTO.getIdOrdenTrabajo() == null) ? "registró" : "actualizó";
		
		try {
			ordenTrabajoDTO = ordenTrabajoService.registrarOrden(ordenTrabajoDTO);
			String nroOrdenTrabajo = ordenTrabajoDTO.getNroOrdenTrabajo();
			int idOrdenTrabajo = ordenTrabajoDTO.getIdOrdenTrabajo();

			return ResponseEntity.status(HttpStatus.OK)
					.body(String.format("Se %s orden de trabajo: ID=%d, NRO. ORDEN DE TRABAJO=%s", accion,
							idOrdenTrabajo, nroOrdenTrabajo));
		} catch (Exception ex) {
			Map<String, Object> respuesta = new LinkedHashMap<>();
	        respuesta.put("timestamp", Instant.now());
	        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
	        respuesta.put("error", "Bad Request");
	        respuesta.put("message", ex.getMessage());
	        respuesta.put("path", "/mantenimiento/ordenes/registrar");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
		}
	}
	
	@GetMapping("obtener/{id}")
	public ResponseEntity<?> obtenerOrden(@PathVariable Integer id) {
	    try {
	        Optional<OrdenTrabajoDTO> ordenTrabajoOpt = ordenTrabajoService.obtenerOrden(id);

	        if (ordenTrabajoOpt.isPresent()) {
	            return ResponseEntity.ok(ordenTrabajoOpt.get());
	        } else {
	            Map<String, Object> respuesta = new LinkedHashMap<>();
	            respuesta.put("timestamp", Instant.now());
	            respuesta.put("status", HttpStatus.NOT_FOUND.value());
	            respuesta.put("error", "Not Found");
	            respuesta.put("message", "Orden de trabajo no encontrada");
	            respuesta.put("path", "/mantenimiento/ordenes/obtener/" + id);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	        }
	    } catch (Exception ex) {
	        Map<String, Object> respuesta = new LinkedHashMap<>();
	        respuesta.put("timestamp", Instant.now());
	        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
	        respuesta.put("error", "Bad Request");
	        respuesta.put("message", ex.getMessage());
	        respuesta.put("path", "/mantenimiento/ordenes/obtener/" + id);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
	    }
	}

	
	@GetMapping("eliminar/{id}")
	public ResponseEntity<?> eliminarOrden(@PathVariable Integer id) {
		try {
			ordenTrabajoService.eliminarOrden(id);
			
			return ResponseEntity.ok(String.format("Orden eliminada con id=%d", id));
		} catch (Exception ex) {
	        Map<String, Object> respuesta = new LinkedHashMap<>();
	        respuesta.put("timestamp", Instant.now());
	        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
	        respuesta.put("error", "Bad Request");
	        respuesta.put("message", ex.getMessage());
	        respuesta.put("path", "/mantenimiento/ordenes/" + id);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
	    }
	}
}
