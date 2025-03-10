package io.valkey.executors;

import io.valkey.CommandObject;
import io.valkey.Connection;
import io.valkey.providers.ConnectionProvider;
import io.valkey.util.IOUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AsyncCommandExecutor implements CommandExecutor {
    /**
     * The following are the default parameters for the async executor service
     * Since Redis query is usually a slower IO operation (requires more threads),
     * so we set DEFAULT_CORE_POOL_SIZE to be the same as the core
     */
    private static final long DEFAULT_KEEPALIVE_TIME_MS = 60000L;
    private static final int DEFAULT_BLOCKING_QUEUE_SIZE = 1024;
    private static final int DEFAULT_CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;

    protected final ConnectionProvider provider;
    private final ExecutorService executorService;

    public AsyncCommandExecutor(ConnectionProvider provider) {
        this.provider = provider;
        this.executorService = new ThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE,
            DEFAULT_MAXIMUM_POOL_SIZE,
            DEFAULT_KEEPALIVE_TIME_MS,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(DEFAULT_BLOCKING_QUEUE_SIZE));
    }

    public AsyncCommandExecutor(ConnectionProvider provider, ExecutorService executorService) {
        this.provider = provider;
        this.executorService = executorService;
    }

    @Override
    public void close() {
        IOUtils.closeQuietly(this.provider);
        executorService.shutdown();
    }

    @Override
    public <T> T executeCommand(CommandObject<T> commandObject) {
        return executeCommandAsync(commandObject).join();
    }

    @Override
    public <T> T broadcastCommand(CommandObject<T> commandObject) {
        return executeCommandAsync(commandObject).join();
    }

    public <T> CompletableFuture<T> executeCommandAsync(CommandObject<T> commandObject) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = provider.getConnection(commandObject.getArguments())) {
                return connection.executeCommand(commandObject);
            }
        }, executorService);
    }
} 