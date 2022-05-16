import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


// 到目前为止显示的频道没有缓冲区。当发送者和接收器相互见面时，未封闭的通道会传输元素（又名Rendezvous）。
// 如果首先调用发送，则将其暂停直到调用接收，如果首先调用接收，则将其暂停，直到调用发送为止。
// Channel（）工厂功能和生产构建器都采用可选的容量参数来指定缓冲区大小。缓冲区允许发件人在悬挂之前发送多个元素，
// 类似于具有指定容量的阻止类似，该元素在缓冲区已满时会阻止。
fun main() {
    runBlocking {
        val channel = Channel<Int>(4) // create buffered channel
        val sender = launch { // launch sender coroutine
            repeat(10) {
                println("Sending $it") // print before sending each element
                channel.send(it) // will suspend when buffer is full
            }
        }
        // don't receive anything... just wait....
        delay(1000)
        sender.cancel() // cancel sender coroutine
    }
}