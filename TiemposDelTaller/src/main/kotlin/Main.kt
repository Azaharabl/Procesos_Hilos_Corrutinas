import java.util.Random
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    println("Hello World!")

    class Coche (
        var tReparacion : Int = kotlin.random.Random.nextInt(3,300),
        var precio : Int =kotlin.random.Random.nextInt(3,300))

    class Mecanico (
        var listaCoches : ArrayList<Coche> = ArrayList<Coche>()
    )


    var listaCoches1 = ArrayList<Coche>()
    repeat(kotlin.random.Random.nextInt(1,5)) {
            listaCoches1.add(Coche())}
    var listaCoches2 = ArrayList<Coche>()
    repeat(kotlin.random.Random.nextInt(1,5)) {
            listaCoches2.add(Coche())}
    var listaCoches3 = ArrayList<Coche>()
    repeat(kotlin.random.Random.nextInt(1,5)) {
            listaCoches3.add(Coche())}

    var mecanico1 = Mecanico(ArrayList(listaCoches1))
    var mecanico2 = Mecanico(ArrayList(listaCoches2))
    var mecanico3 = Mecanico(ArrayList(listaCoches3))

    //secuencial
    var precioTotalSecuencial = 0

    var tiempoSecuencial = measureTimeMillis {
        mecanico1.listaCoches.forEach{
            Thread.sleep(it.tReparacion.toLong())
            precioTotalSecuencial += it.precio
        }
        mecanico2.listaCoches.forEach{
            Thread.sleep(it.tReparacion.toLong())
            precioTotalSecuencial += it.precio
        }
        mecanico3.listaCoches.forEach{
            Thread.sleep(it.tReparacion.toLong())
            precioTotalSecuencial += it.precio
        }
    }
    println("Secuencial tarda :" + tiempoSecuencial)
    println("Secuencial precios totales :" + precioTotalSecuencial)


    //tiempo con en paralelo con hilos pero sin devolver

    var precioTotalHilos = AtomicInteger(0)
    var tiempoHilos = measureTimeMillis {

        var poll = Executors.newFixedThreadPool(3)

        val tiempoInicio = System.currentTimeMillis()
        poll.submit {
            var preciomecanico1 = 0
            mecanico1.listaCoches.forEach {
                Thread.sleep(it.tReparacion.toLong())
                preciomecanico1 = preciomecanico1 + it.precio
            }
            precioTotalHilos.addAndGet(preciomecanico1)
        }
        poll.submit {

            var preciomecanico2 = 0
            mecanico2.listaCoches.forEach {
                Thread.sleep(it.tReparacion.toLong())
                preciomecanico2 = preciomecanico2 + it.precio
            }
            precioTotalHilos.addAndGet(preciomecanico2)
        }
        poll.submit {
            var preciomecanico3 = 0
            mecanico3.listaCoches.forEach {
                Thread.sleep(it.tReparacion.toLong())
                preciomecanico3 = preciomecanico3 + it.precio
            }
            precioTotalHilos.addAndGet(preciomecanico3)
        }

        poll.shutdown()
        poll.awaitTermination(2000L, TimeUnit.MILLISECONDS)
    }
        println("Hilos tarda: " + tiempoHilos)
        println("Hilos precio total: " + precioTotalHilos)


    //Callables

    var poll2 = Executors.newFixedThreadPool(3)
    var totalPRecioFuturos = 0

    var tiempoConHilosYFuturos = measureTimeMillis {


        val respuesta1 : Future<Int> = poll2.submit (Callable<Int>{
            var precioTotal = 0
            mecanico1.listaCoches.forEach{
                Thread.sleep(it.tReparacion.toLong())
                precioTotal = precioTotal + it.precio
            }
             return@Callable precioTotal})



        val respuesta2 : Future<Int> = poll2.submit(Callable<Int>{
            var precioTotal = 0
            mecanico2.listaCoches.forEach{
                Thread.sleep(it.tReparacion.toLong())
                precioTotal = precioTotal + it.precio
            }
            return@Callable precioTotal })


        val respuesta3 : Future<Int> = poll2.submit(Callable<Int>{
            var precioTotal = 0
            mecanico3.listaCoches.forEach{
                Thread.sleep(it.tReparacion.toLong())
                precioTotal = precioTotal + it.precio
            }
            return@Callable precioTotal })

        //esperamos a que termine
        poll2.shutdown()
        poll2.awaitTermination(2000L, TimeUnit.MILLISECONDS)

        totalPRecioFuturos = totalPRecioFuturos +respuesta1.get()+respuesta2.get() + respuesta3.get()

    }


    println("Futuros tarda: " + tiempoConHilosYFuturos)
    println("Futuros PrecioTotal: " +totalPRecioFuturos)



}