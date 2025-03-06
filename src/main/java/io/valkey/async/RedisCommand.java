package io.valkey.async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RedisCommand {
    private final List<String> command;
    private final CompletableFuture<RedisReply> future;
    private final RedisReply reply;

    public RedisCommand(List<String> command) {
        this.command = command;
        this.future = new CompletableFuture<>();
        this.reply = new RedisReply();
    }

    public List<String> getCommand() {
        return command;
    }

    public CompletableFuture<RedisReply> getFuture() {
        return future;
    }

    public RedisReply getReply() {
        return reply;
    }

    public void complete() {
        future.complete(reply);
    }

    public void completeExceptionally(Throwable ex) {
        reply.setError(ex);
        future.complete(reply);
    }
}
