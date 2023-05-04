import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.Channel.Factory.toString
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

suspend fun main(args: Array<String>) {
    println("\n \n Hello Canicas Coroutine World!")

    println("\n \n Una bolsa de canicas donde distintas persona regalan canicas y otas personas las sacan")

    println("\n \n Resolveremos con Launch y Mutex")
    //canicasConLaunch()

    println("\n \n Resolveremos con Chanel(UNLIMITED) M-M ")
    //canicasConChanel()

    println("\n \n ejercicio de un productor y varios cosumidores con canal 1-M")
    //canicasUnoAMuchosCanal()  //fan_out

    println("\n \n ejercicio de un productor y varios cosumidores con canal 1-M usando produce")
    //canicasUnoAMuchosProduce()

    println("\n \n ejercicio de varios productores y un consumidor con canal M-1")
    //canicasMuchosAUno()     //fan_in

    println("\n \n ejercicio de varios productores y un consumidor con canal M-1 usando Actor")
    canicasMuchosAUnoUsandoActor()

    println("\n \n Resolveremos con Chanel(10) M-M  hata que ya hayan cogido todas la canicas que querían")
   //canicasConChanelLimitado()
}

suspend fun canicasMuchosAUnoUsandoActor() {
    println("familiares dan canicas, una niña las coje")
    val miScope = CoroutineScope(Dispatchers.IO)
    val canal = Channel<Canicas>(UNLIMITED)
    var job = ArrayList<Job>()



    //esto es lo unico que cambia, ya que usamos un actor
    var lista1 = ArrayList<Canicas>()

    println("lanzamos hija")
    var actor = miScope.actor<Canicas> {
        for (canicas in canal){
            lista1.add(canicas)
        }
    }

    println("lanzamos papa")
    var papaMetio = 0
    var mamaMetio = 0
    var abueloMetio = 0
    var abuelaMetio = 0


    // Productor papa
    job.add( miScope.launch {
        repeat(10) {
            val num = (1..10).random()
            repeat(num) {canal.send(Canicas())}
            println("Papa Metio $num canicas ")
            papaMetio+=num
        }
    })
    // Productor mama
    job.add( miScope.launch {
        repeat(10) {
            val num = (1..10).random()
            repeat(num) {canal.send(Canicas())}
            println("Mama Metio $num canicas ")
            mamaMetio+=num
        }
    })
    // Productor abuela
    job.add( miScope.launch {
        repeat(10) {
            val num = (1..10).random()
            repeat(num) {canal.send(Canicas())}
            println("abuelaMetio $num canicas ")
            abuelaMetio+=num
        }
    })

    // Productor abuelo
    job.add( miScope.launch {
        repeat(10) {
            val num = (1..10).random()
            repeat(num) {canal.send(Canicas())}
            println("abuelo Metio $num canicas ")
            abueloMetio+=num
        }
    })


    // Sincronizar los trabajos
    job.forEach{ it.join()}
    canal.close()




    println("Al final papa da : $papaMetio, mama da : $mamaMetio, la abuela da : $abuelaMetio, y el abuelo $abueloMetio")
    println("hija recive : ${lista1.size} $lista1")

}

suspend fun canicasUnoAMuchosProduce() {

    val miScope = CoroutineScope(Dispatchers.IO)
    var job = ArrayList<Job>()
    var papaMetio = 0

    // El canal se crea desde el producer
    println("lanzamos padre")
    val canal = miScope.produce {//es igual que antes pero lo producte un productor
        repeat(10) {
            val num = (1..10).random()
            repeat(num) { send(Canicas()) }
            println("Papa Metio $num canicas ")
            papaMetio+=num
        }
        println("Papa se canso de meter canicas, se va a dormir")
    }



    var lista1 = ArrayList<Canicas>()
    var lista2 = ArrayList<Canicas>()
    var lista3 = ArrayList<Canicas>()

    println("lanzamos hijos")
    // Consumidores hijos
    job.add(miScope.launch {
        var canalNOCerradoYVacio = true
        while (canalNOCerradoYVacio){
            try {
                lista1.add( canal.receive())
                println("Hijo1 recibió canica")
            }catch (e: Exception){
                canalNOCerradoYVacio = false
            }
        }
    })

    job.add( miScope.launch {
        var canalNOCerradoYVacio = true
        while (canalNOCerradoYVacio){
            try {
                lista2.add( canal.receive())
                println("Hijo2 recibió canica")
            }catch (e: Exception){
                canalNOCerradoYVacio = false
            }
        }
    })

    job.add(miScope.launch {
        var canalNOCerradoYVacio = true
        while (canalNOCerradoYVacio){
            try {
                lista3.add( canal.receive())
                println("Hijo3 recibió canica")
            }catch (e: Exception){
                canalNOCerradoYVacio = false
            }
        }
    })


    // Sincronizar los trabajos
    job.forEach{ it.join()}


    println("Al final papa da : $papaMetio el hijo1 recibió ${lista1.size} , el hijo2 recibió ${lista2.size} y el hijo3 recibió ${lista3.size}")
    println("hijo 1 : ${lista1.size} $lista1")
    println("hijo 2 : ${lista2.size} $lista2")
    println("hijo 3 : ${lista3.size} $lista3")

}

suspend fun canicasConChanelLimitado() {
    println(" Los Padres dan canicas en una volsa a sus hijos estos comparten volsa de canicas que sacan para jugar. ")
    println(" si la bolsa esta llena los padres se esperan, si no hay canicas los niños se esperan ")
    println(" si la bolsa está vacia y los pafdres no van a meter mas los niños dejan de jugar")
    println(" si los niños paran de jugar antes de que se acaven las bolas imprimimos el resultado de cuantas quedan para mañana")

    println("como podemos ver si el canal tiene  onBufferOverflow = BufferOverflow.SUSPEND si los padres no pueden meter canicas ," +
            "\n porque los niños se han abuerido y la bosa está llenea el programa no termina. para que esto suceda tenemos que \n " +
            " poner el canal en modo onBufferOverflow = BufferOverflow.DROP_LATEST con el cual los padres desecharán las canicas al no ponder meter más")


    //por si queremos probar a modificar valores
    val productores = 6
    val consumidores = 3
    val cuantasVecesDanYQuitan = 5
    val tamañoDelChanel = 10

    //var canal = Channel<Canicas>(tamañoDelChanel, onBufferOverflow = BufferOverflow.SUSPEND)
    //si no entran se esperan a menterlas

    var canal = Channel<Canicas>(tamañoDelChanel, onBufferOverflow = BufferOverflow.DROP_LATEST)
    // si no entran la tiran y no la menten

    var jobsProductores = ArrayList<Job>()
    var jobs = ArrayList<Job>()
    var miScope = CoroutineScope(Dispatchers.IO)

    println("comienzan padres")
    //productores
    repeat(productores){//cuantos productores son
        jobsProductores.add( miScope.launch{
            repeat(cuantasVecesDanYQuitan){//cuantas veces lo hacen
                delay(Random.nextLong(1,100)) //me espero
                var doy = Random.nextInt(5,10)  //cuantas canicas doy
                println("Meto $doy")
                repeat(doy) {canal.send(Canicas())}    //dar
            }
            println("Ya no voy a dar mas canicas, a ver si se aburren")
        })
    }

    println("comienzan a jugar niños")
    //consumidores
    repeat(consumidores){//cuantos productores son
        jobs.add( miScope.launch{

            var quedanBolas = true
            repeat(cuantasVecesDanYQuitan){//cuantas veces lo hacen
                if (quedanBolas){
                    try {
                        delay(Random.nextLong(1,100)) //me espero
                        var quito = Random.nextInt(5,10)  //cuantas canicas quito
                        println("Quito $quito")
                        repeat(quito) {canal.receive()}    //quito
                    }catch (e:Exception){
                        quedanBolas = false
                        println("Niño: no quedan bolas")
                    }
                }

            }
            println("Niño : me voy a casa")
        })
    }

    jobsProductores.forEach { it.join() }   //cuando todos los padres terminen de meter cerramos el chanel
    canal.close()

    jobs.forEach { it.join() } //niños terminan de jugar

    println("la bolsa quedo con : ${canal.toList()}")



}

suspend fun canicasMuchosAUno() {
    println("familiares dan canicas, una niña las coje")
    val miScope = CoroutineScope(Dispatchers.IO)
    val canal = Channel<Canicas>(UNLIMITED)
    var job = ArrayList<Job>()

    println("lanzamos papa")
    var papaMetio = 0
    var mamaMetio = 0
    var abueloMetio = 0
    var abuelaMetio = 0


    // Productor papa
    job.add( miScope.launch {
        repeat(10) {
            val num = (1..10).random()
            repeat(num) {canal.send(Canicas())}
            println("Papa Metio $num canicas ")
            papaMetio+=num
        }
    })
    // Productor mama
    job.add( miScope.launch {
        repeat(10) {
            val num = (1..10).random()
            repeat(num) {canal.send(Canicas())}
            println("Mama Metio $num canicas ")
            mamaMetio+=num
        }
    })
    // Productor abuela
    job.add( miScope.launch {
        repeat(10) {
            val num = (1..10).random()
            repeat(num) {canal.send(Canicas())}
            println("abuelaMetio $num canicas ")
            abuelaMetio+=num
        }
    })

    // Productor abuelo
    job.add( miScope.launch {
        repeat(10) {
            val num = (1..10).random()
            repeat(num) {canal.send(Canicas())}
            println("abuelo Metio $num canicas ")
            abueloMetio+=num
        }
    })




    var lista1 = ArrayList<Canicas>()

    println("lanzamos hija")
    var jobHija = miScope.launch {
        var canalNOCerradoYVacio = true
        while (canalNOCerradoYVacio){
            try {
                lista1.add( canal.receive())
            }catch (e: Exception){
                canalNOCerradoYVacio = false
            }
        }
        println("Hija recibió  todas las canicas")
    }


    // Sincronizar los trabajos
    job.forEach{ it.join()}
    canal.close()
    jobHija.join()



    println("Al final papa da : $papaMetio, mama da : $mamaMetio, la abuela da : $abuelaMetio, y el abuelo $abueloMetio")
    println("hija recive : ${lista1.size} $lista1")


}

suspend fun canicasUnoAMuchosCanal() {
    println("papa da canicas, sus hijos las cojen")

    /**
    La complicación de este ejercicio es que los consumidores
     no terminen hasta que el canal esté vacio, eso lo manejamos a traves de las
     excepciones que nos indican que el canal esta cerado y vacio.
     */

    val miScope = CoroutineScope(Dispatchers.IO)
    val canal = Channel<Canicas>(UNLIMITED)
    var job = ArrayList<Job>()

    println("lanzamos papa")
    var papaMetio = 0
    // Productor papa
    var papaJob = miScope.launch {
        repeat(10) {
            val num = (1..10).random()
            repeat(num) {canal.send(Canicas())}
            println("Papa Metio $num canicas ")
            papaMetio+=num
        }
        println("Papa se canso de meter canicas, se va a dormir")
    }

    var lista1 = ArrayList<Canicas>()
    var lista2 = ArrayList<Canicas>()
    var lista3 = ArrayList<Canicas>()

    println("lanzamos hijos")
    // Consumidores hijos
    job.add(miScope.launch {
        var canalNOCerradoYVacio = true
        while (canalNOCerradoYVacio){
            try {
                lista1.add( canal.receive())
                println("Hijo1 recibió canica")
            }catch (e: Exception){
                canalNOCerradoYVacio = false
            }
        }
    })

    job.add( miScope.launch {
        var canalNOCerradoYVacio = true
        while (canalNOCerradoYVacio){
            try {
                lista2.add( canal.receive())
                println("Hijo2 recibió canica")
            }catch (e: Exception){
                canalNOCerradoYVacio = false
            }
        }
    })

    job.add(miScope.launch {
        var canalNOCerradoYVacio = true
        while (canalNOCerradoYVacio){
            try {
                lista3.add( canal.receive())
                println("Hijo3 recibió canica")
            }catch (e: Exception){
                canalNOCerradoYVacio = false
            }
        }
    })

    // Sincronizar los trabajos
    papaJob.join()
    canal.close()   //cerramos cuando todos los productores terminen

    job.forEach{ it.join()}


    println("Al final papa da : $papaMetio el hijo1 recibió ${lista1.size} , el hijo2 recibió ${lista2.size} y el hijo3 recibió ${lista3.size}")
    println("hijo 1 : ${lista1.size} $lista1")
    println("hijo 2 : ${lista2.size} $lista2")
    println("hijo 3 : ${lista3.size} $lista3")
}

suspend fun canicasConChanel() {
    println("familiares dan canicas, ladrones las roban y al final vemos cuantas hay")

    val miScope = CoroutineScope(Dispatchers.IO)
    val chanel = Channel<Canicas>(UNLIMITED) //ilimitado

    var jobLadron = ArrayList<Job>()
    var jobs = ArrayList<Job>()

    //productores
    repeat(4){
        var amigo = it  //para saber quien lo hace
       jobs.add( miScope.launch {
            repeat(10){
                delay(Random.nextLong(1,100)) //me espero
                var doy = Random.nextInt(5,10)  //cuantas canicas doy
                repeat(doy) {chanel.send(Canicas())}    //dar
                println("Soy amigo $amigo es mi $it vex que hago y regalo $doy canicas, la bosa queda así ${chanel.toString()}")
            }
           println("Soy amigo $amigo y me voy a casa")
        })
    }


    //consumidores
    repeat(2){
        var ladron = it //para saber quien lo hace
        jobLadron.add(miScope.launch {
            repeat(10){
                delay(Random.nextLong(1,100)) //me espero
                var quito = Random.nextInt(1,5) //cuantas canicas quito
                repeat(quito){chanel.receive()} //las quito
                println("Soy ladron $ladron y es mi $it vez que quito y quito $quito canicas, la bosa queda así ${chanel.toString()}")

            }
            println("Soy ladron $ladron y me voy a casa")
        })
    }

    //sincronizo los ladrones
    jobLadron.forEach{ it.join()}
    println("los ladrones terminaron 🏃‍♂️🏃‍♂️")


    //sincronizo amigos
    jobs.forEach{it.join()}
    println("los Amigos terminaron 🏃‍♂️🏃‍♂️")

    chanel.close()


    var lista = chanel.toList()

    println("sacamos lista")
    println(lista)
    println("finalizo")


    /**


    //fin
    var j = miScope.launch {
    while (!chanel.isEmpty) {
    lista.add(chanel.receive())
    }
    }
    j.join()

     */




}

suspend fun canicasConLaunch() {
    println("familiares dan canicas, ladrones las roban y al final vemos cuantas hay : (launch)")

    var miScope = CoroutineScope(Dispatchers.Default)
    var bolsaCanicas = 5 //inicio con 5 canicas
    var mutex = Mutex()

    var productores = ArrayList<Job>()

    //productor hay 4 y cada uno lo hace 10 veces
    repeat(4){
        productores.add(miScope.launch {
            repeat(10){
                delay(Random.nextLong(1,100))   //tardo en regalarte canicas entre 1 y 100
                mutex.withLock {
                    var meto = Random.nextInt(1,7)    //meto de una a 7 canicas
                    bolsaCanicas= bolsaCanicas + meto
                    println("Meto $meto canicas y En la bolsa hay :"+ bolsaCanicas)
                }
            }
        })
    }

    var consumidores = ArrayList<Job>()
    //consumidor son 2 y cada uno lo hace 10 veces
     repeat(2) {
         consumidores.add(miScope.launch {
             repeat(10) {
                 delay(Random.nextLong(1, 100))   //tardo en regalarte canicas entre 1 y 100
                 mutex.withLock {
                     while (bolsaCanicas <1){
                         println("soy ladron pero me espero que no hay canicas")
                     }
                     var robadas = Random.nextInt(0, bolsaCanicas)    //robo de una a 7 canicas
                     println("soy un ladron y en la bolasa hay $bolsaCanicas")
                     bolsaCanicas = bolsaCanicas - robadas
                     println(" Robo canicas $robadas y En la bolsa quedan :" + bolsaCanicas)
                 }
             }
         })
     }

    productores.forEach{ it.join()}
    consumidores.forEach{ it.join()}

    println("El total de canicas que tengo al final es : $bolsaCanicas")

}


