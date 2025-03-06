package io.valkey.async.handlers;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.valkey.async.RedisCommand;


public class CommandDecoder extends ChannelDuplexHandler {

    protected final ByteBuf buffer = ByteBufAllocator.DEFAULT.directBuffer(8192 * 8);
    protected volatile Deque<RedisCommand> commands = new ArrayDeque<>(512);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("CommandDecoder.write - Processing command: " + msg);
        try {
            RedisCommand cmd = (RedisCommand) msg;
            commands.add(cmd);
            ctx.write(cmd.getCommand(), promise);
            System.out.println("CommandDecoder.write - Command added to queue and forwarded");
        } catch (Exception e) {
            System.err.println("CommandDecoder.write - Error processing command: " + e);
            throw e;
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("CommandDecoder.channelRead - Received response from Redis");
        ByteBuf input = (ByteBuf)msg;
        try {
            buffer.writeBytes(input);
            decode(ctx, buffer);
        } catch (Exception e) {
            System.err.println("CommandDecoder.channelRead - Error processing response: " + e);
            throw e;
        } finally {
            input.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.println("CommandDecoder - Exception caught: " + cause);
        cause.printStackTrace();
        // Mark current command as failed and remove it
        RedisCommand currentCommand = commands.peek();
        if (currentCommand != null) {
            currentCommand.completeExceptionally(cause);
            commands.remove();
        }
        ctx.fireExceptionCaught(cause);
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        System.out.println("CommandDecoder.decode - Starting to decode response");
        int length, end;
        ByteBuffer bytes;

        byte type = buffer.readByte();
        RedisCommand command = commands.peek();
        System.out.println("CommandDecoder.decode - Response type: " + (char)type);

        try {
            switch (type) {
                case '+':
                    bytes = readLine(buffer);
                    command.getReply().parse(bytes);
                    System.out.println("CommandDecoder.decode - Decoded simple string response");
                    break;
                case '$':
                    end = findLineEnd(buffer);
                    length = (int)readLong(buffer, buffer.readerIndex(), end);
                    bytes = readBytes(buffer, length);
                    command.getReply().parse(bytes);
                    System.out.println("CommandDecoder.decode - Decoded bulk string response");
                    break;
                case '*':
                    // Read array length
                    end = findLineEnd(buffer);
                    length = (int)readLong(buffer, buffer.readerIndex(), end);
                    StringBuilder result = new StringBuilder();
                    // Read all array elements
                    for (int i = 0; i < length; i++) {
                        if (buffer.readableBytes() < 1) {
                            return; // Wait for more data
                        }
                        buffer.readByte(); // Skip $
                        end = findLineEnd(buffer);
                        if (end == -1) {
                            return; // Wait for more data
                        }
                        int strLen = (int)readLong(buffer, buffer.readerIndex(), end);
                        bytes = readBytes(buffer, strLen);
                        if (bytes == null) {
                            return; // Wait for more data
                        }
                        if (i > 0) result.append(" ");
                        result.append(StandardCharsets.US_ASCII.decode(bytes).toString());
                    }
                    command.getReply().parse(ByteBuffer.wrap(result.toString().getBytes()));
                    System.out.println("CommandDecoder.decode - Decoded array response");
                    break;
                case '-':
                    bytes = readLine(buffer);
                    String errorMsg = StandardCharsets.US_ASCII.decode(bytes).toString();
                    command.completeExceptionally(new RuntimeException(errorMsg));
                    System.out.println("CommandDecoder.decode - Decoded error response: " + errorMsg);
                    break;
                default:
                    throw new Exception("Unknown type: " + type);
            }
            command.complete();
            System.out.println("CommandDecoder.decode - Command completed successfully");
        } catch (Exception e) {
            System.err.println("CommandDecoder.decode - Error while decoding: " + e.getMessage());
            if (command != null) {
                command.completeExceptionally(e);
            }
            throw e;
        } finally {
            if (command != null) {
                commands.remove();
            }
        }
    }

    private int findLineEnd(ByteBuf buffer) {
        int start = buffer.readerIndex();
        int index = buffer.indexOf(start, buffer.writerIndex(), (byte) '\n');
        return (index > 0 && buffer.getByte(index - 1) == '\r') ? index : -1;
    }

    private long readLong(ByteBuf buffer, int start, int end) {
        long value = 0;

        boolean negative = buffer.getByte(start) == '-';
        int offset = negative ? start + 1 : start;
        while (offset < end - 1) {
            int digit = buffer.getByte(offset++) - '0';
            value = value * 10 - digit;
        }
        if (!negative) {
            value = -value;
        }
        buffer.readerIndex(end + 1);

        return value;
    }

    private ByteBuffer readLine(ByteBuf buffer) {
        ByteBuffer bytes = null;
        int end = findLineEnd(buffer);
        if (end > -1) {
            int start = buffer.readerIndex();
            bytes = buffer.nioBuffer(start, end - start - 1);
            buffer.readerIndex(end + 1);
            buffer.markReaderIndex();
        }
        return bytes;
    }

    private ByteBuffer readBytes(ByteBuf buffer, int count) {
        ByteBuffer bytes = null;
        if (buffer.readableBytes() >= count + 2) {
            bytes = buffer.nioBuffer(buffer.readerIndex(), count);
            buffer.readerIndex(buffer.readerIndex() + count + 2);
        }
        return bytes;
    }
}

