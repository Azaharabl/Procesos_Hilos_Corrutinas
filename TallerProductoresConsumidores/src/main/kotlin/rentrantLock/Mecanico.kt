package rentrantLock

import Coche
import rentrantLock.Taller
import java.lang.Thread.sleep
import java.util.concurrent.Callable
import kotlin.random.Random

class Mecanico(taller : Taller, descanso : Pair<Long, Long>, tiempoDearreglar : Pair<Long, Long>): Callable<Long> {
    var taller = taller
    var tiempoDeDescanso = descanso
    var tiempoParaArreglar = tiempoDearreglar
    var trabajando = true
    var totalCobrado = 0L
    override fun call(): Long {
        while (trabajando){
            //tomarDescanso
            sleep(Random.nextLong(tiempoDeDescanso.first, tiempoDeDescanso.second))
            //areglar y vr si sigue trabajando
            var coche : Coche? = taller.getUnCocheDelTaller()
            //si obtiene nullo es que el taller esta cerrado y no hay coches te pareas
            if (coche== null) {
                trabajando = false
            }else{
                sleep(Random.nextLong(tiempoParaArreglar.first, tiempoParaArreglar.second))
                println("Mecanico =  Otro coche arreglado!!")
                totalCobrado= totalCobrado + coche.precio
            }
            println("sigo trabajando es: "+ trabajando)
        }
        println("me voy a mi casa, he terminado")
        return totalCobrado
    }



}