import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce

//周期性产生数据
suspend fun sendString(channel: SendChannel<String>, s: String, time: Long) {
    while (true) {
        delay(time)
        channel.send(s)
    }
}


// 有些时候多个协程可能发送到同一个通道
fun main() {
   runBlocking {
       val channel = Channel<String>()
       launch { sendString(channel, "foo", 200L) }
       launch { sendString(channel, "BAR!", 500L) }
       repeat(6) { // receive first six
           println(channel.receive())
       }
       coroutineContext.cancelChildren() // cancel all children to let main finish
   }
}