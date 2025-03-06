package io.valkey.async.handlers;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.valkey.async.RedisCommand;

public class CommandEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf buf) throws Exception {
        System.out.println("CommandEncoder.encode - Starting to encode command: " + msg);
        try {
            List<String> commands;
            if (msg instanceof RedisCommand) {
                commands = ((RedisCommand) msg).getCommand();
            } else if (msg instanceof List) {
                commands = (List<String>) msg;
            } else {
                throw new IllegalArgumentException("Unknown message type: " + msg.getClass());
            }

            buf.writeByte('*');
            buf.writeBytes(String.valueOf(commands.size()).getBytes());
            buf.writeByte('\r');
            buf.writeByte('\n');

            for (String cmd : commands) {
                buf.writeByte('$');
                buf.writeBytes(String.valueOf(cmd.length()).getBytes());
                buf.writeByte('\r');
                buf.writeByte('\n');
                buf.writeBytes(cmd.getBytes());
                buf.writeByte('\r');
                buf.writeByte('\n');
            }
            System.out.println("CommandEncoder.encode - Finished encoding command");
        } catch (Exception e) {
            System.err.println("CommandEncoder.encode - Error encoding command: " + e);
            throw e;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.println("CommandEncoder - Exception caught: " + cause);
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
    }
}

