package ch01_coroutines

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking<Unit> {
    val channel = Channel<String>(2)
    launch {
        channel.send("A1")
        channel.send("A2")
        channel.send("A3")
        channel.send("A4")
        channel.send("A5")
        log("A done")
    }

    launch {
        channel.send("B1")
        log("B done")
    }

    launch {
        channel.send("C1")
        channel.send("C2")
        log("c done")
    }

    launch {
        repeat(5) {
            log("receive start1")
            val x = channel.receive()
            log(x+"bbbb")
        }

        repeat(4) {
            log("receive start2")
            val x = channel.receive()
            log(x+"aaaaa")
        }
    }

    log("Last")
}

fun log(message: Any?) {
    println("[${Thread.currentThread().name}] $message")
}