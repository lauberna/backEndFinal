// Variables y elementos para odontólogos
const urlOdontologos = "http://localhost:8080/odontologos";
const olO = document.querySelector("#listaO");
const errorOdontologo = document.querySelector("#errO");


//LLAMADAS APIS

async function get() {
  try {
    const response = await fetch(urlOdontologos);
    return await response.json();
  }
  catch {
    throw new Error("error con la api");
  }
}



//LOGICA - CREAR, EDITAR, LISTAR, ELIMINAR

function crearOdontologos(odontologo) {
  const li = document.createElement("li");
  const p = document.createElement("p");
  p.classList.add("listP");
  p.textContent = `nombre: ${odontologo.nombre} ${odontologo.apellido}, matrícula: ${odontologo.matricula}`;
  li.appendChild(p);
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

function mensajeErrorOdontologo(mensaje) {
  errorOdontologo.textContent = mensaje;
  errorOdontologo.style.display = "block";
  setTimeout(() => {
    errorOdontologo.style.display = "none";
  }, 3000);
}


export default cargarOdontologos;
