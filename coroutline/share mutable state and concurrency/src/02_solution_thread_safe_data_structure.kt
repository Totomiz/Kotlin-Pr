import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis



val counterAto = AtomicInteger()

//适用于线程和Coroutines的一般解决方案是使用线程安全（又称同步，可线化或原子化）数据结构，
// 该数据结构为需要在共享状态上执行的相应操作提供了所有必要的同步。在一个简单的计数器的情况下，
// 我们可以使用具有原子增量操作的AtomicInteger类
fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            counterAto.incrementAndGet()
        }
    }
    println("Counter = $counterAto")
}