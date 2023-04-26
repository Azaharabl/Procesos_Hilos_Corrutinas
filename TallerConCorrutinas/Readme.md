# Ejercicio De Corrutinas
- en este ejercicio mostraremos la potencia de las corrutinas y 
tambien como los métodos de sincronizacion para proteger la secion critica son diferentes a los de los hilos,
y por lo tanto no son eficientes.

## brebe explicacion de las corrutinas:

### Corrutinas:
- Las corrutinas son una forma de programación asíncrona que permite al programador escribir código que puede pausarse y reanudarse en cualquier momento. Esto permite escribir código asincrónico de una manera más legible y fácil de mantener.

###Launch: 
- Launch es una función en la biblioteca de Kotlin que se utiliza para iniciar una nueva corrutina en segundo plano. Permite ejecutar una tarea en segundo plano sin bloquear la ejecución del hilo principal.

### Async: 
- Async es una función en la biblioteca de Kotlin que permite lanzar varias corrutinas en paralelo y obtener el resultado de todas ellas. Se utiliza para optimizar el rendimiento de las operaciones asíncronas.

### Scope: 
- Scope es un objeto que se utiliza para definir el ámbito de una corrutina. Permite definir las reglas de ejecución de una corrutina, como su tiempo de vida y el hilo de ejecución en el que se ejecuta.

### CoroutineScope: 
- CoroutineScope es una interfaz que extiende Scope y define los métodos necesarios para crear y cancelar corrutinas. Permite al programador definir el ámbito de una corrutina y administrar su ciclo de vida.

### Dispatcher:
- Dispatcher es una clase que se utiliza para definir el hilo de ejecución en el que se ejecutará una corrutina. Permite al programador controlar la asignación de hilos para diferentes tareas y optimizar el rendimiento de las operaciones asíncronas.
  - El Dispatcher "IO" es utilizado para operaciones de entrada y salida (I/O) y tareas de red.
  - El Dispatcher "Default" es utilizado para tareas de computación intensiva y procesamiento de datos.
  
### funcion suspendida: 
- Cuando se llama a una función suspendida, 
el programa continúa ejecutándose mientras la función se ejecuta en segundo plano.
La función devuelve un objeto que puede ser usado para obtener el resultado de la operación
en el futuro, en lugar de esperar a que la operación se complete antes de continuar con el resto del programa.



## Reañlzación del ejercicio

1. primero añadimos la dependencia actualizada de corrutinas para kotlin
- // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

2. haremos el ejercicio con Asynk para que nos devuelva el resultado pedido

3. haremos el mismo ejercicio con Launch que no devuelve, y de este modo veremos que los metodos de sincronizacion de los hilos no son coorecto:
- no son eficientes.
- no protegen adecuadamente la seción crítica.
