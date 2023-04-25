
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


fun main(args: Array<String>) {
    println("Hello Procesos World!")

    println("\n\n obtenemos nuestro sistema operativo")
    val sistemaOperativo = System.getProperty("os.name") ?: "desconocido"
    println(sistemaOperativo)

    if (sistemaOperativo.contains("Windows", true)) {

        //realizarComandosParaWindows()

        realizarConComandosDeLinuxEnWindows()
    }


    if (sistemaOperativo.contains("Linux", true)) {
        realizarComandosParaLinux()
    }


}

fun realizarComandosParaLinux() {
    var comandInit = "bash"
    var c = "-c"

    println("ir a un diresctorio: cd")

    val commandCD = "bash -c cd C:\\Users\\MiUsuario\\Documents"
    val processCD = ProcessBuilder(commandCD.split(" ")).start()
    var okCD = processCD.waitFor()
    if (okCD == 0){
        println("el proceso cd se ha realizado correctamente")
        var readerCD = BufferedReader(InputStreamReader(processCD.inputStream))
        readerCD.lines().forEach { println(it) }
        readerCD.close()
    }else{
        println("el proceso cd NO se ha realizado correctamente")
        var readerCD = BufferedReader(InputStreamReader(processCD.errorStream))
        readerCD.lines().forEach { println(it) }
        readerCD.close()
    }



    println("\n" + "\n" + "  ejecutar un jar: java -jar para ello necesitamos un archivo.jar si no es así imprimimos el error")
    val commandJar = "bash -c  java -jar MiArchivo.jar"
    val processJar = ProcessBuilder(commandJar.split(" ")).start()
    var okJar = processJar.waitFor()
    if (okJar == 0 ){
        println(" el proceso Jar se ha ejecutado con exito")
        val readerJAR = BufferedReader(InputStreamReader(processJar.inputStream))
        var line: String? = readerJAR.readLine()
        while (line != null) {
            println(line)
            line = readerJAR.readLine()
        }
        readerJAR.close()
    }else{
        println(" el proceso Jar NO se ha ejecutado con exito")
        val readerJAR = BufferedReader(InputStreamReader(processJar.errorStream))
        var line: String? = readerJAR.readLine()
        while (line != null) {
            println(line)
            line = readerJAR.readLine()
        }
        readerJAR.close()
    }



    println("\n" + "\n" + " listar directorios: ls + path")

    val commandDir = "bash -c  ls " + System.getProperty("user.dir")
    val processDir = ProcessBuilder(commandDir.split(" ")).start()
    println(commandDir)
    var okLs =  processDir.waitFor()
    if(okLs==0){
        println("El proceso ls ha finalizado correctamente")
        val resultDir = BufferedReader(InputStreamReader(processDir.inputStream))
        var lineDir: String? = resultDir.readLine()
        while (lineDir != null) {
            println(lineDir)
            lineDir = resultDir.readLine()
        }
        resultDir.close()
    }else{
        println("El proceso ls NO ha finalizado correctamente")
        val resultDir = BufferedReader(InputStreamReader(processDir.errorStream))
        var lineDir: String? = resultDir.readLine()
        while (lineDir != null) {
            println(lineDir)
            lineDir = resultDir.readLine()
        }
        resultDir.close()
    }



    println("\n" + "\n" + "cat ")
    val commandType = "bash -c cat " + System.getProperty("user.dir") + File.separator + "build.gradle.kts"
    println(commandType)
    val processType = ProcessBuilder(commandType.split(" ")).start()
    var okCat = processType.waitFor()
    if (okCat == 0 ){
        println("proceso cat ha terminado correctamente")
        var resultType = BufferedReader(InputStreamReader(processType.inputStream))
        resultType.readLines().forEach { println(it) }
        resultType.close()
    }else{
        println("proceso cat NO ha terminado correctamente")
        var resultType = BufferedReader(InputStreamReader(processType.errorStream))
        resultType.readLines().forEach { println(it) }
        resultType.close()
    }


    println("\n" + "\n" + "cp ")

    val commandXcopy =
        "bash -c cp" + System.getProperty("user.dir") + File.separator + "build.gradle.kts " + System.getProperty("user.dir") + File.separator + "build.gradlecopiado.kts"
    println(commandXcopy)
    val processXcopy = ProcessBuilder(commandXcopy.split(" ")).start()
    var okXcopy = processXcopy.waitFor()
    if(okXcopy==0){
        println(" el proceso cp ha finalizado correctamnete")
        var resultXcopy = BufferedReader(InputStreamReader(processXcopy.inputStream))
        resultXcopy.readLines().forEach { println(it) }
        resultXcopy.close()
    }else{
        println(" el proceso cp NO ha finalizado correctamnete")
        var resultXcopy = BufferedReader(InputStreamReader(processXcopy.errorStream))
        resultXcopy.readLines().forEach { println(it) }
        resultXcopy.close()
    }


    println(
        "\n" +
                "\n" + "cp "
    )

    val commandXcopyDir =
        "bash -c cp " + System.getProperty("user.dir") + File.separator + ".idea " + System.getProperty("user.dir") + File.separator + ".ideaCopy"
    println(commandXcopyDir)
    val processXcopyDir = ProcessBuilder(commandXcopyDir.split(" ")).start()
    okXcopy = processXcopyDir.waitFor()
    if(okXcopy==0){
        println(" el proceso cp ha finalizado correctamnete")
        var resultXcopy = BufferedReader(InputStreamReader(processXcopyDir.inputStream))
        resultXcopy.readLines().forEach { println(it) }
        resultXcopy.close()
    }else{
        println(" el proceso cp NO ha finalizado correctamnete")
        var resultXcopy = BufferedReader(InputStreamReader(processXcopyDir.errorStream))
        resultXcopy.readLines().forEach { println(it) }
        resultXcopy.close()
    }


    println("\n" + "\n" + "  greep ")

    var textoABuscar = "version "
    val commandFind =
        "bash -c greep " + textoABuscar + System.getProperty("user.dir") + File.separator + "build.gradle.kts "
    val processFind = ProcessBuilder(commandFind.split(" ")).start()
    var okFinf = processFind.waitFor()
    if (okFinf == 0 ){
        println("el proceso greep ha terminado correctamente")
        var resultFind = BufferedReader(InputStreamReader(processFind.inputStream))
        resultFind.readLines().forEach { println(it) }
        resultFind.close()
    }else{
        if (okFinf == 0 ){
            println("el proceso greep ha terminado correctamente")
            var resultFind = BufferedReader(InputStreamReader(processFind.errorStream))
            resultFind.readLines().forEach { println(it) }
            resultFind.close()
        }
    }

}


fun realizarComandosParaWindows() {
    //hacemos comandos de windows
    var comandInit = "cmd.exe"
    var c = "/c"

    println("ir a un diresctorio: cd")

    val commandCD = "cmd /c cd C:\\Users\\MiUsuario\\Documents"
    val processCD = ProcessBuilder(commandCD.split(" ")).start()
    var ok = processCD.waitFor()
    if (ok == 0) {
        println("el proceso ha sido correcto")
        var readerCD = BufferedReader(InputStreamReader(processCD.getInputStream()))
        readerCD.lines().forEach { println(it) }
        readerCD.close()
    }


    println("\n" + "\n" + "  ejecutar un jar: java -jar para ello necesitamos un archivo.jar si no es así imprimimos el error")
    val commandJar = "cmd /c java -jar MiArchivo.jar"
    val processJar = ProcessBuilder(commandJar.split(" ")).start()
    var okJar = processJar.waitFor()
    if(okJar == 0 ){
        println("el proceso Java -jar ha sido correcto")
        val readerJAR = BufferedReader(InputStreamReader(processJar.inputStream))
        var line: String? = readerJAR.readLine()
        while (line != null) {
            println(line)
            line = readerJAR.readLine()
        }
        readerJAR.close()

    }else{
        println("el proceso java -jarr  NO se ha ejecutado correctamente")
        val errorReaderJAR = BufferedReader(InputStreamReader(processJar.errorStream))

        var errorLine: String? = errorReaderJAR.readLine()
        while (errorLine != null) {
            println(errorLine)
            errorLine = errorReaderJAR.readLine()
        }
        errorReaderJAR.close()
    }


    println("\n" + "\n" + " listar directorios: dir /b + path")

    val commandDir = "cmd /c dir /b " + System.getProperty("user.dir")
    val processDir = ProcessBuilder(commandDir.split(" ")).start()
    println(commandDir)
    var okDir = processDir.waitFor()
    if (okDir == 0) {
        println("el proceso Dir se ha ejecutado correctamente")

        val resultDir = BufferedReader(InputStreamReader(processDir.inputStream))
        var lineDir: String? = resultDir.readLine()
        while (lineDir != null) {
            println(lineDir)
            lineDir = resultDir.readLine()
        }

        resultDir.close()
    }



    println("\n" + "\n" + "Type es el comando equivalente a cat en windows ")
    val commandType = "cmd /c type " + System.getProperty("user.dir") + File.separator + "build.gradle.kts"
    println(commandType)
    val processType = ProcessBuilder(commandType.split(" ")).start()
    var okType = processType.waitFor()
    if (okType ==0){
        println("el proceso de Type se ha ejecutado correctamente")
        var resultType = BufferedReader(InputStreamReader(processType.inputStream))
        resultType.readLines().forEach { println(it) }

    }


    println("\n" + "\n" + "Xcopy equivalente a cp para windows con un fichero")

    val commandXcopy =
        "cmd /c xcopy " + System.getProperty("user.dir") + File.separator + "build.gradle.kts " + System.getProperty(
            "user.dir"
        ) + File.separator + "build.gradlecopiado.kts"
    println(commandXcopy)
    val processXcopy = ProcessBuilder(commandXcopy.split(" ")).start()
    processXcopy.outputStream.bufferedWriter().use { it.write("F") }//F SI ES UN FICHERO
    var xcopiFile = processXcopy.waitFor()
    if (xcopiFile ==0){
        println(" el proceso xCopy con ficheros se ha ejecutado correctamenre")
        var resultXcopy = BufferedReader(InputStreamReader(processXcopy.inputStream))
        resultXcopy.readLines().forEach { println(it) }
        resultXcopy.close()
    }else{
        println(" el proceso xCopy con ficheros NO se ha ejecutado correctamenre, \n" +
                " seguramente porque ya lo tienes creado de haber ejecutado este programa varias veces")
        var resultXcopyFail = BufferedReader(InputStreamReader(processXcopy.errorStream))
        resultXcopyFail.readLines().forEach { println(it) }
        resultXcopyFail.close()
    }


    println("\n" + "\n" + "Xcopy equivalente a cp para windows con un directorio")

    val commandXcopyDir =
        "cmd /c xcopy " + System.getProperty("user.dir") + File.separator + ".idea " + System.getProperty("user.dir") + File.separator + ".ideaCopy"
    println(commandXcopyDir)
    val processXcopyDir = ProcessBuilder(commandXcopyDir.split(" ")).start()
    var entradaDeXcopyDir = processXcopyDir.outputStream.bufferedWriter().use { it.write("D") }//F SI ES UN FICHERO
    var xcopyDir = processXcopy.waitFor()
    if (xcopyDir ==0){
        println(" el proceso xCopy con Directorios se ha ejecutado correctamenre")
        var resultXcopyDir = BufferedReader(InputStreamReader(processXcopyDir.inputStream))
        resultXcopyDir.readLines().forEach { println(it) }
        resultXcopyDir.close()
    }else{
        println(" el proceso xCopy con Directorios NO se ha ejecutado correctamenre \n \n" +
                " seguramente porque ya lo tienes creado de haber ejecutado este programa varias veces")
        var resultXcopyFail = BufferedReader(InputStreamReader(processXcopyDir.errorStream))
        resultXcopyFail.readLines().forEach { println(it) }
        resultXcopyFail.close()
    }


    println("\n" + "\n" + "Findstr es el comando equivalente a greep en windows")

    var textoABuscar = "version "
    val commandFind =
        "cmd /c findstr " + textoABuscar + System.getProperty("user.dir") + File.separator + "build.gradle.kts "
    val processFind = ProcessBuilder(commandFind.split(" ")).start()

    var okFindFirts = processFind.waitFor()
    if (okFindFirts==0){
        println("el proceso findStr finalizó correctamente")
        var resultFind = BufferedReader(InputStreamReader(processFind.inputStream))
        resultFind.readLines().forEach { println(it) }
        resultFind.close()
    }else{
        println("el proceso findStr NO  finalizó correctamente")
        var resultFindFail = BufferedReader(InputStreamReader(processFind.errorStream))
        resultFindFail.readLines().forEach { println(it) }
        resultFindFail.close()
    }
}

fun realizarConComandosDeLinuxEnWindows() {



        //para ejecutar en windows comandos de linux
        var comandInit = "wsl.exe"

        println("ir a un diresctorio: cd")

        val commandCD = "wsl.exe cd /mnt/"
        println(commandCD)
        val processCD = ProcessBuilder(commandCD.split(" ")).start()
        var okCD = processCD.waitFor()
        if (okCD == 0){
            println("el proceso cd se ha realizado correctamente")
            var readerCD = BufferedReader(InputStreamReader(processCD.inputStream))
            readerCD.lines().forEach { println(it) }
            readerCD.close()
        }else{
            println("el proceso cd NO se ha realizado correctamente")
            var readerCD = BufferedReader(InputStreamReader(processCD.errorStream))
            readerCD.lines().forEach { println(it) }
            readerCD.close()
        }



        println("\n" + "\n" + "  ejecutar un jar: java -jar para ello necesitamos un archivo.jar si no es así imprimimos el error")
        val commandJar = "wsl.exe java -jar MiArchivo.jar"
        val processJar = ProcessBuilder(commandJar.split(" ")).start()
        var okJar = processJar.waitFor()
        if (okJar == 0 ){
            println(" el proceso Jar se ha ejecutado con exito")
            val readerJAR = BufferedReader(InputStreamReader(processJar.inputStream))
            var line: String? = readerJAR.readLine()
            while (line != null) {
                println(line)
                line = readerJAR.readLine()
            }
            readerJAR.close()
        }else{
            println(" el proceso Jar NO se ha ejecutado con exito")
            val readerJAR = BufferedReader(InputStreamReader(processJar.errorStream))
            var line: String? = readerJAR.readLine()
            while (line != null) {
                println(line)
                line = readerJAR.readLine()
            }
            readerJAR.close()
        }



        println("\n" + "\n" + " listar directorios: ls + path")

        val commandDir = "wsl.exe ls /mnt/c"
        val processDir = ProcessBuilder(commandDir.split(" ")).start()
        println(commandDir)
        var okLs =  processDir.waitFor()
        if(okLs==0){
            println("El proceso ls ha finalizado correctamente")
            val resultDir = BufferedReader(InputStreamReader(processDir.inputStream))
            var lineDir: String? = resultDir.readLine()
            while (lineDir != null) {
                println(lineDir)
                lineDir = resultDir.readLine()
            }
            resultDir.close()
        }else{
            println("El proceso ls NO ha finalizado correctamente")
            val resultDir = BufferedReader(InputStreamReader(processDir.errorStream))
            var lineDir: String? = resultDir.readLine()
            while (lineDir != null) {
                println(lineDir)
                lineDir = resultDir.readLine()
            }
            resultDir.close()
        }




        println("\n" + "\n" + "cat ")
        val commandType = "wsl.exe cat /mnt/c/DumpStack.log.tmp"
        println(commandType)
        val processType = ProcessBuilder(commandType.split(" ")).start()
        var okCat = processType.waitFor()
        if (okCat == 0 ){
            println("proceso cat ha terminado correctamente")
            var resultType = BufferedReader(InputStreamReader(processType.inputStream))
            resultType.readLines().forEach { println(it) }
            resultType.close()
        }else{
            println("proceso cat NO ha terminado correctamente")
            var resultType = BufferedReader(InputStreamReader(processType.errorStream))
            resultType.readLines().forEach { println(it) }
            resultType.close()
        }


        println("\n" + "\n" + "cp ")

        val commandXcopy =
            "wsl.exe cp /mnt/c/DumpStack.log.tmp /mnt/c/DumpStack.log2.tmp"
        println(commandXcopy)
        val processXcopy = ProcessBuilder(commandXcopy.split(" ")).start()
        var okXcopy = processXcopy.waitFor()
        if(okXcopy==0){
            println(" el proceso cp ha finalizado correctamnete")
            var resultXcopy = BufferedReader(InputStreamReader(processXcopy.inputStream))
            resultXcopy.readLines().forEach { println(it) }
            resultXcopy.close()
        }else{
            println(" el proceso cp NO ha finalizado correctamnete")
            var resultXcopy = BufferedReader(InputStreamReader(processXcopy.errorStream))
            resultXcopy.readLines().forEach { println(it) }
            resultXcopy.close()
        }

        println("\n" + "\n" + "  greep ")

        var textoABuscar = "c "
        val commandFind =
            "wsl.exe grep " + textoABuscar + "/mnt/"
        val processFind = ProcessBuilder(commandFind.split(" ")).start()
        var okFinf = processFind.waitFor()
        if (okFinf == 0 ){
            println("el proceso greep ha terminado correctamente")
            var resultFind = BufferedReader(InputStreamReader(processFind.inputStream))
            resultFind.readLines().forEach { println(it) }
            resultFind.close()
        }else{

                println("el proceso greep NO ha terminado correctamente")
                var resultFind = BufferedReader(InputStreamReader(processFind.errorStream))
                resultFind.readLines().forEach { println(it) }
                resultFind.close()

        }


}
