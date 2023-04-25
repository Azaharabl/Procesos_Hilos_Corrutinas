package Sincronized

import Coche

class TallerSyncronized(var aparcamientos: Int = 5, cochesPermitidosAlDia : Int = 60) {

    private var bufer = ArrayList<Coche>()
    private var cochesMetidos = 0
    private var cochesAAreglarEnUnDia = cochesPermitidosAlDia
    private var tallerAbierto = true

    private val lock = java.lang.Object()   //para realizar el Sincroniced en kotlin

    fun putUnCocheEnElTaller(coche: Coche):Boolean = synchronized(lock) {
        println("entra en el put del taller")

            while (bufer.size >= aparcamientos) {
                lock.wait()
            }
        println("el taller NO  esta lleno")
            if (cochesMetidos == cochesAAreglarEnUnDia ){
                println("ya se han metido en el taller todos los coches que se pueden arreglar en el dia")
                tallerAbierto = false
            }else{
                bufer.add(coche)
                cochesMetidos++
            }

        lock.notifyAll()
        return  tallerAbierto
    }

    @Synchronized
    fun getUnCocheDelTaller(): Coche? = synchronized(lock){

        if(!tallerAbierto && bufer.size <= 0){
            println("taller cerrado y vacio")
            lock.notifyAll()
            return null
        }
            while (bufer.size == 0) {
                lock.wait()
                if(!tallerAbierto && bufer.size <= 0){
                    println("taller cerrado y vacio")
                    lock.notifyAll()
                    return null
                }
            }
            var coche = bufer.removeAt(0)   //para que sea una cola y sea equtativo con los coches
        lock.notifyAll()
            return coche

    }

}
