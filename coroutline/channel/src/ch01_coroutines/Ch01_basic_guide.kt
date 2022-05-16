import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    println("Start")
    runBlocking {
        launch {
            delay(2000)
            println("${Thread.currentThread()}{world}")
        }
        println("Hello")
    }
    println("end original")

    runBlocking {
        launch {
            dowork1(3000)
        }
        println("Hello ")
    }

    runBlocking {
        dowork2(2000)
    }


    runBlocking {
        dowork3()
        println("done")
    }

    dowork4()
    work5()
}

//1.提取函数重构
suspend fun dowork1(delayTime: Long) {
    markStep(1)
    delay(delayTime)
    println("delay world")
}

//2.使用范围构建起CoroutineScope

suspend fun dowork2(delayTime: Long) = coroutineScope {
    markStep(2)
    launch {
        delay(delayTime)
        println("world 2")
    }
    println("hello again")
}

//3.范围构建器和并发，可以在任何悬挂函数中使用协程构建器来执行多个并发操作，让我们在work中启用两个并发协程
suspend fun dowork3() = coroutineScope {
    markStep(3)
    launch {
        delay(2000)
        println("world2")
    }

    launch {
        delay(1000)
        println("world1")
    }

    for (i in 1..10) {

        launch {
            val i1 = i * 1000L
            delay(i1)
            println("world ${i}")
        }
    }

    println("hello")
    // output hello world1 world2,因为块内同时执行
}

//4.明确的工作
fun dowork4() {
    markStep(4)
    runBlocking {
        val job = launch {
            delay(1000)
            println("world")
        }
        println("hello ")
        job.join()
        println("done")
    }
}

//5.协程是轻量级的，
//协程比 JVM 线程占用更少的资源。在使用线程时耗尽 JVM 可用内存的代码可以使用协程表示，而不会达到资源限制。例如，以下代码启动 100000 个不同的协程，每个协程等待 5 秒，然后打印一个句点 ('.')，同时消耗很少的内存：
fun work5() {
    markStep(5)
    runBlocking {

        repeat(100_000) { // launch a lot of coroutines
            launch {
                delay(5000L)
                print(".")
            }
        }
    }
    // 如果使用线程编写相同的程序，可能消耗过多的内存
}

fun markStep(step:Int){
    println("start->${step} ".plus("-".repeat(100)))
}
