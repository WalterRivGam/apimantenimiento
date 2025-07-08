document.getElementById("btn-registrar-orden").addEventListener("click", () => {
	
	document.getElementById("carga-btn-enviar").style.display = "inline-block";

	validarCampos();
	
	const datos = {
			nroExpediente: document.getElementById('nro-expediente').value.trim(),
			anioExpediente: document.getElementById('anio-expediente').value.trim(),
			esEmergencia: document.getElementById("emergencia").checked,
			dependencia: document.getElementById('dependencia').value.trim(),
			taller: document.getElementById('taller').value.trim(),
			subdependencia: document.getElementById('sub-dependencia').value.trim(),
			nroDocumento: document.getElementById('nro-documento').value.trim(),
			fechaRecepcionAm: document.getElementById('fecha-recepcion-am').value.trim(),
			fechaDependencia: document.getElementById('fecha-dependencia').value.trim(),
			fechaRecepcionUsg: document.getElementById('fecha-recepcion-usg').value.trim(),
			tipoTrabajo: document.querySelector('input[name="trabajo-tipo"]:checked')?.value || null,
			trabajoSolicitado: document.getElementById('trabajo-solicitado').value.trim(),
			usuarioRegistro: document.getElementById('nombresUsuario').value.trim()
	};

	// Enviar al servidor
	fetch('ordenes/guardar', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify(datos)
	}).then(res => res.json())
	  .then(data => {
		  if (data.nroOrdenTrabajo) {
				document.getElementById('nro-orden-trabajo').value = data.nroOrdenTrabajo;
		  }
		  if (data.idOrdenTrabajo) {
				document.getElementById('id-orden-trabajo').value = data.idOrdenTrabajo;
		  }
		  $('#cod-orden-trabajo').text(data.nroOrdenTrabajo);
		  $('#modal-nro-orden-trabajo').modal("show");
		  document.getElementById("carga-btn-enviar").style.display = "none";	
		  
		  bloquearCampos(true);
	  })
	  .catch(err => {
		console.error('Error:', err);		
		$('#modal-error-orden-trabajo').modal("show");
		document.getElementById("carga-btn-enviar").style.display = "none";	
	  });
});

document.getElementById("btn-cancelar-orden").addEventListener("click", () => {
	bloquearCampos(false);
	limpiarCampos();
});

const mostrarError = (idCampo, nombreCampo) => {
	document.getElementById(idCampo).style.backgroundColor = "#fab005";
	$('#campo-vacio').text(nombreCampo);
	$('#modal-campo-vacio').modal("show");
};

function mostrarErrorYOcultarCarga(idCampo, nombreCampo) {
	document.getElementById("carga-btn-enviar").style.display = "none";
	return mostrarError(idCampo, nombreCampo);
}

function validarCampos() {
	const nroExpediente = document.getElementById('nro-expediente').value.trim();
	if (nroExpediente === "") return mostrarErrorYOcultarCarga('nro-expediente', "número de expediente");

	const anioExpediente = document.getElementById('anio-expediente').value.trim();
	if (anioExpediente === "") return mostrarErrorYOcultarCarga('anio-expediente', "año de expediente");

	const dependencia = document.getElementById('dependencia').value.trim();
	if (dependencia === "") return mostrarErrorYOcultarCarga('dependencia', "dependencia");

	const taller = document.getElementById('taller').value.trim();
	if (taller === "") return mostrarErrorYOcultarCarga('taller', "taller");

	const subdependencia = document.getElementById('sub-dependencia').value.trim();
	if (subdependencia === "") return mostrarErrorYOcultarCarga('sub-dependencia', "subdependencia");

	const nroDocumento = document.getElementById('nro-documento').value.trim();
	if (nroDocumento === "") return mostrarErrorYOcultarCarga('nro-documento', "número de documento");

	const fechaRecepcionAm = document.getElementById('fecha-recepcion-am').value.trim();
	if (fechaRecepcionAm === "") return mostrarErrorYOcultarCarga('fecha-recepcion-am', "fecha de recepción AM");

	const fechaDependencia = document.getElementById('fecha-dependencia').value.trim();
	if (fechaDependencia === "") return mostrarErrorYOcultarCarga('fecha-dependencia', "fecha de dependencia");

	const fechaRecepcionUsg = document.getElementById('fecha-recepcion-usg').value.trim();
	if (fechaRecepcionUsg === "") return mostrarErrorYOcultarCarga('fecha-recepcion-usg', "fecha de recepción USG");

	const tipoTrabajo = document.querySelector('input[name="trabajo-tipo"]:checked')?.value || null;
	if (!tipoTrabajo) {
		document.getElementById("carga-btn-enviar").style.display = "none";
		$('#campo-vacio').text("tipo de trabajo");
		$('#modal-campo-vacio').modal("show");
		return;
	}

	const trabajoSolicitado = document.getElementById('trabajo-solicitado').value.trim();
	if (trabajoSolicitado === "") return mostrarErrorYOcultarCarga('trabajo-solicitado', "trabajo solicitado");
}


function bloquearCampos(bloquear) {
	const COLOR = bloquear ? "#ced4da" : "#ffffff";
	
	document.getElementById('nro-expediente').disabled = bloquear;
	document.getElementById('nro-expediente').style.backgroundColor = COLOR;
	
	document.getElementById('anio-expediente').disabled = bloquear;
	document.getElementById('anio-expediente').style.backgroundColor = COLOR;
	
	document.getElementById('emergencia').disabled = true;

	document.getElementById('nro-orden-trabajo').disabled = bloquear;
	document.getElementById('nro-orden-trabajo').style.backgroundColor = COLOR;
	
	document.getElementById('btn-buscar-orden').disabled = bloquear;
	
	document.getElementById('dependencia').disabled = bloquear;
	document.getElementById('dependencia').style.backgroundColor = COLOR;
	
	document.getElementById('sub-dependencia').disabled = bloquear;
	document.getElementById('sub-dependencia').style.backgroundColor = COLOR;
	
	document.getElementById('taller').disabled = bloquear;
	document.getElementById('taller').style.backgroundColor = COLOR;
	
	document.getElementById('nro-documento').disabled = bloquear;
	document.getElementById('nro-documento').style.backgroundColor = COLOR;
	
	document.getElementById('fecha-recepcion-am').disabled = bloquear;
	document.getElementById('fecha-recepcion-am').style.backgroundColor = COLOR;
	
	document.getElementById('fecha-dependencia').disabled = bloquear;
	document.getElementById('fecha-dependencia').style.backgroundColor = COLOR;
	
	document.getElementById('fecha-recepcion-usg').disabled = bloquear;
	document.getElementById('fecha-recepcion-usg').style.backgroundColor = COLOR;
	
	document.querySelectorAll('input[name="trabajo-tipo"]').forEach(radio => {
		radio.disabled = bloquear;
	});
	
	document.getElementById('trabajo-solicitado').disabled = bloquear;
	document.getElementById('trabajo-solicitado').style.backgroundColor = COLOR;
	
	document.getElementById('btn-registrar-orden').disabled = bloquear;
}

function limpiarCampos() {
	document.getElementById('nro-expediente').value = "";
	document.getElementById('anio-expediente').value = "";
	document.getElementById('emergencia').checked = false;
	document.getElementById('nro-orden-trabajo').value = "";
	document.getElementById('btn-buscar-orden').disabled = false;
	document.getElementById('dependencia').value = "";
	document.getElementById('sub-dependencia').value = "";
	document.getElementById('taller').value = "";
	document.getElementById('nro-documento').value = "";
	document.getElementById('fecha-recepcion-am').value = "";
	document.getElementById('fecha-dependencia').value = "";
	document.getElementById('fecha-recepcion-usg').value = "";
	document.querySelectorAll('input[name="trabajo-tipo"]').forEach(radio => {
		radio.checked = false;
	});
	// Marcar el primero
	const primerRadio = document.querySelector('input[name="trabajo-tipo"]');
	if (primerRadio) {
	    primerRadio.checked = true;
	}
	document.getElementById('trabajo-solicitado').value = "";
	document.getElementById('id-orden-trabajo').value = "";
	
	document.getElementById('btn-registrar-orden').disabled = false;
}

const campos = [
	'nro-expediente',
	'anio-expediente',
	'dependencia',
	'taller',
	'sub-dependencia',
	'nro-documento',
	'fecha-recepcion-am',
	'fecha-dependencia',
	'fecha-recepcion-usg',
	'trabajo-solicitado'
];

campos.forEach(id => {
	const campo = document.getElementById(id);
	if (campo) {
		campo.addEventListener("focus", () => {
			campo.style.backgroundColor = "#ffffff";
		});
	}
});

document.getElementById('btn-buscar-orden').addEventListener("click", async () => {
	  const nroOrden = document.getElementById('nro-orden-trabajo').value.trim();

	  if (!nroOrden) {
		$('#titulo-modal-generico').text("Error orden de trabajo");
		$('#mensaje-modal-generico').text("No se ha ingresado el número de orden de trabajo");
		$('#modal-generico').modal("show");
		  
	    return;
	  }

	  const orden = await obtenerOrdenTrabajo(nroOrden);

	  if (orden) {
		  document.getElementById('nro-expediente').value = orden.nroExpediente || "";
		  document.getElementById('anio-expediente').value = orden.anioExpediente || "";
		  document.getElementById('emergencia').checked = orden.esEmergencia;
		  document.getElementById('dependencia').value = orden.dependencia || "";
		  document.getElementById('sub-dependencia').value = orden.subdependencia || "";
		  document.getElementById('taller').value = orden.taller || "";
		  document.getElementById('nro-documento').value = orden.nroDocumento || "";
		  document.getElementById('fecha-recepcion-am').value = orden.fechaRecepcionAm || "";
		  document.getElementById('fecha-dependencia').value = orden.fechaDependencia || "";
		  document.getElementById('fecha-recepcion-usg').value = orden.fechaRecepcionUsg || "";
		  const tipoTrabajo = orden.tipoTrabajo;
		  const radios = document.querySelectorAll('input[name="tipo-trabajo"]');
		  radios.forEach(radio => {
		    radio.checked = (radio.value === tipoTrabajo);
		  });
		  document.getElementById('trabajo-solicitado').value = orden.trabajoSolicitado || "";
		  document.getElementById('id-orden-trabajo').value = orden.idOrdenTrabajo || "";
		  
		  bloquearCampos(true);
		  
	  } else {
		  $('#titulo-modal-generico').text("Error orden de trabajo");
		  $('#mensaje-modal-generico').text("Orden de trabajo no encontrada");
		  $('#modal-generico').modal("show");
	  }
});

async function obtenerOrdenTrabajo(nroOrden) {
	  try {
	    const response = await fetch(`/mantenimiento/ordenes/obtenerPorNroOrden/${encodeURIComponent(nroOrden)}`);

	    if (!response.ok) {
	      const errorData = await response.json();
	      console.error("Error al obtener la orden:", errorData);
	      return null;
	    }

	    const orden = await response.json();
	    return orden;

	  } catch (error) {
	    console.error("Error en la solicitud:", error);
	    return null;
	  }
}

document.getElementById('btn-siguiente').addEventListener("click", async () => {
	const idOrdenTrabajo = document.getElementById('id-orden-trabajo').value;
	const nombresUsuario = document.getElementById('nombresUsuario').value;
	
	if(!idOrdenTrabajo) {
		$('#titulo-modal-generico').text("Error formulario");
		$('#mensaje-modal-generico').text("Seleccione o registre una orden de trabajo");
		$('#modal-generico').modal("show");
	} else {
		const nombresParam = encodeURIComponent(nombresUsuario);
		window.location.href = `ordentrabajo2/${idOrdenTrabajo}?nombres=${nombresParam}`;
	}
});


