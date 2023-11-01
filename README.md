# TPE_Arquitectura
Autores: 
  - Aiello Franco
  - Gallo Benjamin
  - Luque Atilio

a. Como encargado de mantenimiento quiero poder generar un reporte de uso de monopatines por
kilómetros para establecer si un monopatín requiere de mantenimiento. Este reporte debe poder
configurarse para incluir (o no) los tiempos de pausa.

b. Como administrador quiero poder anular cuentas para inhabilitar el uso momentáneo de la
misma.

c. Como administrador quiero consultar los monopatines con más de X viajes en un cierto año.
    GET http://localhost:8081/administracion/monopatines/año/{year}/minimos-viajes/{minimTrips}
    llama a:
    GET http://localhost:8082/monopatines/año/{year}/minimos-viajes/{minimTrips}
    llama a:
    GET http://localhost:8084/viajes/monopatines/año/{year}/minimos-viajes/{minimTrips}


d. Como administrador quiero consultar el total facturado en un rango de meses de cierto año.
    GET http://localhost:8081/administracion/facturacion/entre
    JSON:
    {
      "startDate": "2015-12-28T01:59:41Z",
      "finishDate": "2020-12-08T00:08:31Z"
    }


e. Como administrador quiero consultar la cantidad de monopatines actualmente en operación,
versus la cantidad de monopatines actualmente en mantenimiento.
    GET http://localhost:8081/administracion/monopatines/cantidad/{status}
    llama a:
    GET http://localhost:8082/monopatines/cantidad/{status}

f. Como administrador quiero hacer un ajuste de precios, y que a partir de cierta fecha el sistema
habilite los nuevos precios.

g. Como usuario quiero lun listado de los monopatines cercanos a mi zona, para poder encontrar
un monopatín cerca de mi ubicación


• Registrar monopatín en mantenimiento (debe marcarse como no disponible para su uso)

• Registrar fin de mantenimiento de monopatín
    PUT http://localhost:8081/administracion/monopatin/4/habilitar

✔• Ubicar monopatín en parada (opcional)

• Agregar monopatín 
    POST http://localhost:8081/administracion/monopatines

• Quitar monopatín
    DELETE http://localhost:8081/administracion/monopatines/{id}

• Registrar parada
    POST http://localhost:8081/administracion/estaciones/nueva

• Quitar parada
    DELETE http://localhost:8081/administracion/estaciones/{id}

• Definir precio

• Definir tarifa extra para reinicio por pausa extensa

• Anular cuenta

• Generar reporte de uso de monopatines por kilómetros

• Generar reporte de uso de monopatines por tiempo con pausas

• Generar reporte de uso de monopatines por tiempo sin pausas
