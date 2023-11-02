# TPE_Arquitectura
Autores: 
  - Aiello Franco
  - Gallo Benjamin
  - Luque Atilio

Implementamos normalRate y pauseRate. Durante la pausa (de maximo 15 minutos) se cobra pauseRate. Al finalizarse la pausa por accion del usuario o por transcurso de los 15 minutos (lo que ocurra primero), vuelve el normalRate. De esta manera logramos que usar la pausa tenga sentido para el usuario (ahorrando dinero), pero no deja de tener un minimo costo para no abusar siempre de la totalidad de la misma.
Tambien tenemos una tabla con ambos precios y la fecha a partir de la cual se hacen efectivos, pudiendo cargar multiples periodos con sus precios.

1ra ENTREGA

3)
a. Como encargado de mantenimiento quiero poder generar un reporte de uso de monopatines por
kilómetros para establecer si un monopatín requiere de mantenimiento. Este reporte debe poder
configurarse para incluir (o no) los tiempos de pausa.

b. Como administrador quiero poder anular cuentas para inhabilitar el uso momentáneo de la
misma.

    PUT http://localhost:8081/administracion/cuenta/{id}/suspender

    Habilitar: 
        PUT http://localhost:8081/administracion/cuenta/{id}/habilitar

c. Como administrador quiero consultar los monopatines con más de X viajes en un cierto año.

    GET http://localhost:8081/administracion/monopatines/año/{year}/minimos-viajes/{minimTrips}
    
    *llama a:
    GET http://localhost:8082/monopatines/año/{year}/minimos-viajes/{minimTrips}
    *llama a: *(falta implementar)*
    GET http://localhost:8084/viajes/monopatines/año/{year}/minimos-viajes/{minimTrips}


d. Como administrador quiero consultar el total facturado en un rango de meses de cierto año.

    GET http://localhost:8081/administracion/facturacion/entre
    JSON:
    {
      "startDate": "2022-01-28T01:59:41Z",
      "finishDate": "2023-05-08T00:08:31Z"
    }


e. Como administrador quiero consultar la cantidad de monopatines actualmente en operación,
versus la cantidad de monopatines actualmente en mantenimiento.

    GET http://localhost:8081/administracion/monopatines/cantidad/{status}
    Los status existentes son: 
                    - out of service (out-of-service en la URL) 
                    - free 
                    - busy

    *llama a:
    GET http://localhost:8082/monopatines/cantidad/{status}

f. Como administrador quiero hacer un ajuste de precios, y que a partir de cierta fecha el sistema
habilite los nuevos precios.

g. Como usuario quiero un listado de los monopatines cercanos a mi zona, para poder encontrar
un monopatín cerca de mi ubicación


• Registrar monopatín en mantenimiento (debe marcarse como no disponible para su uso)
    PUT http://localhost:8081/administracion/monopatin/{id}/mantenimiento

• Registrar fin de mantenimiento de monopatín
    PUT http://localhost:8081/administracion/monopatin/{id}/habilitar

• Agregar monopatín 
    POST http://localhost:8081/administracion/monopatines

• Obtener todos los monopatines
    GET http://localhost:8081/administracion/monopatines

• Quitar monopatín
    DELETE http://localhost:8081/administracion/monopatines/{id}

• Registrar parada
    POST http://localhost:8081/administracion/estaciones/nueva

• Quitar parada
    DELETE http://localhost:8081/administracion/estaciones/{id}
    
TODO
• Generar reporte de uso de monopatines por kilómetros

• Generar reporte de uso de monopatines por tiempo con pausas

• Generar reporte de uso de monopatines por tiempo sin pausas
