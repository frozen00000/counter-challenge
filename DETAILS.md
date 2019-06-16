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
    Latency   838.16us  137.18us   6.00ms   95.11%
    Req/Sec     9.59k   633.62    10.18k    92.74%
  Latency Distribution
     50%  807.00us
     75%  834.00us
     90%    0.87ms
     99%    1.43ms
  1156482 requests in 10.10s, 41.91MB read
Requests/sec: 114506.91
Transfer/sec:      4.15MB
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



