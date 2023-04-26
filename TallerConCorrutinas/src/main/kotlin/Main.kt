import kotlinx.coroutines.*
import models.Coche
import models.Mecanico
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

suspend fun main(args: Array<String>) {
    println("Hello World Taller con Corrutinas!")
    println("En cada funcion creamos distintos coches con distintos tiempos y precios,\n " +
            "por lo que los precios no serán los mismos en los tres ejemplos. \n" +
            " pero podemos ver aproximadamente como con corrutanas en muchisimo más eficiente que secuencial, \n" +
            " e incluso que con hilos ")


    println("Async")
    initTallerConAsync()

    println("Await")
    println("esta forma no es correcta, ya que la seción crítica está sobreprotegida con el atomick interger")
    initTallerConAwait()


    println("secuencial : en este caso hay que esperar bastante")
    initTallerSecuencial()



}

fun initTallerSecuencial() {
    var t = measureTimeMillis {

        var total = 0
        repeat(30) {

            var listaCoches = ArrayList<Coche>()
            repeat(30) { listaCoches.add(Coche()) }
            var mecanico = Mecanico(listaCoches)

            mecanico.listaCoches.forEach {
                sleep(it.tReparacion.toLong())
                total = total + it.precio
            }

        }
       // println("El total con Secuencial es:  " + total)
    }
    println("El tiempo tardado secuencialmente es : " + t)



}

suspend fun initTallerConAwait() {

    //En este ejercicio usamos launch en vez de asynk por lo que no no podemos devolver
    // Al aceder a el mismo bufer de memoria se produce las condiciones de carrera

    var t = measureTimeMillis {

        var miScope =   CoroutineScope(Dispatchers.Default)

        //ahora son Job
        var resultados = mutableListOf<Job>()
        //  var total : Int = 0  // no se puede hacer así porque no estaría la secion crítica protegida
        // no se puede hacer así porque estarías pretegiendo la seción critica pero no e forma exiciente
        var total =AtomicInteger(0)
        repeat(30){

            var r = miScope.launch {

                var listaCoches = ArrayList<Coche>()
                repeat(30){listaCoches.add(Coche())}
                var mecanico = Mecanico(listaCoches)

                mecanico.listaCoches.forEach{
                    delay(it.tReparacion.toLong())
                    total.addAndGet(it.precio)
                }

            }
            resultados.add(r)
        }

        //para esperar a que  haya terminado
        resultados.forEach{ it.join() }


      //  println("El total con Await y AtomickInterger es:  " + total)
    }
    println("El tiempo tardado con Await y AtomickInterger es : " + t)
}

suspend fun initTallerConAsync() = coroutineScope {
    var t = measureTimeMillis {

    //el scope usado es el heredado

    //creamos nuestro scope para usarlo, usamos el dispacher Default que suele hacerse para procesamiento de datos
    var miScope =   CoroutineScope(Dispatchers.Default)

    var resultados = mutableListOf<Deferred<Int>>()
    //hacemos 30 mecanicos cada uno con 30 coches para ver la agilidad
    repeat(30){

        var r = miScope.async {

            var listaCoches = ArrayList<Coche>()
            repeat(30){listaCoches.add(Coche())}
            var mecanico = Mecanico(listaCoches)
            var total : Int = 0

            mecanico.listaCoches.forEach{
                delay(it.tReparacion.toLong())
                total = total + it.precio
            }
            return@async total
        }
        resultados.add(r)
    }

    //para obtener resultado esperamos
    var total = resultados.map { it.await() }
    //println("El total con Async es:  " + total.sum())
    }
    println("El tiempo tardado es : " + t)

}
