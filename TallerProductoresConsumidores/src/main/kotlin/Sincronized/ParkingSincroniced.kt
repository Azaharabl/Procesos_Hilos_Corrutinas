package Sincronized

import Coche

class ParkingSincroniced(var aparcamientos: Int = 200) {

    private val maximo = aparcamientos
    var bufer = ArrayList<Coche>()

    private val lock = java.lang.Object()   //para realizar el Sincroniced en kotlin


    fun putEnParquin(coche: Coche) = synchronized(lock) {
        while (bufer.size == maximo){
            lock.wait()
        }
        bufer.add(coche)
        lock.notifyAll()
    }


    fun getDeParquin(): Coche =  synchronized(lock) {
        while (bufer.size==0){
            lock.wait()
        }
        var value = bufer.removeAt(bufer.size - 1)
        lock.notifyAll()
        return value
    }
}