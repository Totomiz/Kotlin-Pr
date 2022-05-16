package ch01_coroutines

import kotlinx.coroutines.*

fun main() {

    runBlocking{
        launch {
            println("bbb")
        }
        println("aaaaa")
    }


}

suspend fun CoroutFun():Int = coroutineScope {
    0
}

fun work1() {
    runBlocking {
        val deferred: Deferred<Int> = async {
            loadData()
        }
        println("waiting...")
        println(deferred.await())
    }
}

fun launb(name: String = "A", finc: String.() -> Unit) {
    println(name)
    finc(name)
}

fun launA(name: String = "A", finc: (String) -> Unit) {
    println(name)
    finc(name)
}

//1.协程通道简介
//协程为我们提供了异步和非阻塞行为的所有好处，但又不缺乏可读性。我们将看到如何在不阻塞底层线程和不使用回调的情况下使用协程来执行网络请求。
suspend fun loadData(): Int {
    println("loading...")
    delay(1000L)
    println("loaded!")
    return 42
}

//1.阻塞的请求，假设