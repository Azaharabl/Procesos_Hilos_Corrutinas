import kotlinx.coroutines.*
import java.util.concurrent.LinkedBlockingDeque
import kotlin.random.Random
import kotlin.system.measureTimeMillis

suspend fun main(args: Array<String>) {
    println("Hello World!")

    println("Generamos numeros")
    var lista = generarNumeros(106)

    var listaS = ArrayList<Int>()
    lista.forEach { listaS.add(it)}
    realizarMediasSecuencial(listaS)

    var listaA = ArrayList<Int>()
    lista.forEach { listaA.add(it)}
    realizarMediasAsynk(listaA)


    var listaL = ArrayList<Int>()
    lista.forEach { listaL.add(it)}
    realizarMediasLanch(listaL)

}

suspend fun realizarMediasLanch(lista: java.util.ArrayList<Int>) {
    var t = measureTimeMillis {

        //no esta bien hecho de este modo ya que aunque protege la seción critica no es eficiente para corrutiinas
        var listaMedias = LinkedBlockingDeque<Double>()


            var jobs = ArrayList<Job>()
            var miEscope = CoroutineScope(Dispatchers.IO)

            lista.windowed(10).forEach {

                var m = miEscope.launch {
                    listaMedias.add(it.average())
                }
                jobs.add(m)
            }


            var resultados = jobs.map { it.join() }

            println("Con lanch el total es : " + listaMedias.sum())


    }
    println("Con lanch el tiempo tardado es : " + t)
}

suspend fun realizarMediasAsynk(lista: java.util.ArrayList<Int>) = coroutineScope {

    var t = measureTimeMillis {

        var miEscope =  CoroutineScope(Dispatchers.Default)

        var res: List<Deferred<Double>> = lista.windowed(10).map {

            miEscope.async { it.average() }
        }

        println("Con Async el total es : " + res.sumOf { it.await() })
    }
    println("Con Async el tiempo tardado es : " +t)
}

fun realizarMediasSecuencial(lista: ArrayList<Int>) {

    var t = measureTimeMillis {

        var listaMedias = ArrayList<Double>()
        lista.windowed(10).forEach {
            listaMedias.add(it.average())
        }

        println("secuencialemnte el total es : " + listaMedias.sum())
    }
    println("secuencialmente el tiempo tardado es : " +t)

}

fun generarNumeros(cantidad : Int): ArrayList<Int> {

        var lista = ArrayList<Int> ()
        repeat(cantidad){lista.add(Random.nextInt(0,101))}
        return  lista

}
