import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking


//构造一个产生无限数据的流
fun CoroutineScope.produceNumbers() = produce{
    var x=1
    while (true){
        x++
        send(x)
//        log("produce number ${x++}")
    }
}

//从一个channel通道作为输入产生另一个通道
fun CoroutineScope.square(number:ReceiveChannel<Int>):ReceiveChannel<Int> = produce{
    for (x in number){
        log("send square 1 value =${x}")
        send(x*x)
    }
}

fun CoroutineScope.squaretwo(number:ReceiveChannel<Int>):ReceiveChannel<Int> = produce{
    for (x in number){
        log("send square 2 value =${x}")
        send(x*x*x)
    }
}

//pipelines是一种模式，其中一个协程正在生产一种无限多值得数据流，而另外一个协程或多个协程正在使用该流
fun main(){
    runBlocking {
        val source=produceNumbers()
        val squares=square(source)
//        val squares2=squaretwo(source)
        //打印前5个数据
        repeat(5){
            log("squares=${squares.receive()}")
//            log("squaresTwo=${squares2.receive()}")
        }
        //完成
        coroutineContext.cancelChildren()
    }
}