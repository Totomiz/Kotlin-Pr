import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

val mux= Mutex()

//互斥方式
//对该问题的相互排除解决方案是通过从未同时执行的关键部分保护共享状态的所有修改。
// 在一个封锁世界中，您通常会使用同步或重新进入。 Coroutine的替代品称为Mutex。
// 它具有锁定和解锁功能来界定关键部分。关键区别在于mutex.lock（）是悬浮函数。
// 它不会阻止线程。还有withlLock扩展函数，可以方便地表示mutex.lock（);try{...}finally{mutex.unlock（）}模式：

//这个例子中的锁定是细粒度的，所以它付出了代价。但是，对于某些绝对必须定期修改某些共享状态但没有限制该状态的自然线程的情况，这是一个不错的选择。
fun main(){
    runBlocking {
        try {
            withContext(Dispatchers.Default){
                massiveRun {
                    mux.withLock {
                        counter++
                    }
                }
            }
            println("Counter = $counter")
        }finally {
//            mux.unlock()
        }

    }
}