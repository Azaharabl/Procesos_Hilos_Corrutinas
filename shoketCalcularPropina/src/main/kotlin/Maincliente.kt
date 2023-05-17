import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.OutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread


fun main(args: Array<String>) {

    var direccion: InetAddress =
        InetAddress.getLocalHost()   //Paso uno -> obtener la dirección, sino seria la ip a conectar
    var puerto: Int = 5000;      //puerto de el shoker para conectar
    var servidor: Socket = Socket(direccion, puerto)     //a crear con la dirección y el puerto


    // paso tres -> pasos de mensajes de lectura y escritura

    println(
        "Bienvenido a elcalculador de propinas en remoto!" +
                "\n, a continuacion escrive tu cuenta y te devolveremos la propina."
    )

    println("dime tu cuenta de la que quieras sacar el iva")
    var numero = readLine()?.toDoubleOrNull()
    println(numero)

    try {
        println("Gracias, enviamos tu peticion en remoto...")
        val salida = DataOutputStream(servidor.getOutputStream())
        salida.writeUTF(numero.toString() + "\n")
        println("enviado")
    } catch (e: Exception) {
        println("no has metifdo los datos correctos")
    }

    try {
        println("Recojemos el resultado de peticion en remoto...")
        val dato = DataInputStream(servidor.getInputStream()).readUTF()
        if (dato == null){
            println("no has metifdo los datos correctos")
        }else{
            println("El la propina de $numero es $dato")
        }

    } catch (e: Exception) {
        println("no has metifdo los datos correctos")
    }
}

