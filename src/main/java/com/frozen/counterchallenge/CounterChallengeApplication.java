package com.frozen.counterchallenge;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.atomic.LongAccumulator;

public class CounterChallengeApplication {

	public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(eventLoopGroup)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<>() {
						@Override
						protected void initChannel(Channel channel) throws Exception {
							ChannelPipeline pipeline = channel.pipeline();
							pipeline.addLast(new HttpServerCodec());
							pipeline.addLast(new HttpServerHandler());
						}
					})
                    .channel(NioServerSocketChannel.class);

            Channel ch = bootstrap.bind(8080).sync().channel();
            ch.closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
	}

	static class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

		private final LongAccumulator atomicLong = new LongAccumulator(Long::sum, 0);

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
			if (msg instanceof LastHttpContent) {
				atomicLong.accumulate(1);
				FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
				response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, 0);
				ctx.write(response);
			}
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.flush();
		}

	}

}
