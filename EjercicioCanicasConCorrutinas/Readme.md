## Canicas con corrutinas
Este ejercicio trata de una memoria compartida que va ha ser una bolsa de canicas.
Realizaremos distintos ejercicios.

- Una bolsa de canicas donde 4 amigos me regalan canicas y dos ladrones me las roban.
    - resolberemos con launch protegiendo la secion crítica con mutex
    - canales
- Una bolsa de canicas donde mi papa nos da canicas y yo y mis hermanos sacamos canicas.
  - con Chanel
- Una bolsa de canicas donde mis cuatro abuelos me dan canicas y yo saco las canicas.
  - con Chanel




### Diferencias entre los distintos canales:

- val rendezvousChannel = Channel<String>() -> 0  los elementos son enviados únicamente cuando un emisor y un receptor se encuentran.
- val bufferedChannel = Channel<String>(10) -> La capacidad del buffer de este Channel es de 10 elementos
- val conflatedChannel = Channel<String>(CONFLATED) ->siempre ofrece el último valor enviado al buffer, descartando los que no fueron recibidos por nadie.
- val unlimitedChannel = Channel<String>(UNLIMITED) -> Con este Channel, tenemos un buffer con capacidad ilimitada, lo que de la memoria

### Pipelines:
Un pipeline es un patón donde una corrutina produce un conjunto de valores sobre un stream. 
Otra corrutina (o varias) pueden consumir esos valores y procesarlos, o relizar filtros o transformaciones.



Para más información:
https://kotlinlang.org/docs/coroutines-and-channels.html#channels
https://kotlinlang.org/docs/channels.html#pipelines
