import kotlinx.coroutines.*


//粗粒度的限制，使用一个context
//实际上，线程限制是在大块中进行的，例如大型国家更新业务逻辑仅限于单个线程。
// 下面的示例这样做，从单线程上下文中运行每个coroutine。
fun main() = runBlocking {
    // confine everything to a single-threaded context
    withContext(counterContext) {
        massiveRun {
            counter++
        }
    }
    println("Counter = $counter")
}