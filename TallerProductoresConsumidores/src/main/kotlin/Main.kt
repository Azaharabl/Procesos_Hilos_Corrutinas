import Sincronized.GruaEntradaSyncronized
import Sincronized.MecanicoSyncronized
import rentrantLock.GruaEntrada
import rentrantLock.Mecanico
import Sincronized.ParkingSincroniced
import Sincronized.TallerSyncronized
import rentrantLock.Parquin
import rentrantLock.Taller
import java.lang.Exception
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {

    var s = measureTimeMillis { tallerSecuencial()  }
    var r =  measureTimeMillis { tallerConHilosYMonitorConRentrantLock()  }
    var b = measureTimeMillis { tallerConBlokindeque() }

println("\n ")
    println("tiempo secuencial : " + s )
    println("tiempo Rentran loock : " + r)
    println("tiempo Bloking y atomick : " + b )

    //con Syncronized no es un modo equitativo
    // println("tiempo Syncroniced : " + measureTimeMillis {  tallerConHilosYSyncroniced() })






}







fun tallerConHilosYSyncroniced() {

    //variables que podemos modificar
    var cuantoTardanLasGruas = Pair(200L, 400L)
    var cuantoTardanEnRepararLosmecanicos = Pair(200L, 400L)
    var cuantoDescansasLosMecanicos = Pair(200L, 400L)
    var cuantoCuestaELCoche = Pair(300L, 400L)

    println("hacemos poll parquin y taller")
    var poll = Executors.newFixedThreadPool(7)
    val parquin = ParkingSincroniced()
    val taller = TallerSyncronized()

    //ponemos los coches en el taller a trabes del poll
    repeat(200) {
            parquin.putEnParquin(Coche(Random.nextLong(cuantoCuestaELCoche.first, cuantoCuestaELCoche.second)))
    }
    println(parquin.bufer.size)


    //creamos las gruas que son 2  y los mecanicos y los ponemos a ejecutar a traves del poll
    repeat(2) {
        println("inicioando grua Syncroniced")
        poll.submit {
            GruaEntradaSyncronized(parquin,taller,cuantoTardanLasGruas).run()
        }
    }

    //creamos los mecanicos
    var mecanicos = ArrayList<MecanicoSyncronized> ()
    repeat(3) {
        println("iniciando mecacnicoSincroniced")
        mecanicos.add(MecanicoSyncronized(taller, cuantoDescansasLosMecanicos,cuantoTardanEnRepararLosmecanicos)) }


    var futuros = poll.invokeAll(mecanicos)
    var resultadoTotal = futuros.map { it.get() }.sum()
    println("las ganancias obtenidas con los futures son : "+ resultadoTotal)
    poll.shutdown()


}




fun tallerConHilosYMonitorConRentrantLock() {
    //variables que podemos modificar
    var cuantoTardanLasGruas = Pair(200L, 400L)
    var cuantoTardanEnRepararLosmecanicos = Pair(200L, 400L)
    var cuantoDescansasLosMecanicos = Pair(200L, 400L)
    var cuantoCuestaELCoche = Pair(300L, 400L)

    var poll = Executors.newFixedThreadPool(7)
    val parquin = Parquin()
    val taller = Taller()

    //ponemos los coches en el taller a trabes del poll
   repeat(200) {
        poll.submit {
            parquin.putEnParquin(Coche(Random.nextLong(cuantoCuestaELCoche.first, cuantoCuestaELCoche.second)))
        }
    }

    //creamos las gruas que son 2  y los mecanicos y los ponemos a ejecutar a traves del poll
    repeat(2) {
        poll.submit {
            GruaEntrada(parquin,taller,cuantoTardanLasGruas).run()
        }
    }
    //creamos los mecanicos
    var mecanicos = ArrayList<Mecanico> ()
    repeat(3) { mecanicos.add(Mecanico(taller, cuantoDescansasLosMecanicos,cuantoTardanEnRepararLosmecanicos)) }


    var futuros = poll.invokeAll(mecanicos)
    var resultadoTotal = futuros.map { it.get() }.sum()
    println("las ganancias obtenidas con los futures son : "+ resultadoTotal)
    poll.shutdown()
    System.exit(0)

}

fun tallerSecuencial() {


    val parquin = ArrayList<Coche>()
    val taller = ArrayList<Coche>()
    val maximoDeCochesEnTAller = 5
    var precioTotal = 0L
    var cochesReparados = 0


    // Rellenar el parquin con 200 coches aleatorios
    rellenaParquinDeCochesCreados(parquin)

    var tardaSecuencial = measureTimeMillis {

        while (cochesReparados < 50) {

            println("entra en el juego")
            llenarTallerConLaGrua(parquin, taller, maximoDeCochesEnTAller)

            var precioRep = repararCochesDelTaller(taller, cochesReparados, precioTotal)
            if (precioRep !=null){ precioTotal = precioTotal + precioRep }

            cochesReparados = cochesReparados + 5
        }


        // Mostrar el precio total de las reparaciones
        println("El precio total de todas las reparaciones es $precioTotal€")

    }
    println("en hacerlo secuencial se tarda: $tardaSecuencial ms")
}

fun repararCochesDelTaller(taller: ArrayList<Coche>, cochesReparados: Int, precioTotal : Long): Long? {
// Reparar los coches en el taller
    var coche : Coche? = null
    while (taller.isNotEmpty()) {
        coche = taller.removeAt(0)
        var tiempo = Random.nextLong(3, 300)
        println("soy un mecanico y voy a reparar un coche que cuesta " +
                "la reparacion ${coche.precio} , pero me tomo un descanso de  $tiempo ")
        Thread.sleep(tiempo)
        var tiempoReparacion = Random.nextLong(300, 600)
        println("soy mecanico, me pongo a repara coche me tardará  $tiempoReparacion ")
        Thread.sleep(tiempoReparacion)
        println("termine el coche")
        println("Un coche ha sido reparado por ${coche.precio}€")

    }
    if (coche != null) {
        return precioTotal + coche.precio
    }
    return null
}

private fun rellenaParquinDeCochesCreados(parquin: ArrayList<Coche>) {
    repeat(200) {
        parquin.add(Coche(Random.nextLong(300, 400)))
    }
}

fun llenarTallerConLaGrua(parquin: ArrayList<Coche>, taller:ArrayList<Coche>, maximoDeCochesEnTAller: Int) {
    // Llevar los coches al taller
    while (taller.size<5) {
        println("taller no esta lleno, la grua sigue trallendos coches")
        var coche = parquin.removeAt(0)
        var tiempo = Random.nextLong(200, 400)
        println("cocjo un coche y lo llevo de camino al taller, tardo $tiempo")
        Thread.sleep(tiempo)
        println("he llevado el coche al taller $tiempo")
        taller.add(coche)
    }
}
fun tallerConBlokindeque() {
    //variables que podemos modificar
    var cuantoTardanLasGruas = Pair(200L, 400L)
    var cuantoTardanEnRepararLosmecanicos = Pair(200L, 400L)
    var cuantoDescansasLosMecanicos = Pair(200L, 400L)
    var cuantoCuestaELCoche = Pair(300L, 400L)

    var parquin = LinkedBlockingDeque<Coche>(200)
    var taller = LinkedBlockingDeque<Coche>(5)
    val cochesArreglados = AtomicInteger(0)
    var cochesMetidosEnTaller = AtomicInteger(0)
    var poll = Executors.newFixedThreadPool(7)
    var totalCobrado = AtomicLong(0)
    var tareasEnproceso = AtomicInteger(0)
    var tareasTermonadas = AtomicInteger(0)

    //ponemos los coches en el taller a trabes del poll
    repeat(200) {
        poll.submit {
            tareasEnproceso.addAndGet(1)
            parquin.add(Coche(Random.nextLong(cuantoCuestaELCoche.first, cuantoCuestaELCoche.second)))
            tareasTermonadas.addAndGet(1)
        }
    }
    println(parquin.size)

    //creamos las gruas que son 2  y los mecanicos y los ponemos a ejecutar a traves del poll
    repeat(2) {
        println("inicioando grua BlokingDeque")
        poll.submit {
            tareasEnproceso.addAndGet(1)

            while (cochesMetidosEnTaller.get() < 50) {
                var coche = parquin.pop()
                Thread.sleep(Random.nextLong(cuantoTardanLasGruas.first, cuantoTardanLasGruas.second))
                //tarda
                println("Grua: coche metiendo en el taller")
                //pone un coche en taller
                if (cochesMetidosEnTaller.get() < 50){
                    taller.put(coche)
                    println( taller.toString())
                    cochesMetidosEnTaller.addAndGet(1)
                }
            }
            println("grua :Terminada")
            tareasTermonadas.addAndGet(1)
            println("tareas ya terminadas :" + tareasTermonadas.get())
        }
        println("Grua: he terminado porque el Taller ha cerrado")
    }

    //creamos las gruas que son 2  y los mecanicos y los ponemos a ejecutar a traves del poll

    repeat(3) {
        println("inicioando mecanico BlokingDeque")

        poll.submit {
            tareasEnproceso.addAndGet(1)
            Thread.sleep(Random.nextLong(cuantoDescansasLosMecanicos.first, cuantoDescansasLosMecanicos.second))
            while (cochesArreglados.get() <= 49) {
                println("en el taller no se han arreglado aun 50 coches ")
                while (taller.size !=0 ) {
                    println("el taller aun no está a 0: " + taller.size)
                    try {
                        var coche: Coche = taller.pop()
                        cochesArreglados.addAndGet(1)
                        Thread.sleep(Random.nextLong(cuantoTardanEnRepararLosmecanicos.first, cuantoTardanEnRepararLosmecanicos.second))
                        println("Mecanico =  Otro coche arreglado!!")
                        println(taller.size)
                        totalCobrado.addAndGet(coche.precio)
                    }catch (e : Exception){e.printStackTrace()}
                }
                println("taller esta a 0: " + taller.size)
                println("coches arreglados son :" + cochesArreglados.get()+ "mecanico ")
            }
            println("mecanico terminado " )
            tareasTermonadas.addAndGet(1)
            println(tareasEnproceso.get())
            println(tareasTermonadas.get())
        }

    }


    while (tareasTermonadas.get()<205 ){

    }
    poll.shutdown()

    println("\n")
    println("tareas Terminadas son : "+ tareasTermonadas.get())
    println("total con boking: " + totalCobrado.get())

}