package io.valkey.async.commands;

import java.util.concurrent.CompletableFuture;

/**
 * This interface provides async commands for Redis HyperLogLog operations with binary data.
 */
public interface AsyncHyperLogLogBinaryCommands {

  /**
   * Binary version of PFADD command.
   * @param key the key
   * @param elements elements to add
   * @return CompletableFuture that completes with 1 if at least one element was added, 0 otherwise
   */
  CompletableFuture<Long> pfadd(byte[] key, byte[]... elements);

  /**
   * Binary version of PFMERGE command.
   * @param destkey destination key
   * @param sourcekeys source keys to merge
   * @return CompletableFuture that completes with "OK"
   */
  CompletableFuture<String> pfmerge(byte[] destkey, byte[]... sourcekeys);

  /**
   * Binary version of PFCOUNT command.
   * @param key the key
   * @return CompletableFuture that completes with the approximated number of unique elements
   */
  CompletableFuture<Long> pfcount(byte[] key);

  /**
   * Binary version of PFCOUNT command.
   * @param keys the keys
   * @return CompletableFuture that completes with the approximated number of unique elements
   */
  CompletableFuture<Long> pfcount(byte[]... keys);
} 