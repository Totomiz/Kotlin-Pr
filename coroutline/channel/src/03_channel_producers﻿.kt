import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking


fun CoroutineScope.produceSquares():ReceiveChannel<Int> = produce{
    for (x in 1..5){
        send(x*x)
    }

}

//生产消费者模式通常在并发中见到
//你可以将这样的生产者抽象为一个以通道为参数的函数，但这违背了函数必须返回结果的常识，produce()方法就是一个协程构建器
//consumeEach {}可以是消费端for的替代
fun main(){
    runBlocking {
        val squares=produceSquares()
        squares.consumeEach {
            println("Consume ${it}")
        }
    }
}