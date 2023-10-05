Entrega proyecto integrador.

Al ejecutar el proyecto, tener abierta la instancia de h2, dentro del 
proyecto se encuentra el archivo asegurándoselas que la ruta de jdbc sea correcta, debido a que sino dara error conection refused. De no poder ingresar a la consola desde el local host, abrirla desde afuera

ejecutar previamente en la base de datos los scripts que se encuentran en 
resources en el archivo sql.

USUARIOS
nombre: admin
contraseña: 0000

nombre: user
contraseña: 0000

la logica del proyecto se basa en que el usuario admin puede realizar toda 
operacion, pero el usuario user, solo puede crear turno, con los pacientes 
y odontologos creados previamente desde un admin. No pueden existir dos 
pacientes con el mismo dni, al igual que dos odontologos con la misma 
matricula.
