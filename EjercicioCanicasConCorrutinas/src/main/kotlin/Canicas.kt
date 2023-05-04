
import java.util.UUID
import kotlin.random.Random

class Canicas () {
    val uuid: String = UUID.randomUUID().toString()
    val color: String = obtenerColor()

    private fun obtenerColor(): String {
        val opciones = listOf("verde", "azul", "roja", "blanca")
        return opciones[Random.nextInt(opciones.size)]
    }

    override fun toString(): String {
        return "('$color')"
    }

}