import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// type your solution here

fun main() {
    //创建通道，发送一系列值
    val channel = Channel<String>()
    runBlocking {

        launch {
            channel.send("A1")
            channel.send("A2")
            channel.send("A3")
            channel.send("A4")
            channel.send("A5")
            log("A, send done")
        }
        repeat(5) {
            log("received ${channel.receive()}")
        }
        log("done")
    }
}