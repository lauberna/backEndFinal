import { actualizar } from "./turnos.js";

const urlPacientes = "http://localhost:8080/pacientes";
const urlPacientesEditar = "http://localhost:8080/pacientes/editar";
const urlPacientesBuscar = "http://localhost:8080/pacientes/buscar/";
const urlPacientesEliminar = "http://localhost:8080/pacientes/";

// Variables y elementos para odontólogos
let inpN = document.querySelector("#inpN");
let calle = document.querySelector("#calle");
let ciudad = document.querySelector("#ciudad");
let inpA = document.querySelector("#inpA");
let form = document.querySelector("#form");
let olP = document.querySelector("#listaP");
let dni = document.querySelector("#dni");
let guardar = document.querySelector("#guardar");
let cancelar = document.querySelector("#cancelar");
let errorPaciente = document.querySelector("#err");
let errorModal = document.querySelector("#errM");
let newP = document.querySelector("#crear");
let modo = "guardar";
const miModal = document.getElementById("miModal");
const cerrarModalBtn = document.getElementById("cerrarModal");

//LLAMADAS APIS

async function get() {
  try {
    const response = await fetch(urlPacientes);
    return await response.json();
  }
  catch {
    throw new Error("error con la api");
  }
}

async function deleteP(id) {
  const response = await fetch(`${urlPacientesEliminar}${id}`, {
    method: "DELETE"
  });
  return await response.json();
}

async function postP(body) {
  try {
    const response = await fetch(urlPacientesEditar, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body)
    });
    return await response.json();
  } catch {
    throw new Error("error con la api");
  }
}

async function crear(body) {
  try {
    const response = await fetch(urlPacientes, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body)
    });
    return await response.json();
  } catch {
    throw new Error("error con la api");
  }
}

async function obtenerPacientePorId(id) {
  const response = await fetch(`${urlPacientesBuscar}${id}`);
  return await response.json();
}

//LOGICA - CREAR, EDITAR, LISTAR, ELIMINAR

function crearPacientes(paciente) {
  const li = document.createElement("li");
  const p = document.createElement("p");
  p.classList.add("listP");
  p.textContent = `nombre: ${paciente.nombre} ${paciente.apellido}, dni: ${paciente.dni}, domicilio: ${paciente.domicilio.calle}, ${paciente.domicilio.ciudad}`;
  const btnEditar = document.createElement("button");
  const btnEliminar = document.createElement("button");
  btnEditar.textContent = "Editar";
  btnEliminar.textContent = "Eliminar";
  btnEditar.dataset.pacienteId = paciente.id;
  btnEliminar.addEventListener("click", (e) => {
    eliminarPaciente(paciente.id);
  });
  li.appendChild(p);
  li.appendChild(btnEditar);
  li.appendChild(btnEliminar);
  return li;
}

async function cargarPacientes() {
  get()
    .then(pacientes => {
      olP.innerHTML = "";
      if (!pacientes.response) {
        mensajeErrorPaciente(pacientes.body);
      } else {
        console.log(pacientes);
        pacientes.body.forEach((paciente) => {
          const pacienteElement = crearPacientes(paciente);
          olP.appendChild(pacienteElement);
        });
      }
    }).catch(err => mensajeErrorPaciente(err));
}

function nuevoPaciente() {
  modo = "guardar";
  dni.readOnly = false;
  abrirModal();
  const nuevoPaciente = {
    nombre: inpN.value,
    apellido: inpA.value,
    dni: dni.value,
    domicilio: {
      calle: calle.value,
      ciudad: ciudad.value
    }
  };
  return nuevoPaciente;
}

function guardarPaciente(pacienteId, aux) {
  if (aux == "editar") {
    obtenerPacientePorId(pacienteId)
      .then((paciente) => {
        let dni = paciente.body.dni;
        console.log(paciente);
        const pacienteEditado = {
          id: paciente.body.id,
          nombre: inpN.value,
          apellido: inpA.value,
          dni: dni,
          fechaIngreso: paciente.body.fechaIngreso,
          domicilio: {
            id: paciente.body.domicilio.id,
            calle: calle.value,
            ciudad: ciudad.value
          }
        };
        postP(pacienteEditado)
          .then((res) => {
            if (res.response) {
              actualizar();
              cerrarForm();
              console.log(res);
            } else throw new Error(res.body);
          }).catch(e => mensajeErrorPacienteModal(e));
      });
  }
  if (aux == "guardar") {
    crear(nuevoPaciente())
      .then(res => {
        if (res.response) {
          cerrarForm();
          actualizar();
          console.log(res);
        } else throw new Error(res.body);
      }).catch(e => mensajeErrorPacienteModal(e));
  }
}

function mostrarDatosPaciente(pacienteId) {
  modo = "editar";
  dni.readOnly = true;
  abrirModal();
  guardar.dataset.pacienteId = pacienteId;
  obtenerPacientePorId(pacienteId)
    .then((paciente) => {
      inpN.value = paciente.body.nombre;
      inpA.value = paciente.body.apellido;
      dni.value = paciente.body.dni;
      calle.value = paciente.body.domicilio.calle;
      ciudad.value = paciente.body.domicilio.ciudad;
    })
    .catch((error) => {
      mensajeErrorPacienteModal(error);
    });
}

function eliminarPaciente(pacienteId) {
  deleteP(pacienteId)
    .then(res => {
      if (res.error) {
        throw new Error("error con la api");
      }
      cerrarForm();
      actualizar();
      console.log(res);
    })
    .catch((error) => mensajeErrorPaciente(error));
}

function abrirModal() {
  miModal.style.display = "block";
  errorPaciente.style.display = "none";
}

function mensajeErrorPaciente(mensaje) {
  errorPaciente.textContent = mensaje;
  errorPaciente.style.display = "block";
  setTimeout(() => { errorPaciente.style.display = "none"; }, 3000);
}

function mensajeErrorPacienteModal(mensaje) {
  errorModal.textContent = mensaje;
  errorModal.style.opacity = "1";
  setTimeout(() => { errorModal.style.opacity = "0"; }, 3000);
}

function cerrarForm() {
  miModal.style.display = "none";
  form.reset();
}

cancelar.addEventListener("click", (e) => {
  e.preventDefault();
  cerrarForm();
});

olP.addEventListener("click", (e) => {
  const target = e.target;
  if (target.tagName === "BUTTON" && target.textContent === "Editar") {
    e.preventDefault();
    mostrarDatosPaciente(target.dataset.pacienteId);
  }
});

guardar.addEventListener("click", (e) => {
  e.preventDefault();
  if (modo == "guardar") {
    guardarPaciente(e.target.dataset.pacienteId, "guardar");
  }
  if (modo == "editar") {
    guardarPaciente(e.target.dataset.pacienteId, "editar");
  }
});

newP.addEventListener("click", (e) => {
  e.preventDefault();
  nuevoPaciente();
});

cerrarModalBtn.addEventListener("click", cerrarForm);

window.addEventListener("click", (e) => {
  if (e.target === miModal) {
    cerrarForm();
  }
});

export default cargarPacientes;
