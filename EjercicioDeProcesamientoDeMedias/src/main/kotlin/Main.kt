import java.lang.Thread.sleep
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    println("Hello World!")

    println("hello procesamiento de medias con hilos")
    println("El ejercicio trata de dad ina lista de numeros aleatorios cojerlos de 10 en 10,\n" +
            " procesar la media de cada uno de los bloques y luego sumarlas todas.")


    var lista : ArrayList<Int> = generarNumeros(106)
    println("nuestra lista de numeros es : " + lista)

    var listS = ArrayList<Int>()
    lista.forEach { listS.add(it) }
    //realizacionSecuencial(listS)

    var listH = ArrayList<Int>()
    lista.forEach { listH.add(it) }
    realizacioHilos(listH)

    var listF = ArrayList<Int>()
    lista.forEach { listF.add(it) }
    //realizacionFuturos(listF)

}

private fun realizacionFuturos(lista: ArrayList<Int>) {
    println("\n \n")
    var tiempoFutures = measureTimeMillis {
        futures(lista)
    }
    println("Tiempo Futures : " + tiempoFutures)
}

private fun realizacioHilos(lista: ArrayList<Int>) {
    println("\n \n")
    var tiempoHilos = measureTimeMillis {
        hilos(lista)
    }
    println("Tiempo Hilos : " + tiempoHilos)
}

private fun realizacionSecuencial(lista: ArrayList<Int>) {
    println("\n \n")
    var tiempoSecuencial = measureTimeMillis {
        secuencial(lista)
    }
    println("Tiempo Secuencial : " + tiempoSecuencial)
}

fun futures(lista: ArrayList<Int>) {
    println("Futuros calables")


    var poll2 = Executors.newFixedThreadPool(7)

    var listaFuturos = ArrayList<Future<Double>>()
    lista.windowed(10).forEach {
        listaFuturos.add(poll2.submit(
            Callable<Double> {
                var media = it.average()
                println("yo soy callable: mi sublista es:" +it + " y su media es: " + media)
                return@Callable media
            }
        ))
    }



    var listaMedias = ArrayList<Double>()
    for (futuro in listaFuturos) {
        listaMedias.add(futuro.get())
    }
    poll2.shutdown()
    println("\n \n")
    println("todas las medias son : " + listaMedias)
    println("la suma de todas las medis es: "+ listaMedias.sum())
}

fun hilos(lista: ArrayList<Int>) {
    println("hilos")


    var mediasHilos = LinkedBlockingDeque<Double>() //aceden todos los hilos
    var poll = Executors.newFixedThreadPool(7)

    lista.windowed(10).forEach {
        poll.submit{
            var media = it.average()
            println("yo soy hilo: mi sublista es:" + it + " y su media es: " +media )
            mediasHilos.add(media)
        }
    }

    poll.shutdown()
    while (!poll.isTerminated) {
        sleep(1.toLong())
        println("esperando ...")
    }

    println("\n ")
    println("todas las medias son : " + mediasHilos)
    println("la suma de todas las medis es: "+ mediasHilos.sum())

}

fun secuencial(lista: ArrayList<Int>) {

    println("Secuencial")

        var medias = ArrayList<Double>()

        lista.windowed(10).forEach {

            var media = it.average()
            println("creamos sublista :" + it + " y su media es: " + media)
            medias.add(media)
        }
        println("\n ")
        println("todas las medias son : " + medias)
        println("la suma de todas las medias es: " + medias.sum())

}

fun generarNumeros(cantidad : Int): ArrayList<Int> {
    var lista = ArrayList<Int> ()
    repeat(cantidad){lista.add(Random.nextInt(0,101))}
    return  lista
}
