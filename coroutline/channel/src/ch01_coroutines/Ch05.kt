package ch01_coroutines

import kotlinx.coroutines.newSingleThreadContext

fun main() {
    newSingleThreadContext("Main")
}