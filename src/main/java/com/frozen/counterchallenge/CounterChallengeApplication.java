package com.frozen.counterchallenge;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.atomic.LongAccumulator;

public class CounterChallengeApplication {

	private final static LongAccumulator atomicLong = new LongAccumulator(Long::sum, 0);

	public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new KQueueEventLoopGroup();
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
                    .channel(KQueueServerSocketChannel.class);

            Channel ch = bootstrap.bind(8080).sync().channel();
            ch.closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
	}

	static class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

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
