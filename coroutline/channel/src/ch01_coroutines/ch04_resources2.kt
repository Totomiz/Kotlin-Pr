package ch01_coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout




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

    repeat(100){
        runBlocking {
            repeat(100_000){
                launch {
                    val resource= withTimeout(60){
                        delay(50)

                        Resource()
                    }
                    resource.close()
                }
//                println("aaaaa${it}")

            }
        }
        println("acquired==${acquired}")
    }
}
