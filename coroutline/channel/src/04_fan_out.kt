import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//周期性产生数据
fun CoroutineScope.produceNumbersDelay() = produce<Int> {
    var x = 1 // start from 1
    while (true) {
        send(x++) // produce next
        delay(100) // wait 0.1s
    }
}

//然后我们可以有几个协程处理器
fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
    for (msg in channel) {
        println("Processor #$id received $msg")
    }
}

// 有些时候多个协程可能同一从同个channel接受,在他们之间分配工作
fun main() {
   runBlocking {
       val producer = produceNumbers()
       repeat(5) {
           launch {
               launchProcessor(it, producer)
           }
       }
       delay(950)
       producer.cancel() // cancel producer coroutine and thus kill them all
   }
}