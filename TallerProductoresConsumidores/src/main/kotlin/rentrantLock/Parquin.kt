package rentrantLock

import Coche
import java.util.concurrent.locks.ReentrantLock

class Parquin(var aparcamientos: Int = 200) {

    private var bufer = ArrayList<Coche>()
    private val lock = ReentrantLock()
    private val esperarParaMeter = lock.newCondition()
    private val esperarParaSacar = lock.newCondition()

    fun putEnParquin(coche: Coche) {
        lock.lock()
        try {
            while (bufer.size >= aparcamientos) {
                esperarParaMeter.await()
            }
            bufer.add(coche)
            esperarParaSacar.signal()
        } finally {
            lock.unlock()
        }
    }

    fun getDeParquin(): Coche {
        lock.lock()
        try {
            while (bufer.size == 0) {
                esperarParaSacar.await()
            }
            var value = bufer.removeAt(bufer.size - 1)  //es una cola
            esperarParaMeter.signal()
            return value
        } finally {
            lock.unlock()
        }
    }

}




