fun log(message: Any?) {
    println("[${Thread.currentThread().name}] $message")
}