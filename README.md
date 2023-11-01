# TPE_Arquitectura
Autores: 
  - Aiello Franco
  - Gallo Benjamin
  - Luque Atilio

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
