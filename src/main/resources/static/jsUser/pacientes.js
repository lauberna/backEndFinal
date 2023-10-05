// Variables y elementos para odontólogos
const urlPacientes = "http://localhost:8080/pacientes";
let olP = document.querySelector("#listaP");
let errorPaciente = document.querySelector("#err");

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

//LOGICA - CREAR, EDITAR, LISTAR, ELIMINAR

function crearPacientes(paciente) {
  const li = document.createElement("li");
  const p = document.createElement("p");
  p.classList.add("listP");
  p.textContent = `nombre: ${paciente.nombre} ${paciente.apellido}, dni: ${paciente.dni}, domicilio: ${paciente.domicilio.calle}, ${paciente.domicilio.ciudad}`;
  li.appendChild(p);
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
function mensajeErrorPaciente(mensaje) {
  errorPaciente.textContent = mensaje;
  errorPaciente.style.display = "block";
  setTimeout(() => { errorPaciente.style.display = "none"; }, 3000);
}

export default cargarPacientes;
