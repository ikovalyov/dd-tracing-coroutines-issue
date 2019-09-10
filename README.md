# dd-tracing-coroutines-issue
Sample project to demonstrate issues with coroutines when using dd-java-agent 0.32.0

# Issue description

As you can see, parent span is lost in the coroutine. Same issue happens with automated instrumentario (which is discovered on our datadog APM dashboard). Right now only spans created within the same thread are being displaying.

Also if we create spans in the coroutine we suggest it to be a child of the parent span. But span is not set yet (null in the log). So I assume they are created as parent spans and either wrongly reported as parent ones or ignored as expired ones.

# Versions
Kotlin version is `1.3.50`

DD agent version is `0.32.0~e158f110`

# Start the application
`./gradlew run`

# Sample output

```
./gradlew run

> Task :run
[dd.tracing.agent - 2019-09-10 09:12:44:024 +0200] [main] INFO datadog.trace.agent.ot.DDTraceOTInfo - dd-trace - version: 0.32.0~e158f110
[dd.tracing.agent - 2019-09-10 09:12:44:153 +0200] [main] INFO datadog.trace.agent.ot.DDTracer - New instance: DDTracer-1317b708{ serviceName=unnamed-java-app, writer=DDAgentWriter { api=DDApi { tracesUrl=http://localhost:8126/v0.3/traces } }, sampler=datadog.trace.agent.common.sampling.RateByServiceSampler@6438a7fe, defaultSpanTags={}}
[dd.tracing.agent - 2019-09-10 09:12:44:163 +0200] [main] INFO datadog.trace.agent.tooling.VersionLogger - dd-trace-ot - version: 0.32.0~e158f110
[dd.tracing.agent - 2019-09-10 09:12:44:164 +0200] [main] INFO datadog.trace.agent.tooling.VersionLogger - dd-trace-api - version: 0.32.0~e158f110
[dd.tracing.agent - 2019-09-10 09:12:44:164 +0200] [main] INFO datadog.trace.agent.tooling.VersionLogger - dd-java-agent - version: 0.32.0~e158f110
[dd.tracing.agent - 2019-09-10 09:12:44:178 +0200] [main] INFO datadog.trace.agent.jmxfetch.JMXFetch - JMXFetch config: null [] [] [] null null {runtime-id=1ce98a33-41f9-4c80-9361-b4fdf1ed8c17, language=jvm, service=unnamed-java-app} statsd:localhost:8125 System.err INFO
2019-09-10 09:12:45.458  INFO 10483 [           main] Main$main$1                              : main DDSpan [ t_id=4655828248416555931, s_id=4116955359741915433, p_id=0] trace=unnamed-java-app/main/main metrics={_sampling_priority_v1=1} tags={language=jvm, runtime-id=1ce98a33-41f9-4c80-9361-b4fdf1ed8c17, thread.id=1, thread.name=main}, duration_ns=0
2019-09-10 09:12:45.487  INFO 10483 [atcher-worker-1] Main                                     : DefaultDispatcher-worker-1 null
2019-09-10 09:12:45.495  INFO 10483 [atcher-worker-2] Main                                     : DefaultDispatcher-worker-2 null
2019-09-10 09:12:45.497  INFO 10483 [atcher-worker-3] Main                                     : DefaultDispatcher-worker-3 null
2019-09-10 09:12:45.501  INFO 10483 [atcher-worker-6] Main                                     : DefaultDispatcher-worker-6 null
2019-09-10 09:12:45.501  INFO 10483 [atcher-worker-7] Main                                     : DefaultDispatcher-worker-7 null
2019-09-10 09:12:45.502  INFO 10483 [atcher-worker-4] Main                                     : DefaultDispatcher-worker-4 null
2019-09-10 09:12:45.504  INFO 10483 [atcher-worker-5] Main                                     : DefaultDispatcher-worker-5 null
2019-09-10 09:12:45.506  INFO 10483 [atcher-worker-9] Main                                     : DefaultDispatcher-worker-9 null
2019-09-10 09:12:45.505  INFO 10483 [tcher-worker-10] Main                                     : DefaultDispatcher-worker-10 null
2019-09-10 09:12:45.505  INFO 10483 [atcher-worker-8] Main                                     : DefaultDispatcher-worker-8 null
[dd.tracing.agent - 2019-09-10 09:12:46:537 +0200] [Thread-1] INFO datadog.trace.agent.common.writer.DDAgentWriter - Flushing any remaining traces.

```