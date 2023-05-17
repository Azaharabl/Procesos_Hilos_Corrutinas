## Ejercicio de Manejo de flows
Hacemos varios ejercicios de mensajes pero con flows

### SharedFlows y StateFlows

Tanto SharedFlow como StateFlow son clases que permiten la emisión y recepción de valores asincrónicos en Kotlin Coroutines,
pero hay algunas diferencias clave entre ellas.

Concurrencia (Muy importante):
- SharedFlow es seguro para el acceso concurrente desde múltiples hilos, lo que significa que los valores pueden ser emitidos y recolectados desde hilos diferentes.
- Por otro lado, StateFlow está diseñado para ser utilizado en un único hilo, y no es seguro para el acceso concurrente.

Inmutabilidad: 
- StateFlow es inmutable. 
- SharedFlow es mutable.

Múltiples emisiones: 
- SharedFlow permite la emisión múltiple de valores, lo que significa que múltiples valores pueden ser emitidos a
través del flujo sin que el flujo se complete.
- StateFlow sólo permite la emisión de un único valor y luego se completa automáticamente.

Métodos de recolección: 
- StateFlow proporciona un método de recolección llamado collect.
- SharedFlow proporciona dos métodos de recolección: collect y collectLatest(sólo recibe el valor más reciente emitido)


En resumen, StateFlow es útil cuando se necesita un flujo de valores únicos e inmutables, mientras que SharedFlow es útil cuando se necesita un flujo mutable de valores múltiples que puede ser accedido y modificado desde múltiples hilos.
### StateFlows
- Un servicio detablon de anuncios en el que una persona pone losmensajes en el tablon de anuncios y una varias personas los ven
- Un servicio de tablon de anuncios donde varias personas ponen mensaje  varias lo leen
- Un servicio de tablon de anuncios donde varias personas ponen mensaje solo una lo lee
### SharedFlows
- Un servicio de tablon de anuncion en el que una persona pone vacantes a una excursion en el tablon de anuncios y una varias personas los ven y reserban plaza
- Un servicio de tablon de anuncion en el que varias personas ponen vacantes a una excursion en el tablon de anuncios y una varias personas los ven y reserban plaza


https://developer.android.com/kotlin/flow/stateflow-and-sharedflow?hl=es-419