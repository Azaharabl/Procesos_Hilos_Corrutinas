
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


fun main(args: Array<String>) {
    println("Hello Procesos World!")

    println("\n\n obtenemos nuestro sistema operativo")
    val sistemaOperativo = System.getProperty("os.name") ?: "desconocido"
    println(sistemaOperativo)

    if (sistemaOperativo.contains("Windows", true)) {
        //hacemos comandos de windows
        var comandInit = "cmd.exe"
        var c = "/c"

        println("ir a un diresctorio: cd")

        val commandCD = "cmd /c cd C:\\Users\\MiUsuario\\Documents"
        val processCD = ProcessBuilder(commandCD.split(" ")).start()
        processCD.waitFor()
        var readerCD = BufferedReader(InputStreamReader(processCD.getInputStream()))
        readerCD.lines().forEach { println(it) }
        readerCD.close()


        println(
            "\n" +
                    "\n" +
                    "  ejecutar un jar: java -jar para ello necesitamos un archivo.jar si no es así imprimimos el error"
        )
        val commandJar = "cmd /c java -jar MiArchivo.jar"
        val processJar = ProcessBuilder(commandJar.split(" ")).start()
        processJar.waitFor()

        val readerJAR = BufferedReader(InputStreamReader(processJar.inputStream))
        val errorReaderJAR = BufferedReader(InputStreamReader(processJar.errorStream))

        var line: String? = readerJAR.readLine()
        while (line != null) {
            println(line)
            line = readerJAR.readLine()
        }

        var errorLine: String? = errorReaderJAR.readLine()
        while (errorLine != null) {
            println(errorLine)
            errorLine = errorReaderJAR.readLine()
        }

        readerJAR.close()
        errorReaderJAR.close()


        println(
            "\n" +
                    "\n" +
                    " listar directorios: dir /b + path"
        )

        val commandDir = "cmd /c dir /b " + System.getProperty("user.dir")
        val processDir = ProcessBuilder(commandDir.split(" ")).start()
        println(commandDir)
        processDir.waitFor()

        val resultDir = BufferedReader(InputStreamReader(processDir.inputStream))
        var lineDir: String? = resultDir.readLine()
        while (lineDir != null) {
            println(lineDir)
            lineDir = resultDir.readLine()
        }

        resultDir.close()

        println(
            "\n" +
                    "\n" +
                    "Type es el comando equivalente a cat en windows "
        )
        val commandType = "cmd /c type " + System.getProperty("user.dir") + File.separator + "build.gradle.kts"
        println(commandType)
        val processType = ProcessBuilder(commandType.split(" ")).start()
        processType.waitFor()
        var resultType = BufferedReader(InputStreamReader(processType.inputStream))
        resultType.readLines().forEach { println(it) }


        println(
            "\n" +
                    "\n" + "Xcopy equivalente a cp para windows con un fichero"
        )

        val commandXcopy =
            "cmd /c xcopy " + System.getProperty("user.dir") + File.separator + "build.gradle.kts " + System.getProperty(
                "user.dir"
            ) + File.separator + "build.gradlecopiado.kts"
        println(commandXcopy)
        val processXcopy = ProcessBuilder(commandXcopy.split(" ")).start()
        var entradaDeXcopy = processXcopy.outputStream.bufferedWriter().use { it.write("F") }//F SI ES UN FICHERO
        processXcopy.waitFor()
        var resultXcopy = BufferedReader(InputStreamReader(processXcopy.inputStream))
        resultXcopy.readLines().forEach { println(it) }
        resultXcopy.close()

        println(
            "\n" +
                    "\n" + "Xcopy equivalente a cp para windows con un directorio"
        )

        val commandXcopyDir =
            "cmd /c xcopy " + System.getProperty("user.dir") + File.separator + ".idea " + System.getProperty("user.dir") + File.separator + ".ideaCopy"
        println(commandXcopyDir)
        val processXcopyDir = ProcessBuilder(commandXcopyDir.split(" ")).start()
        var entradaDeXcopyDir = processXcopyDir.outputStream.bufferedWriter().use { it.write("D") }//F SI ES UN FICHERO
        processXcopy.waitFor()
        var resultXcopyDir = BufferedReader(InputStreamReader(processXcopyDir.inputStream))
        resultXcopyDir.readLines().forEach { println(it) }
        resultXcopyDir.close()


        println(
            "\n" +
                    "\n" + "Findstr es el comando equivalente a greep en windows"
        )

        var textoABuscar = "version "
        val commandFind =
            "cmd /c findstr " + textoABuscar + System.getProperty("user.dir") + File.separator + "build.gradle.kts "
        val processFind = ProcessBuilder(commandFind.split(" ")).start()
        var resultFind = BufferedReader(InputStreamReader(processFind.inputStream))
        resultFind.readLines().forEach { println(it) }
        processFind.waitFor()
        resultFind.close()

    }
    if (sistemaOperativo.contains("Linux", true)) {
        var comandInit = "bash"
        var c = "-c"

        println("ir a un diresctorio: cd")

        val commandCD = "bash -c cd C:\\Users\\MiUsuario\\Documents"
        val processCD = ProcessBuilder(commandCD.split(" ")).start()
        processCD.waitFor()
        var readerCD = BufferedReader(InputStreamReader(processCD.getInputStream()))
        readerCD.lines().forEach { println(it) }
        readerCD.close()


        println(
            "\n" +
                    "\n" +
                    "  ejecutar un jar: java -jar para ello necesitamos un archivo.jar si no es así imprimimos el error"
        )
        val commandJar = "bash -c  java -jar MiArchivo.jar"
        val processJar = ProcessBuilder(commandJar.split(" ")).start()
        processJar.waitFor()

        val readerJAR = BufferedReader(InputStreamReader(processJar.inputStream))
        val errorReaderJAR = BufferedReader(InputStreamReader(processJar.errorStream))

        var line: String? = readerJAR.readLine()
        while (line != null) {
            println(line)
            line = readerJAR.readLine()
        }

        var errorLine: String? = errorReaderJAR.readLine()
        while (errorLine != null) {
            println(errorLine)
            errorLine = errorReaderJAR.readLine()
        }

        readerJAR.close()
        errorReaderJAR.close()


        println(
            "\n" +
                    "\n" +
                    " listar directorios: ls + path"
        )

        val commandDir = "bash -c  ls " + System.getProperty("user.dir")
        val processDir = ProcessBuilder(commandDir.split(" ")).start()
        println(commandDir)
        processDir.waitFor()

        val resultDir = BufferedReader(InputStreamReader(processDir.inputStream))
        var lineDir: String? = resultDir.readLine()
        while (lineDir != null) {
            println(lineDir)
            lineDir = resultDir.readLine()
        }

        resultDir.close()

        println(
            "\n" +
                    "\n" +
                    "cat "
        )
        val commandType = "bash -c cat " + System.getProperty("user.dir") + File.separator + "build.gradle.kts"
        println(commandType)
        val processType = ProcessBuilder(commandType.split(" ")).start()
        processType.waitFor()
        var resultType = BufferedReader(InputStreamReader(processType.inputStream))
        resultType.readLines().forEach { println(it) }


        println(
            "\n" +
                    "\n" + "cp "
        )

        val commandXcopy =
            "bash -c cp" + System.getProperty("user.dir") + File.separator + "build.gradle.kts " + System.getProperty("user.dir") + File.separator + "build.gradlecopiado.kts"
        println(commandXcopy)
        val processXcopy = ProcessBuilder(commandXcopy.split(" ")).start()
        processXcopy.waitFor()
        var resultXcopy = BufferedReader(InputStreamReader(processXcopy.inputStream))
        resultXcopy.readLines().forEach { println(it) }
        resultXcopy.close()

        println(
            "\n" +
                    "\n" + "cp "
        )

        val commandXcopyDir =
            "bash -c cp " + System.getProperty("user.dir") + File.separator + ".idea " + System.getProperty("user.dir") + File.separator + ".ideaCopy"
        println(commandXcopyDir)
        val processXcopyDir = ProcessBuilder(commandXcopyDir.split(" ")).start()
        processXcopy.waitFor()
        var resultXcopyDir = BufferedReader(InputStreamReader(processXcopyDir.inputStream))
        resultXcopyDir.readLines().forEach { println(it) }
        resultXcopyDir.close()


        println(
            "\n" +
                    "\n" + "  greep "
        )

        var textoABuscar = "version "
        val commandFind =
            "bash -c greep " + textoABuscar + System.getProperty("user.dir") + File.separator + "build.gradle.kts "
        val processFind = ProcessBuilder(commandFind.split(" ")).start()
        var resultFind = BufferedReader(InputStreamReader(processFind.inputStream))
        resultFind.readLines().forEach { println(it) }
        processFind.waitFor()
        resultFind.close()


    }

}
