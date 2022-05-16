package ch01_coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis


fun main() {
    val star = System.currentTimeMillis()
    runBlocking {

        val time = measureTimeMillis {
            val one = computOne()
            val two = computTwo()
            println("total done = ${one + two}")
        }
        println("complate in ${time} ms")

        val time2 = measureTimeMillis {
            val one = launch {
                println("start one")
                delay(1000)
                11
            }
            val two = launch {
                println("start two")
                delay(1500)
                155
            }
            one.join()
            two.join()
            println("total done = ")
        }
        println("complate in ${time2} ms")

        val time3 = measureTimeMillis {
            val one = async {
                println("start one")
                delay(1000)
                11
            }
            val two = async {
                println("start two")
                delay(1500)
                155
            }
            val await1 = one.await()
            val await2 = two.await()
            println("total done = ${await1 + await2}")
        }
        println("complate3 in ${time3} ms")

        //Lazy start
        val time4 = measureTimeMillis {
            val one = async(start = CoroutineStart.LAZY) {
                println("start4 one")
                delay(1000)
                11
            }
            val two = async(start = CoroutineStart.LAZY) {
                println("start4 two")
                delay(1500)
                155
            }
            one.start()
            two.start()
            val await1 = one.await()
            val await2 = two.await()
            println("total4 done = ${await1 + await2}")
        }
        println("complate4 in ${time4} ms")
    }

}

suspend fun computOne(): Int {
    delay(1000)
    return 11
}

suspend fun computTwo(): Int {
    delay(1200)
    return 22
}