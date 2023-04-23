package Sincronized

import Coche

class TallerSyncronized(var aparcamientos: Int = 5, cochesPermitidosAlDia : Int = 60) {

    private var bufer = ArrayList<Coche>()
    private var cochesMetidos = 0
    private var cochesAAreglarEnUnDia = cochesPermitidosAlDia
    private var tallerAbierto = true

    @Synchronized
    fun putUnCocheEnElTaller(coche: Coche):Boolean {
        println("entra en el put del taller")

            while (bufer.size >= aparcamientos) {

            }
        println("el taller NO  esta lleno")
            if (cochesMetidos == cochesAAreglarEnUnDia ){
                println("ya se han metido en el taller todos los coches que se pueden arreglar en el dia")
                tallerAbierto = false
            }else{
                bufer.add(coche)
                cochesMetidos++
            }

        return  tallerAbierto
    }

    @Synchronized
    fun getUnCocheDelTaller(): Coche? {

        if(!tallerAbierto && bufer.size <= 0){
            println("taller cerrado y vacio")
            return null
        }
            while (bufer.size == 0) {
                if(!tallerAbierto && bufer.size <= 0){
                    println("taller cerrado y vacio")
                    return null
                }
            }
            var coche = bufer.removeAt(0)   //para que sea una cola y sea equtativo con los coches
            return coche

    }

}
