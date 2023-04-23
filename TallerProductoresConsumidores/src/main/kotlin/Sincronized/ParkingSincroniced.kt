package Sincronized

import Coche

class ParkingSincroniced(var aparcamientos: Int = 200) {
    private val maximo = aparcamientos

     var bufer = ArrayList<Coche>()

    @Synchronized
    fun putEnParquin(coche: Coche) {

        if(bufer.size==200){
            println("Esta lleno el parquin, no poner mas")
        }else{
            bufer.add(coche)
        }
    }

    @Synchronized
    fun getDeParquin(): Coche {
        while (bufer.size==0){

        }
        var value = bufer.removeAt(bufer.size - 1)
        return value
    }
}