# Details for pure Natty implementation

### Profiler statistics

![alt profiler](https://github.com/frozen00000/counter-challenge/blob/pure-netty/profiler.png)

`sun.nio.ch.FileDispatcherImpl.write0` - 32.5%

`sun.nio.ch.FileDispatcherImpl.read0` - 10.3%

`sun.nio.ch.KQueue.poll` - 34.4%

### WRK results

```$xslt
wrk --latency -t12 -c100 -d10s http://localhost:8080/
Running 10s test @ http://localhost:8080/
  12 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     0.88ms   83.13us   8.53ms   96.71%
    Req/Sec     9.09k   395.91     9.59k    66.17%
  Latency Distribution
     50%    0.86ms
     75%    0.92ms
     90%    0.94ms
     99%    1.07ms
  1095751 requests in 10.10s, 39.71MB read
Requests/sec: 108492.43
Transfer/sec:      3.93MB
```

### Hardware

Processor: 2.2 GHz Intel Core i7

### Software

Java:
```$xslt
openjdk version "11.0.2" 2019-01-15
OpenJDK Runtime Environment 18.9 (build 11.0.2+9)
OpenJDK 64-Bit Server VM 18.9 (build 11.0.2+9, mixed mode)
```

OS: MacOS 10.14.5



