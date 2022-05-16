package ch01_coroutines

import kotlinx.coroutines.*


fun main(args: Array<String>) {
    println("Start")
    Thread.sleep(5000)

    println(Thread.currentThread())
    runBlocking {
        repeat(100){
            launch {
                delay(2000)
                println("${Thread.currentThread()}{world}${it}")
                launch(Dispatchers.Default) {
                    dowork(it.toLong())
                }
            }
        }
        println("Hello")
    }
    println("End")
}

suspend fun dowork(delayTime:Long)= coroutineScope{
//    delay(delayTime)
    println("delay result${delayTime}")
}