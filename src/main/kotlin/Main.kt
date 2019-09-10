import datadog.opentracing.DDSpan
import io.opentracing.util.GlobalTracer
import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Main {
    private val logger: Logger = LoggerFactory.getLogger(Main.javaClass)
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val tracer = GlobalTracer.get()
        tracer.buildSpan("main").startActive(true)
        val span = tracer.activeSpan()
        logger.info("${Thread.currentThread().name} $span")
        coroutineScope {
            repeat(10) {
                launch(Dispatchers.IO) {
                    waitFor(1000)
                }
            }
        }
    }

    private suspend fun waitFor(millis: Long) {
        val tracer = GlobalTracer.get()
        val span = tracer.activeSpan()
        logger.info("${Thread.currentThread().name} $span")
        delay(millis)
    }
}