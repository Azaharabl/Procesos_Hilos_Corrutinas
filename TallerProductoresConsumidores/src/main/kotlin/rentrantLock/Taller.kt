package rentrantLock

import Coche
import java.util.concurrent.locks.ReentrantLock

class Taller(var aparcamientos: Int = 5, cochesPermitidosAlDia : Int = 60) {

    private var bufer = ArrayList<Coche>()
    private val lock = ReentrantLock()
    private val esperarParaPoner = lock.newCondition()
    private val esperarParaSacar = lock.newCondition()
    private var cochesMetidos = 0
    private var cochesAAreglarEnUnDia = cochesPermitidosAlDia
    private var tallerAbierto = true

    fun putUnCocheEnElTaller(coche: Coche):Boolean {
        lock.lock()
        try {
            while (bufer.size >= aparcamientos) {
                //si esta llenos espera
                println("el taller esta lleno: me espero")
                esperarParaPoner.await()
            }
            if (cochesMetidos == cochesAAreglarEnUnDia ){
                println("ya se han metido en el taller todos los coches que se pueden arreglar en el dia")
                tallerAbierto = false
            }else{
                bufer.add(coche)
                cochesMetidos++
                println("aviso que el taller no esta vacio")
                esperarParaSacar.signal()
            }
        } finally {
            println(bufer.toString())
            lock.unlock()
        }
        return  tallerAbierto
    }

    fun getUnCocheDelTaller(): Coche? {
        lock.lock()
        try {
            if(!tallerAbierto && bufer.size <= 0){
                //si el coche ya no acepta mas coches y está vacio
                println("aviso que el taller tiene hueco")
                esperarParaSacar.signal()
                return null
            }
            while (bufer.size == 0) {
                //si esta vacio espera
                println("el taler está vacio: me espero")
                esperarParaSacar.await()
            }

            var coche = bufer.removeAt(0)   //para que sea una cola y sea equtativo con los coches
            println("aviso que el taller tiene hueco")
            esperarParaPoner.signal()
            return coche
        } finally {
            lock.unlock()
        }
    }

}
