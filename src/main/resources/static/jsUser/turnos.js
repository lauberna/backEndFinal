import cargarOdontologos from "./odontologos.js";
import cargarPacientes from "./pacientes.js";

// Elementos HTML
const inpHoraT = document.querySelector("#hora");
const formT = document.querySelector("#edit-turno-form");
const olT = document.querySelector("#turnos-list");
const cancelarT = document.querySelector("#cancelarT");
const actualizarT = document.querySelector("#actT");
const errT = document.querySelector("#errT");
const errMT = document.querySelector("#errMT");
const crearT = document.querySelector("#crearT");
const miModalT = document.getElementById("miModalT");
const cerrarModalBtnT = document.getElementById("cerrarModalT");

// URL de la API de turnos
const urlTurnos = "http://localhost:8080/turnos";

// Función para obtener los datos de turnos desde la API
async function get() {
  try {
    const response = await fetch(urlTurnos);
    return await response.json();
  } catch {
    throw new Error("Error con la API");
  }
}

// Función para crear elementos de la lista de turnos
function crearTurnos(turno) {
  const li = document.createElement("li");
  const p = document.createElement("p");
  p.classList.add("listP");
  p.textContent = `Fecha: ${turno.fecha}, Doctor: ${turno.odontologo.nombre} ${turno.odontologo.apellido}, Paciente: ${turno.paciente.nombre} ${turno.paciente.apellido}`;
  li.appendChild(p);
  return li;
}

// Función para cargar la lista de turnos
export default async function cargarTurnos() {
  get()
    .then((turnos) => {
      olT.innerHTML = "";
      if (!turnos.response) {
        mensajeErrorT(turnos.body);
      } else {
        console.log(turnos.body);
        turnos.body.forEach((turno) => {
          const turnoElement = crearTurnos(turno);
          olT.appendChild(turnoElement);
        });
      }
    })
    .catch((err) => mensajeErrorT(err));
}

// Función para cargar la lista de pacientes desde la API
async function cargarPacientesList() {
  try {
    const response = await fetch("http://localhost:8080/pacientes");
    const pacientes = await response.json();
    const pacienteSelect = document.getElementById("paciente-select");
    pacienteSelect.innerHTML = "";
    if (!pacientes.response) {
      console.log("no hay pacientes por agregar a la lista");
    }
    pacientes.body.forEach((paciente) => {
      const option = document.createElement("option");
      option.value = paciente.id;
      option.textContent = paciente.nombre;
      pacienteSelect.appendChild(option);
    });
  } catch (error) { }
}

// Función para cargar la lista de odontólogos desde la API
async function cargarOdontologosList() {
  try {
    const response = await fetch("http://localhost:8080/odontologos");
    const odontologos = await response.json();
    const odontologoSelect = document.getElementById("odontologo-select");
    odontologoSelect.innerHTML = "";

    odontologos.body.forEach((odontologo) => {
      const option = document.createElement("option");
      option.value = odontologo.id;
      option.textContent = odontologo.nombre;
      odontologoSelect.appendChild(option);
    });
  } catch (error) {
  }
}

// Evento para el botón "Crear Turno"
crearT.addEventListener("click", () => {
  abrirModalT();
});

// Evento para el botón "Guardar Turno"
document
  .getElementById("guardar-btn")
  .addEventListener("click", async (event) => {
    event.preventDefault();
    const pacienteSelect = document.getElementById("paciente-select");
    const odontologoSelect = document.getElementById("odontologo-select");
    const hora = document.querySelector("#hora");

    const datosTurnoEditado = {
      fecha: hora.value,
      idPa: pacienteSelect.value,
      idOd: odontologoSelect.value,
    };
    try {
      const url = "http://localhost:8080/turnos";

      const res = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(datosTurnoEditado),
      });
      const turno = await res.json();
      if (!turno.response) throw new Error(turno.body);
      console.log(turno);
      cargarTurnos();
      cerrarFormT();
      return turno;
    } catch (error) {
      mensajeErrorMT(error);
    }
  });


// Función para abrir el modal de turnos
function abrirModalT() {
  miModalT.style.display = "block";
  errT.style.display = "none";
}

// Función para mostrar un mensaje de error para turnos
function mensajeErrorT(mensaje) {
  errT.textContent = mensaje;
  errT.style.display = "block";
  setTimeout(() => {
    errT.style.display = "none";
  }, 3000);
}

// Función para mostrar un mensaje de error en el modal de turnos
function mensajeErrorMT(mensaje) {
  errMT.textContent = mensaje;
  errMT.style.opacity = "1";
  setTimeout(() => {
    errMT.style.opacity = "0";
  }, 3000);
}

// Función para cerrar el formulario de turnos
function cerrarFormT() {
  miModalT.style.display = "none";
  formT.reset();
}

// Eventos adicionales
cancelarT.addEventListener("click", (e) => {
  e.preventDefault();
  cerrarFormT();
});

export function actualizar() {
  cargarPacientesList();
  cargarPacientes();
  cargarOdontologosList();
  cargarTurnos();
  cargarOdontologos();
}

actualizarT.addEventListener("click", (e) => {
  e.preventDefault();
  actualizar();
});

cargarPacientesList();
cargarOdontologosList();

cerrarModalBtnT.addEventListener("click", cerrarFormT);

window.addEventListener("click", (e) => {
  if (e.target === miModalT) {
    cerrarFormT();
  }
});


