PARTE DEL SERVIDOR QUE PROVEE UN SERVICIO
-----------------------------------------
Hemos creado un Servicio Web RESTful que provee de datos, (pojo/bean -> Perro) desde una bbdd perrera
y tabla 'perro', en formato JSON. La parte que interactúa con la bbdd y el cliente que consume esos
datos está hecha con HIBERNATE.

PARTE DEL CLIENTE 1
-------------------
Está incluido el interfaz del cliente hecho en JQuery Mobile (desde aplicación de móvil) el cual recibe
datos en formato AJAX-JSON mediante GET, crea registros mediante POST, modifica registros mediante PUT
y elimina mediante DELETE.
Arrancar el servidor y escribir: http://localhost:8080/restHibernate/

PARTE DEL CLIENTE 2
-------------------
Si en vez de tratarlo desde una aplicación como ésta queremos una página web donde podamos hacer lo mismo
totalmente documentado tenemos instalado SWAGGER.
Arrancar el servidor y escribir: http://localhost:8080/restHibernate/api.jsp

Se puede utilizar MATERIALIZECSS en la app de móvil para utilizar css de Android (es como Bootstrap)

EXPLICACIÓN Y UTILIZACIÓN PRÁCTICA
----------------------------------
Se utiliza para exportar información en el momento de un lenguaje a otro. Desde el servidor (JAVA) a un
móvil (otro lenguaje) o a otro servidor con PHP u otro lenguaje de programación.
