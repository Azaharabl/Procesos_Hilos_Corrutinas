package Sincronized

import rentrantLock.Parquin
import rentrantLock.Taller
import java.lang.Thread.sleep
import kotlin.random.Random

class GruaEntradaSyncronized(parkin: ParkingSincroniced, taller: TallerSyncronized, cuantoTardanLasGruas: Pair<Long, Long>): Runnable {
    val parkin = parkin
    val taller = taller
    var tallerAbierto = true
    var cuantoTardanLasGruas = cuantoTardanLasGruas
    override fun run() {
        while(tallerAbierto) {
            var coche = parkin.getDeParquin()
            println("Grua: coche sacado del parquin")
            sleep(Random.nextLong(cuantoTardanLasGruas.first, cuantoTardanLasGruas.second))
            println("Grua: llego al taller")
            tallerAbierto = taller.putUnCocheEnElTaller(coche)
            println("Grua: coche metido en el taller")
        }
        println("Grua: he terminado porque el Taller ha cerrado")
    }

}
