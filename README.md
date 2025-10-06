# Prueba Técnica | Manejo de Microservicios

Esta es una prueba técnica donde me tocó aplicar mis conocimientos sobre la arquitectura de microservicios, el proyecto gira entorno a ellos y espero que sea de su agrado.


# Problema Planteado

El problema o la solución a la que se quiere llegar, es a realizar una arquitectura de microservicios para un sistema de reservas de vuelos. Este sistema de vuelos debe de estar compuesto de los vuelos, las reservas y los usuarios que los alberguen; se debe de poder consultar los vuelos que existen, filtrarlos, reservar vuelos, poder cancelarlos y revisar los vuelos que tiene reservado un usuario determinado.

## Tecnologías Usadas

Para este proyecto fue necesario del uso de **Postman** para gestionar y poder usar los distintos endpoints que fui creando en mi backend sin necesidad de un frontend, así mismo use como parte fundamental del backend **Spring Boot** debido a que se me solicito una solución que sea robusta y escalable, por lo que consideré Spring Boot como una buena alternativa ya que se trata de un sistema de vuelos donde debe ser robusto y Spring Boot se especializa bastante en ello.
>Nota: Cabe aclarar que el proyecto puede ser realizados en distintas tecnologías para el desarrollo del backend. En un inicio iba a usar Django pero Spring Boot me pareció lo más acertado para este caso en particular.

Del lado de la base de datos use **PostgreSQL** ya que actualmente es la más confiable y a la vez gratuita, por lo que es una buena opción.

## Endpoints

Como endpoints principales tenemos los siguientes:

### flights-service
- `GET: http://localhost:8085/api/vuelos/all`. Obtener los vuelos disponibles.
- `GET: http://localhost:8085/api/vuelos`. Obtener filtrado por query params de origen, destino y fecha.

### booking-service
- `GET: http://localhost:8086/api/reservas/all`. Obtener las reservas disponibles.
- `POST: http://localhost:8086/api/reservas`. Reservar vuelos.
- `DELETE: http://localhost:8086/api/reservas/{{bookingId}}`. Cancelar reservas basado en el ID de la reserva.

### user-service

 - `GET: http://localhost:8087/api/usuarios/all`. Obtener todos los
   usuarios. 
  - `GET:   http://localhost:8087/api/usuarios/{{userId}}/reservas`. Obtener la
   lista de las reservas por usuario.

### user-service --- auth

- `POST: http://localhost:8087/auth/register`. Registrar usuario.
- `POST: http://localhost:8087/auth/login`. Login del usuario.

## Inicializar el Proyecto

Para inicializar el proyecto de manera correcta, asegurate de tener Spring Boot +3.5.6 y Java +21 al menos para correr el proyecto de manera correcta.

1. Clona el repositorio con `git clone <link-del-proyecto>`
2. Una vez clonado, abre PostgreSQL y crea la base de datos con los mismos atributos de los archivos application.yaml
3. Ahora dirigete al CMD y entra a uno de los servicios
4. Desde la carpeta raiz del servicio (por ejemplo "/flights-service" ejecuta el siguiente comando `mvn clean install`. Una vez haya terminado la instalación de las dependencias coloca `mvn spring-boot:run`
5. Repite el proceso anterior con todos los servicios

Una vez hecho esto, tendrás arriba todos los servicios!

>Nota: De manera automática se poblará la base de datos cuando inicialices el servicio de "flights-service".

## Capturas del Proyecto

![](https://i.imgur.com/MX8SOaH.png)

![](https://i.imgur.com/3UeMTiq.png)

![](https://i.imgur.com/OTIx5Z9.png)

![](https://i.imgur.com/hQqOndQ.png)

![](https://i.imgur.com/yP5yycv.png)

![](https://i.imgur.com/aawJXuK.png)


## Manejo de Errores

Manejé errores desde una clase global en cada servicio para los distinto errores que se podían presentar, inclusive entre las comunicaciones de los servicios. A continuación dejo unas validaciones que se muestran desde Postman junto a sus codigos HTTP.

![](https://i.imgur.com/lAgsJaw.png)

![](https://i.imgur.com/EwTEzaj.png)

![](https://i.imgur.com/bhY5ZTX.png)

![](https://i.imgur.com/cSRSRGE.png)

## Aclaraciones

Cabe aclarar que al ser un proyecto donde el tiempo es oro y se necesita aprovechar lo máximo posible, decidí obviar ciertos aspectos de la aplicación que las prefiero aclarar acá. La primera es que como se ve user-service y auth están en 1 solo servicio, esto es debido a cuestiones de tiempo y al estar en Spring Boot el Spring Securit y requiere muchas más configuraciones al hacerlo en un servicio aparte, es por ello que opté por colocarlo todo en 1 solo aunque se sepa que lo recomendado es colocarlo por separados como un servicio de "security-service".

Lo otro que me gustaría aclarar es que realmente utilice las 3 tablas en 1 sola base de datos, pero en un enfoque real lo más óptimo es que cada servicio cuente con su base de datos y configuraciones propias

> Nota: De igual manera incluí la comunicación entre los 3 servicios simulando estar en bases de datos distintas.

Por último, me gustaría recalcar que en la sección de **/docs** del repositorio se encontrará documentación importante del proyecto, como las exportaciones de endpoints en Postman, el diseño de como planteé la arquitectura de microservicios y demás.

**IMPORTANTE:** La población del proyecto la saque de [openflights](https://openflights.org/data) pero debido a que este no tenía algunas columnas de la base de datos, decidí colocarlos mediante un script de python.
