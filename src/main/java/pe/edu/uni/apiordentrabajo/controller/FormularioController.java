package pe.edu.uni.apiordentrabajo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormularioController {

	@GetMapping("registrarorden")
	public String registrarOrden(@RequestParam(name = "nombres", required = false) String nombres, Model model) {
		model.addAttribute("nombresUsuario", nombres);
		return "registrar_orden";
	}

	@GetMapping("ordentrabajo2/{idOrdenTrabajo}")
	public String ordenTrabajo2(@PathVariable Integer idOrdenTrabajo,
			@RequestParam(name = "nombres", required = false) String nombres, Model model) {

		model.addAttribute("idOrdenTrabajo", idOrdenTrabajo);
		model.addAttribute("nombresUsuario", nombres);

		return "orden_trabajo_2";
	}

}
