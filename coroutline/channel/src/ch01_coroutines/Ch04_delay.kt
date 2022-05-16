package ch01_coroutines

import kotlinx.coroutines.*


fun main() = runBlocking {
//    testCancel1()
//    testCancel2()
//    testCancelable3()
    testCancelableWithFinally4()
    testCancelableFinallyWithNonCancellable()
    testFuncTimeout()
}

fun testCancel1() = runBlocking {
    println("<top>.testCancel1")
    val job = launch(Dispatchers.Unconfined) {
        withContext(Dispatchers.IO) {
            repeat(1000) {
                println("job im sleeping $it ....")
                delay(500)
            }
        }
    }
    delay(1300)
    println("main im waiting")

    job.cancel()
    job.join()//comment this line ,but also the same output
//    job.cancelAndJoin()
    println("Main:Now im quit")
}


suspend fun time() = coroutineScope {
    println("<top>.time")
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {

        var nextPrintTime = startTime
        var i = 0
        while (i < 500) { // computation loop, just wastes CPU
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }

    }
}

//2.无法取消的计算
fun testCancel2() {
    println("<top>.testCancel2")
//    runBlocking(Dispatchers.Default) {
    runBlocking {
        println("dispather= ${this.coroutineContext} ${Thread.currentThread()}")
        val startTime = System.currentTimeMillis()
//        val job = launch(Dispatchers.Default) {
        val job = launch {
            println("dispathe launch=${this.coroutineContext} ${Thread.currentThread()}")
            var nextPrintTime = startTime
            var i = 0
            while (i < 10) { // computation loop, just wastes CPU
                // print a message twice a second
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ... ${Thread.currentThread()}")
                    nextPrintTime += 500L
                    if (i == 5) {
                        yield()
                    }
                }
            }
        }
        val job2 = launch(Dispatchers.IO) {
            println("dispathe job2 launch2=${this.coroutineContext} ${Thread.currentThread()}")
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }
}

//3.使代码可以取消 ,方式一 使用isActive ,方式2
fun testCancelable3() {
    println("<top>.testCancelable3")
    runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.IO) {
            var nextPrintTime = startTime
            var i = 0
            //方式一 使用isActive
            while (isActive) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
            //方式二 使用yield()
//            while (i < 10) { // computation loop, just wastes CPU
//                // print a message twice a second
//                if (System.currentTimeMillis() >= nextPrintTime) {
//                    println("job: I'm sleeping ${i++} ...")
//                    nextPrintTime += 500L
//                }
//                yield()
//            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }
}

//finally
fun testCancelableWithFinally4() {
    println("<top>.testCancelableWithFinally4")
    runBlocking {

        val startTime = System.currentTimeMillis()

        val job = launch(Dispatchers.IO) {
            try {
                var nextPrintTime = startTime
                var i = 0
                while (isActive) {
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        println("job: I'm sleeping ${i++} ...")
                        nextPrintTime += 500L
                    }
                }
            } finally {
                println("job: I'm running finally")
            }

        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }
}

fun testCancelableFinallyWithNonCancellable() {
    println("<top>.testCancelableFinallyWithNonCancellable")
    runBlocking {

        val startTime = System.currentTimeMillis()

        val job = launch(Dispatchers.Default) {
            try {
                var nextPrintTime = startTime
                var i = 0
                while (isActive) {
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        println("job: I'm sleeping ${i++} ...")
                        nextPrintTime += 500L
                    }
                }
            } finally {
                withContext(NonCancellable) {
                    println("job: I'm running finally")
                }
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }
}

fun testFuncTimeout() {
    println("<top>.testFuncTimeout")
    runBlocking {

        val result = withTimeoutOrNull(1300L) {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
        println("Result is $result")
    }

}

fun testAsynchronous() {

}
