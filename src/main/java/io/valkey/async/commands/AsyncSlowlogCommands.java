package io.valkey.async.commands;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import io.valkey.resps.Slowlog;

/**
 * This interface provides async commands for Redis slowlog operations.
 */
public interface AsyncSlowlogCommands {

  /**
   * Async version of <b><a href="http://redis.io/commands/slowlog-get">SLOWLOG GET Command</a></b>
   * Returns the Redis slow queries log.
   * @return CompletableFuture that completes with list of {@link Slowlog} entries
   */
  CompletableFuture<List<Slowlog>> slowlogGet();

  /**
   * Async version of <b><a href="http://redis.io/commands/slowlog-get">SLOWLOG GET Command</a></b>
   * Returns the Redis slow queries log.
   * @param count number of entries to return
   * @return CompletableFuture that completes with list of {@link Slowlog} entries
   */
  CompletableFuture<List<Slowlog>> slowlogGet(int count);

  /**
   * Async version of <b><a href="http://redis.io/commands/slowlog-len">SLOWLOG LEN Command</a></b>
   * Returns the length of the Redis slow queries log.
   * @return CompletableFuture that completes with the length of the slow log
   */
  CompletableFuture<Long> slowlogLen();

  /**
   * Async version of <b><a href="http://redis.io/commands/slowlog-reset">SLOWLOG RESET Command</a></b>
   * Resets the Redis slow queries log.
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> slowlogReset();
} 