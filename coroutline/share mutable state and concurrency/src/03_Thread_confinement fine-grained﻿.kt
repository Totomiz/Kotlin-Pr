import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.*

val counterContext= newSingleThreadContext("CounterThread")

//解决方式二，使用细粒度的限制
fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            // confine each increment to a single-threaded context
            withContext(counterContext) {
                counter++
            }
        }
    }
    println("Counter = $counter")
}