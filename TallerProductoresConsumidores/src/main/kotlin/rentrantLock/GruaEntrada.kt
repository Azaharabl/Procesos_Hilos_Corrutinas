package rentrantLock

import rentrantLock.Parquin
import rentrantLock.Taller
import java.lang.Thread.sleep
import kotlin.random.Random

class GruaEntrada(parkin: Parquin, taller: Taller, cuantoTardanLasGruas: Pair<Long, Long>): Runnable {
    val parkin = parkin
    val taller = taller
    var tallerAbierto = true
    var cuantoTardanLasGruas = cuantoTardanLasGruas
    override fun run() {
        while(tallerAbierto) {
            //grua coche un choche del parquin
            var coche = parkin.getDeParquin()
            //tarda
            sleep(Random.nextLong(cuantoTardanLasGruas.first, cuantoTardanLasGruas.second))
            println("Grua: coche metido en el taller")
            //pone un coche en taller
            tallerAbierto = taller.putUnCocheEnElTaller(coche)
        }
        println("Grua: he terminado porque el Taller ha cerrado")
    }

}
