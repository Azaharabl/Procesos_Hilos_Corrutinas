import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread


fun main(args: Array<String>) {
    println("Hello Serber soket!")

    var  servidor : ServerSocket;
    var cliente: Socket ;
    var  puerto = 5000;
    var peticionesListas= 0

    servidor =  ServerSocket(puerto);
    System.out.println("Servidor arrancado...");

        try {
            while(true){ //no paramos el serbidor
                System.out.println("Esperando...")      //me quedo a la escucha
                cliente = servidor.accept();            //aceptamos el servidor cliente
                peticionesListas++
                mandarAlHilo(cliente)
                System.out.println("Peticion numero $peticionesListas mandado al hilo para procesar...")
            }
            System.out.println("Servidor finalizado...");
            servidor.close();
        } catch (e: Exception ) {
            e.printStackTrace();
        }





}
fun mandarAlHilo(cliente: Socket) {
    thread {    //le dejamos a un hilo
        val dato = DataInputStream(cliente.getInputStream()).readUTF() //todo aqui falla
        var respuesta : String = ""
        try {
            println("ha llegado: $dato ")
            var numero =  dato.toDoubleOrNull();
            println("el resultado es ${respuesta.toString()}")
            var calculoIva = numero?.times(0.21)
            respuesta = calculoIva.toString()
            println("mandando el resultado $respuesta")

        }catch (e:Exception){
            respuesta = "Error:  numero mandado no es correcto "
        }
        DataOutputStream(cliente.getOutputStream()).writeUTF(respuesta+"\n");
        println("enviado al cliente")
    }
}