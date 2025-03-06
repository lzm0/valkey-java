package io.valkey.async.handlers;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RedisChannelInitializer extends ChannelInitializer<SocketChannel> {

    @ChannelHandler.Sharable
    private static class LoggingExceptionHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            System.err.println("Pipeline Exception: " + cause);
            cause.printStackTrace();
            ctx.close();
        }
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();
        p.addLast("encoder", new CommandEncoder());
        p.addLast("decoder", new CommandDecoder());
        p.addLast("exceptionHandler", new LoggingExceptionHandler());
    }
}
