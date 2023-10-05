import { actualizar } from "./turnos.js";

const urlOdontologos = "http://localhost:8080/odontologos";
const urlOdontologosEditar = "http://localhost:8080/odontologos/editar";
const urlOdontologosBuscar = "http://localhost:8080/odontologos/buscar/";
const urlOdontologosEliminar = "http://localhost:8080/odontologos/";

// Variables y elementos para odontólogos
const inpNO = document.querySelector("#inpNO");
const inpAO = document.querySelector("#inpAO");
const inpMO = document.querySelector("#inpMO");
const formO = document.querySelector("#formO");
const olO = document.querySelector("#listaO");
const guardarO = document.querySelector("#guardarO");
const cancelarO = document.querySelector("#cancelarO");
const errorOdontologo = document.querySelector("#errO");
const errorModal = document.querySelector("#errMO");
const crearO = document.querySelector("#crearO");
const miModalOdontologo = document.getElementById("miModalO");
const cerrarModalBtnO = document.getElementById("cerrarModalO");
let modoO = "";


//LLAMADAS APIS

async function obtenerOdontologoId(id) {
  const response = await fetch(`${urlOdontologosBuscar}${id}`);
  return await response.json();
}

async function get() {
  try {
    const response = await fetch(urlOdontologos);
    return await response.json();
  }
  catch {
    throw new Error("error con la api");
  }
}

async function postO(body) {
  try {
    const response = await fetch(urlOdontologosEditar, {
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
    const response = await fetch(urlOdontologos, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body)
    });
    return await response.json();
  } catch {
    throw new Error("error con la api");
  }
}

//LOGICA - CREAR, EDITAR, LISTAR, ELIMINAR

function crearOdontologos(odontologo) {
  const li = document.createElement("li");
  const p = document.createElement("p");
  p.classList.add("listP");
  p.textContent = `nombre: ${odontologo.nombre} ${odontologo.apellido}, matrícula: ${odontologo.matricula}`;
  const btnEditar = document.createElement("button");
  const btnEliminar = document.createElement("button");
  btnEditar.textContent = "Editar";
  btnEliminar.textContent = "Eliminar";
  btnEditar.dataset.odontologoId = odontologo.id;
  btnEliminar.addEventListener("click", (e) => {
    eliminarOdontologo(odontologo.id);
  });
  li.appendChild(p);
  li.appendChild(btnEditar);
  li.appendChild(btnEliminar);
  return li;
}

async function cargarOdontologos() {
  get()
    .then(odontologos => {
      olO.innerHTML = "";
      if (!odontologos.response) {
        mensajeErrorOdontologo(odontologos.body);
      } else {
        console.log(odontologos);
        odontologos.body.forEach((odontologo) => {
          const odontologoElement = crearOdontologos(odontologo);
          olO.appendChild(odontologoElement);
        });
      }
    }).catch(err => mensajeErrorOdontologo(err));
}

function nuevoOdontologo() {
  modoO = "guardar";
  inpMO.readOnly = false;
  abrirModalO();
  const nuevoOdontologo = {
    nombre: inpNO.value,
    apellido: inpAO.value,
    matricula: inpMO.value,
  };
  return nuevoOdontologo;
}

function guardarOdontologo(odontologoId, aux) {
  if (aux == "editar") {
    obtenerOdontologoId(odontologoId)
      .then((odontologo) => {
        const odontologoEditado = {
          id: odontologo.body.id,
          nombre: inpNO.value,
          apellido: inpAO.value,
          matricula: odontologo.body.matricula,
        };
        postO(odontologoEditado)
          .then((res) => {
            if (res.response) {
              cerrarFormO();
              actualizar();
              console.log(res);
            } else throw new Error(res.body);
          }).catch(e => mensajeErrorOdontologoModal(e));
      });
  }
  if (aux == "guardar") {
    crear(nuevoOdontologo())
      .then(res => {
        if (res.response) {
          cerrarFormO();
          actualizar();
          console.log(res);
        } else throw new Error(res.body);
      }).catch(e => mensajeErrorOdontologoModal(e));
  }
}

function mostrarDatosOdontologo(odontologoId) {
  modoO = "editar";
  inpMO.readOnly = true;
  abrirModalO();
  formO.reset();
  guardarO.dataset.odontologoId = odontologoId;
  fetch(`${urlOdontologosBuscar}${odontologoId}`)
    .then((response) => response.json())
    .then((odontologo) => {
      inpNO.value = odontologo.body.nombre;
      inpAO.value = odontologo.body.apellido;
      inpMO.value = odontologo.body.matricula;
    })
    .catch((error) => {
      mensajeErrorOdontologoModal(error.message);
    });
}

function eliminarOdontologo(odontologoId) {
  fetch(`${urlOdontologosEliminar}${odontologoId}`, {
    method: "DELETE",
  })
    .then((response) => response.json())
    .then((res) => {
      if (res.error) {
        throw new Error("Error con la API");
      }
      console.log(res);
      cerrarFormO();
      actualizar();
    })
    .catch((error) => {
      mensajeErrorOdontologo(error.message);
    });
}

function abrirModalO() {
  miModalOdontologo.style.display = "block";
  errorOdontologo.style.display = "none";
}

function mensajeErrorOdontologo(mensaje) {
  errorOdontologo.textContent = mensaje;
  errorOdontologo.style.display = "block";
  setTimeout(() => {
    errorOdontologo.style.display = "none";
  }, 3000);
}

function mensajeErrorOdontologoModal(mensaje) {
  errorModal.textContent = mensaje;
  errorModal.style.opacity = "1";
  setTimeout(() => {
    errorModal.style.opacity = "0";
  }, 3000);
}

function cerrarFormO() {
  miModalOdontologo.style.display = "none";
  formO.reset();
}


cancelarO.addEventListener("click", (e) => {
  e.preventDefault();
  cerrarFormO();
});


olO.addEventListener("click", (e) => {
  const target = e.target;
  if (target.tagName === "BUTTON" && target.textContent === "Editar") {
    e.preventDefault();
    mostrarDatosOdontologo(target.dataset.odontologoId);
  }
});

guardarO.addEventListener("click", (e) => {
  e.preventDefault();
  if (modoO === "guardar") {
    guardarOdontologo(e.target.dataset.odontologoId, "guardar");
  }
  if (modoO === "editar") {
    guardarOdontologo(e.target.dataset.odontologoId, "editar");
  }
});

crearO.addEventListener("click", (e) => {
  e.preventDefault();
  nuevoOdontologo();
});

cerrarModalBtnO.addEventListener("click", cerrarFormO);

window.addEventListener("click", (e) => {
  if (e.target === miModalOdontologo) {
    cerrarFormO();
  }
});

export default cargarOdontologos;
