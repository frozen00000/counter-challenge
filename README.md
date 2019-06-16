# counter-challenge

Program Requirements:
- A single endpoint is exposed at `/`
- Each request should increase shared for whole application counter (think about thread safe implementation)

Notes:
- Benchmarking with https://github.com/wg/wrk
- Example command: `wrk --latency -t12 -c100 -d10s http://localhost:8080`

The goal is to achieve best throughput (as many request per second as you can).
