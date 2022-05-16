package ch01_coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout


var acquired = 0

class Resource {
    init {
        acquired++
    }

    fun close() {
        acquired--
    }
}

fun main() {
//    runBlocking {
//        repeat(100_000){
//            launch {
//                val resource= withTimeout(60){
//                    delay(50)
//                    Resource()
//                }
//                resource.close()
//            }
//        }
//    }
    println("acquired==${acquired}")

    repeat(100) {
        println("acquired==${acquired}-----${it}  start")
        runBlocking {
            repeat(100_000) {
                launch {
                    var resource: Resource? = null // Not acquired yet
                    try {
                        resource=withTimeout(60) {
                            delay(50)
                            Resource()
                        }
                    } finally {
                        resource?.close()
                    }

                }
            }
        }
        println("acquired==${acquired}-----${it}  end")
    }
}
