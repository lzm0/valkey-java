package io.valkey.async;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.valkey.DefaultJedisClientConfig;
import io.valkey.HostAndPort;
import io.valkey.JedisClientConfig;
import io.valkey.async.handlers.RedisChannelInitializer;
import io.valkey.exceptions.JedisConnectionException;
import java.util.concurrent.CompletableFuture;
import java.util.List;
import java.util.Arrays;

public class AsyncJedis {
    private final HostAndPort hostAndPort;
    private final JedisClientConfig config;
    private final EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;

    public AsyncJedis(HostAndPort hostAndPort) {
        this.hostAndPort = hostAndPort;
        this.config = DefaultJedisClientConfig.builder().build();
    }

    public AsyncJedis(HostAndPort hostAndPort, JedisClientConfig config) {
        this.hostAndPort = hostAndPort;
        this.config = config;
    }

    public boolean connect() throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .handler(new RedisChannelInitializer());

        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, config.getConnectionTimeoutMillis());
        ChannelFuture f = bootstrap.connect(hostAndPort.getHost(), hostAndPort.getPort());
        f.awaitUninterruptibly();
        if (f.isCancelled() || !f.isSuccess()) {
            throw new JedisConnectionException(f.cause());
        }
        channel = f.channel();
        return true;
    }

    public CompletableFuture<RedisReply> set(String key, String value) {
        List<String> command = Arrays.asList("SET", key, value);
        RedisCommand redisCommand = new RedisCommand(command);
        channel.writeAndFlush(redisCommand);
        return redisCommand.getFuture();
    }

    public CompletableFuture<RedisReply> get(String key) {
        List<String> command = Arrays.asList("GET", key);
        RedisCommand redisCommand = new RedisCommand(command);
        channel.writeAndFlush(redisCommand);
        return redisCommand.getFuture();
    }
}
