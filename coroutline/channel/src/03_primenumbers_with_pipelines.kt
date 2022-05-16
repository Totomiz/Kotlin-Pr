import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking


fun CoroutineScope.numbersFrom(start: Int) = produce<Int> {
    var x = start
    while (true) send(x++) // infinite stream of integers from start
}

fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce<Int> {
    for (x in numbers) if (x % prime != 0) send(x)
}


////pipelines是一种模式，其中一个协程正在生产一种无限多值得数据流，而另外一个协程或多个协程正在使用该流
//产生质数
fun main() =
    runBlocking {
        var cur = numbersFrom(2)

        //打印前10个数据
        repeat(10) {
            val prime = cur.receive()
            println("prime number = ${prime}")
            cur = filter(cur, prime)
        }
        //完成
        coroutineContext.cancelChildren()
    }
