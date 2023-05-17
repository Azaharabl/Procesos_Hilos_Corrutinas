import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlin.random.Random


suspend fun main(args: Array<String>) {
    println("Hello Flujos World!")


    //stateFlowsUnProductorVariosLectores()

   // stateFlowsVariosProductoresUnLector()

    //shareFlowsVariosProductoresVariosLectoresYFiltro()

    crearDosFlowYUnirlos()

}


suspend fun shareFlowsVariosProductoresVariosLectoresYFiltro() {
    println("Un servicio detablon de anuncios en el que varios anunciantes 😁 ponen " +
            "los mensajes en el tablon de anuncios y una varias personas 📚 ven 10. " +
            "Algunos ❓ solo quieren los de coches, así que los filtran.")


    var sharedFlow = MutableSharedFlow<String>(
        100,
        10,
        BufferOverflow.DROP_OLDEST
    )

    var miScope = CoroutineScope(Dispatchers.Default)

    var jobs = ArrayList<Job>()
    var jobs2 = ArrayList<Job>()

    //10 aunuciantes van poniendo anuncios
    for (anunciante in 1..40) {
        jobs.add(miScope.launch {
            println("😁Soy anunciante $anunciante me espero un poco")
            delay(Random.nextLong(1, 10000))
            var listaAunucuion = listOf<String>("Anuncio coche", "Anuncio piso", "Anuncio gato", "Anuncio trabajo")
            var anuncio = listaAunucuion[Random.nextInt(0, listaAunucuion.size)]

            sharedFlow.tryEmit(anuncio)
            println("\uD83D\uDE01 Soy anunciante $anunciante pongo anuncio")
        })
    }


    //2 lectores ven cojen todos , las listas son iguales
    for (lector in 1..2) {
        jobs2.add(miScope.launch {
            delay(Random.nextLong(1, 10000))
            println("📚Soy un lector de anuncios $lector leo hasta 10 anuncios, luego me aburro ")
            sharedFlow.take(10).collect{ println("lector $lector " + it) }

            // sharedFlow.collect { lista -> println("$lista") }
            // de este modo no acava el programa, siguen esperando a que el flow acabe


        })
    }

    //1 lectores que cojen solo los de coches
    jobs2.add(miScope.launch {
        println("❓Soy un lector que solo me interesan los coches miro 5 anncios y me voy ")
        var listaCoches = ArrayList<String>()
        delay(100)
        sharedFlow.filter { it.equals("Anuncio coche", true)   }//filtramos
                .take(5).collect{
                    listaCoches.add(it)
            }
        println("❓ mi lista es : $listaCoches")
    })


    //finalizar
    jobs.forEach { it.join() }
    jobs2.forEach { it.join() }







}

suspend fun crearDosFlowYUnirlos() {


    var jobs = ArrayList<Job>()
    val miScope = CoroutineScope(Dispatchers.Default)

//creamos un flow
    var flow1 = flow{
            repeat(20) {
                delay(Random.nextLong(1, 100))
                var listaAunucuion = listOf<String>("Anuncio coche", "Anuncio piso", "Anuncio gato", "Anuncio trabajo")
                var anuncio = listaAunucuion[Random.nextInt(0, listaAunucuion.size)]
                emit(anuncio)
            }
    }

    var flow2= flow {
            repeat(20) {
                delay(Random.nextLong(1, 100))
                var listaAunucuion = listOf<String>("Notas primero", "Notas segundo", "Notas Finales")
                var anuncio = listaAunucuion[Random.nextInt(0, listaAunucuion.size)]
                emit(anuncio)
            }
    }


    var flowTotal = merge(flow1,flow2)  //unimos dos flows

    miScope.launch { flowTotal.collect{ println("🕵️‍♀️llego a la lista total: " + it) }} //mostrar todos los anuncios

//1 lectores que cojen solo los de anuncios
    jobs.add(miScope.launch {
        println("📚Soy un lector que solo me interesan los anuncios miro 5 anncios y me voy ")
        var listaCoches = ArrayList<String>()
        delay(100)
        flowTotal.filter { it.contains("Anuncio ")   }   //filtramos
            .take(5).collect{
                listaCoches.add(it)
            }
        println("\uD83D\uDCDA mi lista es : $listaCoches")
    })

//1 lectores que cojen solo los de notas
    jobs.add(miScope.launch {
        println("\uD83D\uDCCBSoy un lector que solo me interesan las notas miro 5 anncios y me voy ")
        var listaCoches = ArrayList<String>()
        delay(100)
        flowTotal.filter { it.contains("Notas")   }   //filtramos
            .take(5).collect{
                listaCoches.add(it)
            }
        println("\uD83D\uDCCB mi lista es : $listaCoches")
    })

//1 lectores que cojen todo
    jobs.add(miScope.launch {
        println("👌Soy un lector que leo todo solo 20 ")
        var listaCoches = ArrayList<String>()
        delay(100)
        flowTotal.take(20).collect { listaCoches.add(it) }
        println("\uD83D\uDC4CSoy un lector  " + listaCoches)
    })


    jobs.forEach { it.join() }



}

suspend fun stateFlowsVariosProductoresUnLector() {
    println("Un servicio detablon de anuncios en el que varios anunciantes 😁 ponen " +
            "los mensajes en el tablon de anuncios y una varias personas 📚 los ven. " +
            "Podemos ver que aunque algunos ❓ aunque eliminen mensajes el resto pe lectores tiene la lista completa.")



    var stateFlows = MutableStateFlow<List<String>>(listOf())  //nuestro valor obserbable es una lista

    var miScope = CoroutineScope(Dispatchers.Default)

    var jobs = ArrayList<Job>()
    var jobs2 = ArrayList<Job>()


    //50 aunuciantes van poniendo anuncios
    for (anunciante in 1..50) {
        jobs.add(miScope.launch {
            println("😁Soy anunciante $anunciante me espero un poco")
            delay(Random.nextLong(1, 10000))
            var listaAunucuion = listOf<String>("Anuncio coche", "Anuncio piso", "Anuncio gato", "Anuncio trabajo")
            var anuncio = listaAunucuion[Random.nextInt(0, listaAunucuion.size)]

            var nuevaLista = stateFlows.value + anuncio         //obtenemos la lista y la anadimos el nuevo valor
            stateFlows.value = nuevaLista                       //actualizamos el valor de la lista

            println("\uD83D\uDE01 Soy anunciante $anunciante pongo anuncio y se queda así ${stateFlows.value}")
        })
    }

    //100 lectores ventodo
    for (lector in 1..100) {
        jobs.add(miScope.launch {
            delay(Random.nextLong(1, 10000))
            println("📚Soy un lector de anuncios $lector leo los anuncios y leo ${stateFlows.value}")
            //el lector verá una lista completa
        })
    }


    //10 lectores que cojen 1
    for (lector in 1..10) {
        jobs.add(miScope.launch {
            delay(Random.nextLong(1, 10000))

                if (stateFlows.value.size !=0) {
                    var numero = Random.nextInt(stateFlows.value.size)
                    println("❓Soy un lector $lector que quitalos ultimos $numero pero solo para mi " +
                            "y leo los anuncios  ${stateFlows.value.drop(numero)}")
                    //el lector tiene una lista completa y aunque elimina un valor
                    // lo elimina de su lista particular, no del resto
                }
        })
    }

    //finalizar
    jobs.forEach { it.join() }

}

suspend fun stateFlowsUnProductorVariosLectores() {
    println("Un servicio detablon de anuncios en el que una persona 😁 pone " +
            "los mensajes en el tablon de anuncios y una varias personas 📚 los ven. " +
            "Podemos ver que aunque algunos ❓ aunque eliminen mensajes el resto pe lectores tiene la lista completa.")


    var stateFlows =  MutableStateFlow<List<String>>(listOf())  //nuestro valor obserbable es una lista

    var miScope = CoroutineScope(Dispatchers.Default)

    var jobs = ArrayList<Job>()
    var jobs2 = ArrayList<Job>()



    jobs.add(miScope.launch {
    //1 aunuciantes va poniendo anuncios
        for ( anuncio in 1..50){

            println("😁Soy anunciante me espero un poco")
            delay(Random.nextLong(1,500))
            var listaAunucuion = listOf<String>("Anuncio coche","Anuncio piso","Anuncio gato","Anuncio trabajo" )
            var anuncio = listaAunucuion[Random.nextInt(0,listaAunucuion.size)]

            var nuevaLista = stateFlows.value + anuncio         //obtenemos la lista y la anadimos el nuevo valor
            stateFlows.value = nuevaLista                       //actualizamos el valor de la lista

            println("\uD83D\uDE01 Soy anunciante y mi anuncio numero $anuncio esta puesto  ${stateFlows.value}")
        }
    })

    //100 lectores ventodo
    for ( lector in 1..100){
        jobs.add(miScope.launch {
            delay(Random.nextLong(1,10000))
            println("📚Soy un lector de anuncios $lector leo los anuncios y leo ${stateFlows.value}")
            //el lector verá una lista completa
        })
    }


    //10 lectores que cojen 1
    for ( lector in 1..10){
        jobs.add(miScope.launch {
            delay(Random.nextLong(1,10000))
            if (stateFlows.value.size !=0) {
                var numero = Random.nextInt(stateFlows.value.size)
                println("❓Soy un lector $lector que quitalos ultimos $numero pero solo para mi " +
                        "y leo los anuncios  ${stateFlows.value.drop(numero)}")
                //el lector tiene una lista completa y aunque elimina un valor lo elimina de su lista particular, no del resto
            }
        })
    }

    //finalizar
    jobs.forEach { it.join() }




}

