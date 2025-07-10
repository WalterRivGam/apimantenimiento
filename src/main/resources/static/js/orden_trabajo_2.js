let materialRowCount = 0;
let personnelRowCount = 0;

function addMaterialRow() {
	materialRowCount++;
	const tableBody = document.querySelector('#materialsTable tbody');
	const newRow = document.createElement('tr');
	newRow.innerHTML = `
			<td>${materialRowCount}</td>
			<td><input type="text" name="material-description-${materialRowCount}" class="mb-0" autocomplete = "off"></td>
			<td><input type="text" name="material-unit-${materialRowCount}" class="mb-0" autocomplete = "off"></td>
			<td><input type="number" name="material-quantity-${materialRowCount}" class="mb-0"></td>
			`;
	//<td><input type="text" name="material-value-${materialRowCount}" class="mb-0" autocomplete = "off"></td>
	//tableBody.insertBefore(newRow, tableBody.lastElementChild);
	tableBody.appendChild(newRow);
	
	/*// Asociar eventos para recalcular total
	  const cantidadInput = newRow.querySelector(`input[name="material-quantity-${materialRowCount}"]`);
	  const valorInput = newRow.querySelector(`input[name="material-value-${materialRowCount}"]`);

	  [cantidadInput, valorInput].forEach(input => {
	    input.addEventListener("input", calcularTotalMateriales);
	  });*/
}

function addPersonnelRow() {
	personnelRowCount++;
	const tableBody = document.querySelector('#personnelTable tbody');
	const newRow = document.createElement('tr');
	newRow.innerHTML = `
			<td>${personnelRowCount}</td>
			<td><input type="date" name="personal-fecha-${personnelRowCount}" class="mb-0"></td>
			<td><input type="text" name="personal-nombre-${personnelRowCount}" class="mb-0" autocomplete = "off"></td>
			<td><input type="text" name="personal-dias-${personnelRowCount}" class="mb-0" autocomplete = "off"></td>
			<td><input type="text" name="personal-horas-${personnelRowCount}" class="mb-0" autocomplete = "off"></td>
			<td><input type="checkbox" name="personal-responsable" class="mb-0"></td>
			<td><input type="radio" name="personal-tecnico" class="mb-0"></td>
			`;
	tableBody.appendChild(newRow);
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
	  tbody.innerHTML = ""; // Limpiar el contenido actual de la tabla

	  //let total = 0;

	  materiales.forEach((material, index) => {
	    const fila = document.createElement("tr");

	    const cantidad = material.cantidad ?? "";
	    //const valor = material.valor ?? "";

	    /*// Convertir valor a número (si es válido) para el total
	    const valorNumerico = parseFloat(valor);
	    if (!isNaN(valorNumerico)) {
	      total += valorNumerico;
	    }*/

	    fila.innerHTML = `
	      <td>${index + 1}</td>
	      <td style="display: none;"><input type="text" name="material-id-${index + 1}" value="${material.idMaterial || ''}"></td>
	      <td><input type="text" name="material-description-${index + 1}" value="${material.descripcion || ''}" class="mb-0" style="width: 100%;" autocomplete = "off"></td>
	      <td><input type="text" name="material-unit-${index + 1}" value="${material.unidad || ''}" class="mb-0" style="width: 100%;" autocomplete = "off"></td>
	      <td><input type="number" name="material-quantity-${index + 1}" value="${cantidad}" class="mb-0" style="width: 100%;"></td>
	    `;
	    //<td><input type="text" name="material-value-${index + 1}" value="${valor}" class="mb-0" style="width: 100%;" autocomplete = "off"></td>

	    tbody.appendChild(fila);
	  });

	  /*// Fila del total al final
	  const filaTotal = document.createElement("tr");
	  filaTotal.innerHTML = `
	    <td colspan="4" style="text-align: right;"><strong>TOTAL:</strong></td>
	    <td><input type="text" name="material-total" readonly value="${total.toFixed(2)}" class="mb-0" style="width: 100%; font-weight: bold;" autocomplete = "off"></td>
	  `;
	  tbody.appendChild(filaTotal);*/
	  materialRowCount = materiales.length;
}



function llenarTablaPersonal(personalParticipa, tecnico) {
	  const tbody = document.querySelector("#personnelTable tbody");
	  tbody.innerHTML = ""; // Limpia la tabla actual si hay datos anteriores

	  personalParticipa.forEach((persona, index) => {
	    const fila = document.createElement("tr");
	    const esTecnico = tecnico && tecnico === persona.nombresCompletos;

	    fila.innerHTML = `
	      <td>${index + 1}</td>
	      <td style="display: none;"><input type="text" name="personal-id-${index + 1}" value="${persona.idPersonalParticipa || ''}"></td>
	      <td><input type="date" name="personal-fecha-${index + 1}" value="${persona.fechaInicio || ''}" style="width: 100%;" class="mb-0"></td>
	      <td><input type="text" name="personal-nombre-${index + 1}" value="${persona.nombresCompletos || ''}" style="width: 100%;" class="mb-0" autocomplete = "off"></td>
	      <td><input type="text" name="personal-dias-${index + 1}" value="${persona.dias != null ? persona.dias : ''}" style="width: 100%;" class="mb-0" autocomplete = "off"></td>
	      <td><input type="text" name="personal-horas-${index + 1}" value="${persona.horas != null ? persona.horas : ''}" style="width: 100%;" class="mb-0" autocomplete = "off"></td>
	      <td><input type="checkbox" name="personal-responsable" ${persona.esResponsable ? "checked" : ""}></td>
	      <td><input type="radio" name="personal-tecnico" ${esTecnico ? "checked" : ""}></td>
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

	    const idMaterial = fila.querySelector(`input[name="material-id-${i + 1}"]`)?.value || null;
	    const descripcion = fila.querySelector(`input[name="material-description-${i + 1}"]`)?.value?.trim() || null;
	    const unidad = fila.querySelector(`input[name="material-unit-${i + 1}"]`)?.value?.trim() || null;
	    const cantidadStr = fila.querySelector(`input[name="material-quantity-${i + 1}"]`)?.value;
	    //const valor = fila.querySelector(`input[name="material-value-${i + 1}"]`)?.value?.trim() || null;
	    const valor = null;
	    
	    const cantidad = isNaN(parseInt(cantidadStr)) ? null : parseInt(cantidadStr);

	    const filaVacia = !descripcion && !unidad && cantidad === null && !valor;
	    if (filaVacia) continue;

	    const material = {
	      idMaterial: idMaterial,
	      descripcion: descripcion,
	      unidad: unidad,
	      cantidad: cantidad,
	      valor: valor,
	      idOrdenTrabajo: idOrdenTrabajo,
	      flagRegistroEliminado: false
	    };

	    materiales.push(material);
	  }

	  return materiales;
	}



function obtenerPersonalParticipa() {
	  const tabla = document.getElementById("personnelTable").getElementsByTagName("tbody")[0];
	  const filas = tabla.getElementsByTagName("tr");

	  const listaPersonal = [];
	  let tecnico = null;

	  for (let i = 0; i < filas.length; i++) {
	    const fila = filas[i];

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
	      flagRegistroEliminado: false,
	      esResponsable: checkboxResponsable ? checkboxResponsable.checked : false
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



