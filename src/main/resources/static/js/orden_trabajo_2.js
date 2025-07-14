let materialRowCount = 0;
let personnelRowCount = 0;

function addMaterialRow() {
	  materialRowCount++;
	  const tableBody = document.querySelector('#materialsTable tbody');
	  const newRow = document.createElement('tr');

	  newRow.classList.add("fila-material");
	  newRow.dataset.esNuevo = "true";

	  newRow.innerHTML = `
	    <td>${materialRowCount}</td>
	    <td><input type="text" name="material-description-${materialRowCount}" class="mb-0" autocomplete="off"></td>
	    <td><input type="text" name="material-unit-${materialRowCount}" class="mb-0" autocomplete="off"></td>
	    <td><input type="number" name="material-quantity-${materialRowCount}" class="mb-0"></td>
	    <td class="text-center">
	      <i class="fas fa-minus-circle fa-xl icono-eliminar" style="color: #800000; cursor: pointer;"></i>
	    </td>
	  `;

	  tableBody.appendChild(newRow);

	  // Reordenar por si acaso, aunque normalmente no necesario después de agregar
	  reordenarFilasMateriales();
}

function addPersonnelRow() {
	  personnelRowCount++;
	  const tableBody = document.querySelector('#personnelTable tbody');
	  const newRow = document.createElement('tr');

	  newRow.classList.add("fila-personal");
	  newRow.dataset.esNuevo = "true";

	  newRow.innerHTML = `
	    <td>${personnelRowCount}</td>
	    <td><input type="date" name="personal-fecha-${personnelRowCount}" class="mb-0"></td>
	    <td><input type="text" name="personal-nombre-${personnelRowCount}" class="mb-0" autocomplete="off"></td>
	    <td><input type="text" name="personal-dias-${personnelRowCount}" class="mb-0" autocomplete="off"></td>
	    <td><input type="text" name="personal-horas-${personnelRowCount}" class="mb-0" autocomplete="off"></td>
	    <td class="text-center"><input type="checkbox" name="personal-responsable" class="mb-0"></td>
	    <td class="text-center"><input type="radio" name="personal-tecnico" class="mb-0"></td>
	    <td class="text-center"><i class="fas fa-minus-circle fa-xl icono-eliminar" style="color: #800000; cursor: pointer;"></i></td>
	  `;

	  tableBody.appendChild(newRow);
	  reordenarFilasPersonal();
}

        
document.addEventListener("DOMContentLoaded", () => {
	  fetch(`/mantenimiento/ordenes/obtener/${idOrdenTrabajo}`)
	    .then(response => {
	      if (!response.ok) {
	        throw new Error("No se pudo obtener la orden de trabajo");
	      }
	      return response.json();
	    })
	    .then(data => {
	      document.getElementById("nro-expediente").value = data.nroExpediente;
	      document.getElementById("anio-expediente").value = data.anioExpediente;
	      document.getElementById("nro-orden-trabajo").value = data.nroOrdenTrabajo;
	      document.getElementById("taller").value = data.taller;
	      
	      // mostrar datos de personal que participa
	      const personalParticipa = data.personalParticipa;
	      const tecnico = data.tecnico;
	      llenarTablaPersonal(personalParticipa, tecnico);
	      
	      document.getElementById("fecha-recepcion-taller").value = data.fechaRecepcionTaller;
	      document.getElementById("fecha-trabajo-realizado").value = data.fechaTrabajoRealizado;
	      document.getElementById("diagnostico").value = data.diagnostico;
	      document.getElementById("observaciones").value = data.observaciones;
	      document.getElementById("fecha-atencion-taller").value = data.fechaAtencionTaller;
	      
	      // mostrar datos de materiales
	      const materiales = data.materiales;
	      llenarTablaMateriales(materiales);
	    })
	    .catch(error => {
	      console.error("Error:", error);
	      alert("Error al cargar la orden de trabajo.");
	    });
});

function llenarTablaMateriales(materiales) {
	const tbody = document.querySelector("#materialsTable tbody");
	tbody.innerHTML = "";

	materiales
		.filter(m => !m.flagRegistroEliminado)
		.forEach((material, index) => {
			const fila = document.createElement("tr");
			fila.classList.add("fila-material");
			fila.dataset.idMaterial = material.idMaterial || "";
			fila.dataset.esNuevo = material.idMaterial ? "false" : "true";

			const cantidad = material.cantidad ?? "";

			fila.innerHTML = `
				<td>${index + 1}</td>
				<td style="display: none;">
					<input type="hidden" name="material-id-${index + 1}" value="${material.idMaterial || ''}">
				</td>
				<td><input type="text" name="material-description-${index + 1}" value="${material.descripcion || ''}" class="mb-0" style="width: 100%;" autocomplete="off"></td>
				<td><input type="text" name="material-unit-${index + 1}" value="${material.unidad || ''}" class="mb-0" style="width: 100%;" autocomplete="off"></td>
				<td><input type="number" name="material-quantity-${index + 1}" value="${cantidad}" class="mb-0" style="width: 100%;"></td>
				<td class="text-center">
					<i class="fas fa-minus-circle fa-xl icono-eliminar" style="color: #800000; cursor: pointer;"></i>
				</td>
			`;

			tbody.appendChild(fila);
		});

	materialRowCount = materiales.length;
}

document.querySelector('#materialsTable tbody').addEventListener('click', function (e) {
	  if (e.target.classList.contains('icono-eliminar')) {
	    const fila = e.target.closest('tr');
	    const esNuevo = fila.dataset.esNuevo === "true";

	    if (esNuevo) {
	      fila.remove(); // Borra del DOM
	    } else {
	      fila.style.display = "none"; // Oculta
	      const input = document.createElement('input');
	      input.type = "hidden";
	      input.name = `material-eliminado-${fila.dataset.idMaterial}`;
	      input.value = "true";
	      fila.appendChild(input);
	    }
	    
	    reordenarFilasMateriales();
	  }
});

document.querySelector('#personnelTable tbody').addEventListener('click', function (e) {
	  if (e.target.classList.contains('icono-eliminar')) {
	    const fila = e.target.closest('tr');
	    const esNuevo = fila.dataset.esNuevo === "true";

	    if (esNuevo) {
	      fila.remove();
	    } else {
	      fila.style.display = "none";
	      const input = document.createElement('input');
	      input.type = "hidden";
	      input.name = `personal-eliminado-${fila.dataset.idPersonalParticipa}`;
	      input.value = "true";
	      fila.appendChild(input);
	    }

	    reordenarFilasPersonal();
	  }
});

function llenarTablaPersonal(personalParticipa, tecnico) {
	  const tbody = document.querySelector("#personnelTable tbody");
	  tbody.innerHTML = "";

	  personalParticipa
	    .filter(p => !p.flagRegistroEliminado)
	    .forEach((persona, index) => {
	      const fila = document.createElement("tr");
	      const esTecnico = persona.esTecnico === true;

	      fila.classList.add("fila-personal");
	      fila.dataset.idPersonalParticipa = persona.idPersonalParticipa || "";
	      fila.dataset.esNuevo = persona.idPersonalParticipa ? "false" : "true";

	      fila.innerHTML = `
	        <td>${index + 1}</td>
	        <td style="display: none;"><input type="hidden" name="personal-id-${index + 1}" value="${persona.idPersonalParticipa || ''}"></td>
	        <td><input type="date" name="personal-fecha-${index + 1}" value="${persona.fechaInicio || ''}" class="mb-0" style="width: 100%;"></td>
	        <td><input type="text" name="personal-nombre-${index + 1}" value="${persona.nombresCompletos || ''}" class="mb-0" style="width: 100%;" autocomplete="off"></td>
	        <td><input type="text" name="personal-dias-${index + 1}" value="${persona.dias ?? ''}" class="mb-0" style="width: 100%;" autocomplete="off"></td>
	        <td><input type="text" name="personal-horas-${index + 1}" value="${persona.horas ?? ''}" class="mb-0" style="width: 100%;" autocomplete="off"></td>
	        <td class="text-center"><input type="checkbox" name="personal-responsable" ${persona.esResponsable ? "checked" : ""}></td>
	        <td class="text-center"><input type="radio" name="personal-tecnico" ${esTecnico ? "checked" : ""}></td>
	        <td class="text-center"><i class="fas fa-minus-circle fa-xl icono-eliminar" style="color: #800000; cursor: pointer;"></i></td>
	      `;

	      tbody.appendChild(fila);
	    });

	  personnelRowCount = personalParticipa.length;
}

document.getElementById('btn-enviar-orden-trabajo').addEventListener("click", async () => {
	  document.getElementById("carga-btn-enviar").style.display = "inline-block";

	  const { listaPersonal, tecnico } = obtenerPersonalParticipa(idOrdenTrabajo);
	  const materiales = obtenerMateriales();

	  const datos = {
	    idOrdenTrabajo: idOrdenTrabajo,
	    personalParticipa: listaPersonal,
	    tecnico: tecnico,
	    fechaRecepcionTaller: document.getElementById("fecha-recepcion-taller").value,
	    fechaTrabajoRealizado: document.getElementById("fecha-trabajo-realizado").value,
	    diagnostico: document.getElementById("diagnostico").value,
	    observaciones: document.getElementById("observaciones").value,
	    fechaAtencionTaller: document.getElementById("fecha-atencion-taller").value,
	    materiales: materiales,
	    flagRegistroEliminado: false,
	    usuarioActualizacion: document.getElementById("nombresUsuario").value
	  };

	  try {
	    const response = await fetch('/mantenimiento/ordenes/guardar', {
	      method: 'POST',
	      headers: {
	        'Content-Type': 'application/json'
	      },
	      body: JSON.stringify(datos)
	    });

	    if (!response.ok) {
	      $('#titulo-modal-generico').text("Error orden de trabajo");
		  $('#mensaje-modal-generico').text("Error al guardar la orden");
		  $('#modal-generico').modal("show");
	      return;
	    }

	    const resultado = await response.json();
	    console.log("Respuesta del servidor:", resultado);

	    $('#titulo-modal-generico').text("Orden de trabajo");
		$('#mensaje-modal-generico').text("Orden actualizada: " + resultado.nroOrdenTrabajo);
		$('#modal-generico').modal("show");
		
		const nombresCompletos = document.getElementById("nombresUsuario").value;
		const nombresParam = encodeURIComponent(nombresCompletos);
		window.location.href = `/mantenimiento/registrarorden?nombres=${nombresParam}`;
		
	  } catch (error) {
	      console.error("Error en la petición:", error);
	      $('#titulo-modal-generico').text("Error orden de trabajo");
		  $('#mensaje-modal-generico').text("Ocurrió un error al guardar la orden.");
		  $('#modal-generico').modal("show");
	  } finally {
	    document.getElementById("carga-btn-enviar").style.display = "none";
	  }
});

function obtenerMateriales() {
	  const tabla = document.getElementById("materialsTable").getElementsByTagName("tbody")[0];
	  const filas = tabla.getElementsByTagName("tr");

	  const materiales = [];

	  for (let i = 0; i < filas.length; i++) {
	    const fila = filas[i];

	    const eliminadoInput = fila.querySelector(`input[name^="material-eliminado-"]`);
	    const estaEliminado = eliminadoInput !== null;

	    const idMaterial = fila.querySelector(`input[name="material-id-${i + 1}"]`)?.value || null;
	    const descripcion = fila.querySelector(`input[name="material-description-${i + 1}"]`)?.value?.trim() || null;
	    const unidad = fila.querySelector(`input[name="material-unit-${i + 1}"]`)?.value?.trim() || null;
	    const cantidadStr = fila.querySelector(`input[name="material-quantity-${i + 1}"]`)?.value;
	    const cantidad = isNaN(parseInt(cantidadStr)) ? null : parseInt(cantidadStr);

	    // Si la fila está eliminada, igual debemos enviarla con el flag en true
	    if (estaEliminado) {
	      materiales.push({
	        idMaterial: idMaterial,
	        descripcion: descripcion,
	        unidad: unidad,
	        cantidad: cantidad,
	        valor: null,
	        idOrdenTrabajo: idOrdenTrabajo,
	        flagRegistroEliminado: true
	      });
	      continue;
	    }

	    // Omitir filas vacías no eliminadas
	    const filaVacia = !descripcion && !unidad && cantidad === null;
	    if (filaVacia) continue;

	    // Fila válida activa
	    materiales.push({
	      idMaterial: idMaterial,
	      descripcion: descripcion,
	      unidad: unidad,
	      cantidad: cantidad,
	      valor: null,
	      idOrdenTrabajo: idOrdenTrabajo,
	      flagRegistroEliminado: false
	    });
	  }

	  return materiales;
}

function reordenarFilasMateriales() {
	  const filas = document.querySelectorAll("#materialsTable tbody tr");
	  let contador = 1;

	  filas.forEach(fila => {
	    if (fila.style.display === "none") return; // Omitir filas eliminadas (ocultas)

	    // Actualizar número visible
	    const celdaNumero = fila.querySelector("td:first-child");
	    if (celdaNumero) {
	      celdaNumero.textContent = contador;
	    }

	    // Actualizar atributos name
	    fila.querySelectorAll("input").forEach(input => {
	      if (input.name.startsWith("material-id-")) {
	        input.name = `material-id-${contador}`;
	      } else if (input.name.startsWith("material-description-")) {
	        input.name = `material-description-${contador}`;
	      } else if (input.name.startsWith("material-unit-")) {
	        input.name = `material-unit-${contador}`;
	      } else if (input.name.startsWith("material-quantity-")) {
	        input.name = `material-quantity-${contador}`;
	      }
	    });

	    contador++;
	  });

	  materialRowCount = contador - 1;
}

function reordenarFilasPersonal() {
	  const filas = document.querySelectorAll("#personnelTable tbody tr");
	  let contador = 1;

	  filas.forEach(fila => {
	    if (fila.style.display === "none") return;

	    // Reasignar número visible
	    const celdaNumero = fila.querySelector("td:first-child");
	    if (celdaNumero) {
	      celdaNumero.textContent = contador;
	    }

	    // Reasignar nombres de inputs
	    fila.querySelectorAll("input").forEach(input => {
	      if (input.name.startsWith("personal-id-")) {
	        input.name = `personal-id-${contador}`;
	      } else if (input.name.startsWith("personal-fecha-")) {
	        input.name = `personal-fecha-${contador}`;
	      } else if (input.name.startsWith("personal-nombre-")) {
	        input.name = `personal-nombre-${contador}`;
	      } else if (input.name.startsWith("personal-dias-")) {
	        input.name = `personal-dias-${contador}`;
	      } else if (input.name.startsWith("personal-horas-")) {
	        input.name = `personal-horas-${contador}`;
	      }
	    });

	    contador++;
	  });

	  personnelRowCount = contador - 1;
}



function obtenerPersonalParticipa() {
	  const tabla = document.getElementById("personnelTable").getElementsByTagName("tbody")[0];
	  const filas = tabla.getElementsByTagName("tr");

	  const listaPersonal = [];
	  let tecnico = null;

	  for (let i = 0; i < filas.length; i++) {
	    const fila = filas[i];

	    const eliminadoInput = fila.querySelector(`input[name^="personal-eliminado-"]`);
	    const estaEliminado = eliminadoInput !== null;

	    const id = fila.querySelector(`input[name="personal-id-${i + 1}"]`)?.value || null;
	    const fecha = fila.querySelector(`input[name="personal-fecha-${i + 1}"]`)?.value || null;
	    const nombre = fila.querySelector(`input[name="personal-nombre-${i + 1}"]`)?.value || null;
	    const dias = fila.querySelector(`input[name="personal-dias-${i + 1}"]`)?.value || null;
	    const horas = fila.querySelector(`input[name="personal-horas-${i + 1}"]`)?.value || null;

	    const checkboxResponsable = fila.querySelector('input[type="checkbox"][name="personal-responsable"]');
	    const inputRadioTecnico = fila.querySelector('input[type="radio"][name="personal-tecnico"]');

	    const trabajador = {
	    	idPersonalParticipa: id,
	    	fechaInicio: fecha,
	    	nombresCompletos: nombre,
	    	dias: isNaN(parseInt(dias)) ? null : parseInt(dias),
	    	horas: isNaN(parseInt(horas)) ? null : parseInt(horas),
	    	idOrdenTrabajo: idOrdenTrabajo,
	    	tipoDocumento: null,
	    	nroDocumento: null,
	    	flagRegistroEliminado: estaEliminado,
	    	esResponsable: checkboxResponsable ? checkboxResponsable.checked : false,
	    	esTecnico: inputRadioTecnico ? inputRadioTecnico.checked : false
	    };


	    listaPersonal.push(trabajador);

	    if (inputRadioTecnico?.checked) {
	      tecnico = nombre;
	    }
	  }

	  return {
	    listaPersonal,
	    tecnico
	  };
}

function calcularTotalMateriales() {
	  const filas = document.querySelectorAll("#materialsTable tbody tr");
	  let total = 0;

	  // Excluye la última fila (donde está el TOTAL)
	  for (let i = 0; i < filas.length - 1; i++) {
	    const fila = filas[i];

	    const cantidadInput = fila.querySelector(`input[name^="material-quantity-"]`);
	    const valorInput = fila.querySelector(`input[name^="material-value-"]`);

	    const cantidad = cantidadInput ? parseFloat(cantidadInput.value) : 0;
	    const valor = valorInput ? parseFloat(valorInput.value) : 0;

	    if (!isNaN(cantidad) && !isNaN(valor)) {
	      total += cantidad * valor;
	    }
	  }

	  const totalInput = document.querySelector('input[name="material-total"]');
	  if (totalInput) {
	    totalInput.value = total.toFixed(2); // 2 decimales
	  }
	}



