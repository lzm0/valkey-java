package io.valkey.async.commands;

import io.valkey.args.ExpiryOption;
import io.valkey.params.RestoreParams;
import io.valkey.params.ScanParams;
import io.valkey.params.SortingParams;
import io.valkey.resps.ScanResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This interface provides async commands for Redis key operations.
 */
public interface AsyncKeyCommands {

  /**
   * Async version of EXISTS command.
   * @param key key to check
   * @return CompletableFuture that completes with true if the key exists
   */
  CompletableFuture<Boolean> exists(String key);

  /**
   * Async version of EXISTS command.
   * @param keys keys to check
   * @return CompletableFuture that completes with number of existing keys
   */
  CompletableFuture<Long> exists(String... keys);

  /**
   * Async version of PERSIST command.
   * @param key key to persist
   * @return CompletableFuture that completes with 1 if the timeout was removed, 0 if key does not exist or does not have an associated timeout
   */
  CompletableFuture<Long> persist(String key);

  /**
   * Async version of TYPE command.
   * @param key key to check
   * @return CompletableFuture that completes with type of key, or none when key does not exist
   */
  CompletableFuture<String> type(String key);

  /**
   * Async version of DUMP command.
   * @param key key to dump
   * @return CompletableFuture that completes with serialized value
   */
  CompletableFuture<byte[]> dump(String key);

  /**
   * Async version of RESTORE command.
   * @param key key to restore
   * @param ttl time to live in milliseconds
   * @param serializedValue serialized value
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> restore(String key, long ttl, byte[] serializedValue);

  /**
   * Async version of RESTORE command.
   * @param key key to restore
   * @param ttl time to live in milliseconds
   * @param serializedValue serialized value
   * @param params additional parameters
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> restore(String key, long ttl, byte[] serializedValue, RestoreParams params);

  /**
   * Async version of EXPIRE command.
   * @param key key to expire
   * @param seconds time to live in seconds
   * @return CompletableFuture that completes with 1 if the timeout was set, 0 if key does not exist or the timeout could not be set
   */
  CompletableFuture<Long> expire(String key, long seconds);

  /**
   * Async version of EXPIRE command.
   * @param key key to expire
   * @param seconds time to live in seconds
   * @param expiryOption expiry option
   * @return CompletableFuture that completes with 1 if the timeout was set, 0 if key does not exist or the timeout could not be set
   */
  CompletableFuture<Long> expire(String key, long seconds, ExpiryOption expiryOption);

  /**
   * Async version of PEXPIRE command.
   * @param key key to expire
   * @param milliseconds time to live in milliseconds
   * @return CompletableFuture that completes with 1 if the timeout was set, 0 if key does not exist or the timeout could not be set
   */
  CompletableFuture<Long> pexpire(String key, long milliseconds);

  /**
   * Async version of PEXPIRE command.
   * @param key key to expire
   * @param milliseconds time to live in milliseconds
   * @param expiryOption expiry option
   * @return CompletableFuture that completes with 1 if the timeout was set, 0 if key does not exist or the timeout could not be set
   */
  CompletableFuture<Long> pexpire(String key, long milliseconds, ExpiryOption expiryOption);

  /**
   * Async version of EXPIRETIME command.
   * @param key key to check
   * @return CompletableFuture that completes with the absolute Unix timestamp (seconds) the key will expire at, or -1 if the key has no expiry
   */
  CompletableFuture<Long> expireTime(String key);

  /**
   * Async version of PEXPIRETIME command.
   * @param key key to check
   * @return CompletableFuture that completes with the absolute Unix timestamp (milliseconds) the key will expire at, or -1 if the key has no expiry
   */
  CompletableFuture<Long> pexpireTime(String key);

  /**
   * Async version of EXPIREAT command.
   * @param key key to expire
   * @param unixTime timestamp in seconds
   * @return CompletableFuture that completes with 1 if the timeout was set, 0 if key does not exist or the timeout could not be set
   */
  CompletableFuture<Long> expireAt(String key, long unixTime);

  /**
   * Async version of EXPIREAT command.
   * @param key key to expire
   * @param unixTime timestamp in seconds
   * @param expiryOption expiry option
   * @return CompletableFuture that completes with 1 if the timeout was set, 0 if key does not exist or the timeout could not be set
   */
  CompletableFuture<Long> expireAt(String key, long unixTime, ExpiryOption expiryOption);

  /**
   * Async version of PEXPIREAT command.
   * @param key key to expire
   * @param millisecondsTimestamp timestamp in milliseconds
   * @return CompletableFuture that completes with 1 if the timeout was set, 0 if key does not exist or the timeout could not be set
   */
  CompletableFuture<Long> pexpireAt(String key, long millisecondsTimestamp);

  /**
   * Async version of PEXPIREAT command.
   * @param key key to expire
   * @param millisecondsTimestamp timestamp in milliseconds
   * @param expiryOption expiry option
   * @return CompletableFuture that completes with 1 if the timeout was set, 0 if key does not exist or the timeout could not be set
   */
  CompletableFuture<Long> pexpireAt(String key, long millisecondsTimestamp, ExpiryOption expiryOption);

  /**
   * Async version of TTL command.
   * @param key key to check
   * @return CompletableFuture that completes with TTL in seconds, -2 if the key does not exist, -1 if the key exists but has no associated expire
   */
  CompletableFuture<Long> ttl(String key);

  /**
   * Async version of PTTL command.
   * @param key key to check
   * @return CompletableFuture that completes with TTL in milliseconds, -2 if the key does not exist, -1 if the key exists but has no associated expire
   */
  CompletableFuture<Long> pttl(String key);

  /**
   * Async version of TOUCH command.
   * @param key key to touch
   * @return CompletableFuture that completes with number of keys that were touched
   */
  CompletableFuture<Long> touch(String key);

  /**
   * Async version of TOUCH command.
   * @param keys keys to touch
   * @return CompletableFuture that completes with number of keys that were touched
   */
  CompletableFuture<Long> touch(String... keys);

  /**
   * Async version of SORT command.
   * @param key key to sort
   * @return CompletableFuture that completes with sorted elements
   */
  CompletableFuture<List<String>> sort(String key);

  /**
   * Async version of SORT command.
   * @param key key to sort
   * @param dstkey destination key
   * @return CompletableFuture that completes with number of elements stored at destination
   */
  CompletableFuture<Long> sort(String key, String dstkey);

  /**
   * Async version of SORT command.
   * @param key key to sort
   * @param sortingParams sorting parameters
   * @return CompletableFuture that completes with sorted elements
   */
  CompletableFuture<List<String>> sort(String key, SortingParams sortingParams);

  /**
   * Async version of SORT command.
   * @param key key to sort
   * @param sortingParams sorting parameters
   * @param dstkey destination key
   * @return CompletableFuture that completes with number of elements stored at destination
   */
  CompletableFuture<Long> sort(String key, SortingParams sortingParams, String dstkey);

  /**
   * Async version of SORT_RO command.
   * @param key key to sort
   * @param sortingParams sorting parameters
   * @return CompletableFuture that completes with sorted elements
   */
  CompletableFuture<List<String>> sortReadonly(String key, SortingParams sortingParams);

  /**
   * Async version of DEL command.
   * @param key key to delete
   * @return CompletableFuture that completes with number of keys that were removed
   */
  CompletableFuture<Long> del(String key);

  /**
   * Async version of DEL command.
   * @param keys keys to delete
   * @return CompletableFuture that completes with number of keys that were removed
   */
  CompletableFuture<Long> del(String... keys);

  /**
   * Async version of UNLINK command.
   * @param key key to unlink
   * @return CompletableFuture that completes with number of keys that were unlinked
   */
  CompletableFuture<Long> unlink(String key);

  /**
   * Async version of UNLINK command.
   * @param keys keys to unlink
   * @return CompletableFuture that completes with number of keys that were unlinked
   */
  CompletableFuture<Long> unlink(String... keys);

  /**
   * Async version of COPY command.
   * @param srcKey source key
   * @param dstKey destination key
   * @param replace whether to replace existing key
   * @return CompletableFuture that completes with true if key was copied
   */
  CompletableFuture<Boolean> copy(String srcKey, String dstKey, boolean replace);

  /**
   * Async version of RENAME command.
   * @param oldkey old key name
   * @param newkey new key name
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> rename(String oldkey, String newkey);

  /**
   * Async version of RENAMENX command.
   * @param oldkey old key name
   * @param newkey new key name
   * @return CompletableFuture that completes with 1 if key was renamed, 0 if target key already exists
   */
  CompletableFuture<Long> renamenx(String oldkey, String newkey);

  /**
   * Async version of MEMORY USAGE command.
   * @param key key to check
   * @return CompletableFuture that completes with memory usage in bytes
   */
  CompletableFuture<Long> memoryUsage(String key);

  /**
   * Async version of MEMORY USAGE command.
   * @param key key to check
   * @param samples number of samples
   * @return CompletableFuture that completes with memory usage in bytes
   */
  CompletableFuture<Long> memoryUsage(String key, int samples);

  /**
   * Async version of OBJECT REFCOUNT command.
   * @param key key to check
   * @return CompletableFuture that completes with reference count
   */
  CompletableFuture<Long> objectRefcount(String key);
} 