import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// type your solution here

fun main(){
    //创建通道，发送一系列值
    //close()可用来关闭,for循环用
    val channel=Channel<String>()
    runBlocking {

        launch {
            for (x in 1..5){
                channel.send("value=${x*x}")
                channel.close()
            }
            log("Value, send done")
        }
//        for(y in channel){
//            log("received value=${y}")
//        }
        repeat(5) {
            log("received ${channel.receive()}")
        }
        log("done")
    }
}