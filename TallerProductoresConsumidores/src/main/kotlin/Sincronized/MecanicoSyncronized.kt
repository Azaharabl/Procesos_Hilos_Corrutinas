package Sincronized

import Coche
import rentrantLock.Taller
import java.lang.Thread.sleep
import java.util.concurrent.Callable
import kotlin.random.Random

class MecanicoSyncronized(taller : TallerSyncronized, descanso : Pair<Long, Long>, tiempoDearreglar : Pair<Long, Long>): Callable<Long> {
    var taller = taller
    var tiempoDeDescanso = descanso
    var tiempoParaArreglar = tiempoDearreglar
    var trabajando = true
    var totalCobrado = 0L
    override fun call(): Long {
        while (trabajando){
            println("Mecanico : descanso")
            sleep(Random.nextLong(tiempoDeDescanso.first, tiempoDeDescanso.second))
            println("Mecanico : intento cojer coche del taller")
            var coche : Coche? = taller.getUnCocheDelTaller()
            println("Mecanico : cojido coche del taller")
            if (coche== null) {
                trabajando = false
            }else{
                sleep(Random.nextLong(tiempoParaArreglar.first, tiempoParaArreglar.second))
                println("Mecanico =  Otro coche arreglado!!")
                totalCobrado= totalCobrado + coche.precio
            }
            println("Mecanico : sigo trabajando es: "+ trabajando)
        }
        println("Mecanico: me voy a mi casa, he terminado")
        return totalCobrado
    }



}