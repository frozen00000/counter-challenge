### Build

`mvn clean package`

`native-image --no-fallback --allow-incomplete-classpath -jar target/counter-challenge-full.jar -H:ReflectionConfigurationResources=netty_reflection_config.json -H:Name=netty-svm-http-server --initialize-at-build-time=io.netty,netty.svm --initialize-at-run-time=io.netty.handler.codec.http.HttpObjectEncoder,io.netty.handler.codec.http2.Http2CodecUtil,io.netty.handler.codec.http2.DefaultHttp2FrameWriter,io.netty.handler.codec.http.websocketx.WebSocket00FrameEncoder -Dio.netty.noUnsafe=true`

### Run

`./netty-svm-http-server`

Sie of executable - 7.9 mb

### Notes

This implementation is based on Netty and GraalVM. See more https://medium.com/graalvm/instant-netty-startup-using-graalvm-native-image-generation-ed6f14ff7692