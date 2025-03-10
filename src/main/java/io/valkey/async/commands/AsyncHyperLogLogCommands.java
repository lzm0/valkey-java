package io.valkey.async.commands;

import java.util.concurrent.CompletableFuture;

/**
 * This interface provides async commands for Redis HyperLogLog operations.
 */
public interface AsyncHyperLogLogCommands {

  /**
   * Async version of <b><a href="http://redis.io/commands/pfadd">PFADD Command</a></b>
   * Adds elements to a HyperLogLog data structure.
   * @param key the key
   * @param elements elements to add
   * @return CompletableFuture that completes with 1 if at least one element was added, 0 otherwise
   */
  CompletableFuture<Long> pfadd(String key, String... elements);

  /**
   * Async version of <b><a href="http://redis.io/commands/pfmerge">PFMERGE Command</a></b>
   * Merges multiple HyperLogLog values into a single key.
   * @param destkey destination key
   * @param sourcekeys source keys to merge
   * @return CompletableFuture that completes with "OK"
   */
  CompletableFuture<String> pfmerge(String destkey, String... sourcekeys);

  /**
   * Async version of <b><a href="http://redis.io/commands/pfcount">PFCOUNT Command</a></b>
   * Returns the approximated cardinality of a HyperLogLog data structure.
   * @param key the key
   * @return CompletableFuture that completes with the approximated number of unique elements
   */
  CompletableFuture<Long> pfcount(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/pfcount">PFCOUNT Command</a></b>
   * Returns the approximated cardinality of the union of multiple HyperLogLog values.
   * @param keys the keys
   * @return CompletableFuture that completes with the approximated number of unique elements
   */
  CompletableFuture<Long> pfcount(String... keys);
} 