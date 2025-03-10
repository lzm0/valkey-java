package io.valkey.async.commands;

import io.valkey.args.ExpiryOption;
import io.valkey.params.RestoreParams;
import io.valkey.params.ScanParams;
import io.valkey.params.SortingParams;
import io.valkey.resps.ScanResult;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * This interface provides async commands for Redis key operations with binary data.
 */
public interface AsyncKeyBinaryCommands {

  /**
   * Async version of EXISTS command.
   * @param key key to check
   * @return CompletableFuture that completes with true if the key exists
   */
  CompletableFuture<Boolean> exists(byte[] key);

  /**
   * Async version of EXISTS command.
   * @param keys keys to check
   * @return CompletableFuture that completes with number of existing keys
   */
  CompletableFuture<Long> exists(byte[]... keys);

  /**
   * Async version of PERSIST command.
   * @param key key to persist
   * @return CompletableFuture that completes with 1 if the timeout was removed, 0 if key does not exist or does not have an associated timeout
   */
  CompletableFuture<Long> persist(byte[] key);

  /**
   * Async version of TYPE command.
   * @param key key to check
   * @return CompletableFuture that completes with type of key, or none when key does not exist
   */
  CompletableFuture<String> type(byte[] key);

  /**
   * Async version of DUMP command.
   * @param key key to dump
   * @return CompletableFuture that completes with serialized value
   */
  CompletableFuture<byte[]> dump(byte[] key);

  /**
   * Async version of RESTORE command.
   * @param key key to restore
   * @param ttl time to live in milliseconds
   * @param serializedValue serialized value
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> restore(byte[] key, long ttl, byte[] serializedValue);

  /**
   * Async version of RESTORE command.
   * @param key key to restore
   * @param ttl time to live in milliseconds
   * @param serializedValue serialized value
   * @param params additional parameters
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> restore(byte[] key, long ttl, byte[] serializedValue, RestoreParams params);

  /**
   * Async version of EXPIRE command.
   * @param key key to expire
   * @param seconds time to live in seconds
   * @return CompletableFuture that completes with 1 if the timeout was set, 0 if key does not exist or the timeout could not be set
   */
  CompletableFuture<Long> expire(byte[] key, long seconds);

  /**
   * Async version of EXPIRE command.
   * @param key key to expire
   * @param seconds time to live in seconds
   * @param expiryOption expiry option
   * @return CompletableFuture that completes with 1 if the timeout was set, 0 if key does not exist or the timeout could not be set
   */
  CompletableFuture<Long> expire(byte[] key, long seconds, ExpiryOption expiryOption);

  /**
   * Async version of PEXPIRE command.
   * @param key key to expire
   * @param milliseconds time to live in milliseconds
   * @return CompletableFuture that completes with 1 if the timeout was set, 0 if key does not exist or the timeout could not be set
   */
  CompletableFuture<Long> pexpire(byte[] key, long milliseconds);

  /**
   * Async version of PEXPIRE command.
   * @param key key to expire
   * @param milliseconds time to live in milliseconds
   * @param expiryOption expiry option
   * @return CompletableFuture that completes with 1 if the timeout was set, 0 if key does not exist or the timeout could not be set
   */
  CompletableFuture<Long> pexpire(byte[] key, long milliseconds, ExpiryOption expiryOption);

  /**
   * Async version of EXPIRETIME command.
   * @param key key to check
   * @return CompletableFuture that completes with the absolute Unix timestamp (seconds) the key will expire at, or -1 if the key has no expiry
   */
  CompletableFuture<Long> expireTime(byte[] key);

  /**
   * Async version of PEXPIRETIME command.
   * @param key key to check
   * @return CompletableFuture that completes with the absolute Unix timestamp (milliseconds) the key will expire at, or -1 if the key has no expiry
   */
  CompletableFuture<Long> pexpireTime(byte[] key);

  /**
   * Async version of EXPIREAT command.
   * @param key key to expire
   * @param unixTime timestamp in seconds
   * @return CompletableFuture that completes with 1 if the timeout was set, 0 if key does not exist or the timeout could not be set
   */
  CompletableFuture<Long> expireAt(byte[] key, long unixTime);

  /**
   * Async version of EXPIREAT command.
   * @param key key to expire
   * @param unixTime timestamp in seconds
   * @param expiryOption expiry option
   * @return CompletableFuture that completes with 1 if the timeout was set, 0 if key does not exist or the timeout could not be set
   */
  CompletableFuture<Long> expireAt(byte[] key, long unixTime, ExpiryOption expiryOption);

  /**
   * Async version of PEXPIREAT command.
   * @param key key to expire
   * @param millisecondsTimestamp timestamp in milliseconds
   * @return CompletableFuture that completes with 1 if the timeout was set, 0 if key does not exist or the timeout could not be set
   */
  CompletableFuture<Long> pexpireAt(byte[] key, long millisecondsTimestamp);

  /**
   * Async version of PEXPIREAT command.
   * @param key key to expire
   * @param millisecondsTimestamp timestamp in milliseconds
   * @param expiryOption expiry option
   * @return CompletableFuture that completes with 1 if the timeout was set, 0 if key does not exist or the timeout could not be set
   */
  CompletableFuture<Long> pexpireAt(byte[] key, long millisecondsTimestamp, ExpiryOption expiryOption);

  /**
   * Async version of TTL command.
   * @param key key to check
   * @return CompletableFuture that completes with TTL in seconds, -2 if the key does not exist, -1 if the key exists but has no associated expire
   */
  CompletableFuture<Long> ttl(byte[] key);

  /**
   * Async version of PTTL command.
   * @param key key to check
   * @return CompletableFuture that completes with TTL in milliseconds, -2 if the key does not exist, -1 if the key exists but has no associated expire
   */
  CompletableFuture<Long> pttl(byte[] key);

  /**
   * Async version of TOUCH command.
   * @param key key to touch
   * @return CompletableFuture that completes with number of keys that were touched
   */
  CompletableFuture<Long> touch(byte[] key);

  /**
   * Async version of TOUCH command.
   * @param keys keys to touch
   * @return CompletableFuture that completes with number of keys that were touched
   */
  CompletableFuture<Long> touch(byte[]... keys);

  /**
   * Async version of SORT command.
   * @param key key to sort
   * @return CompletableFuture that completes with sorted elements
   */
  CompletableFuture<List<byte[]>> sort(byte[] key);

  /**
   * Async version of SORT command.
   * @param key key to sort
   * @param sortingParams sorting parameters
   * @return CompletableFuture that completes with sorted elements
   */
  CompletableFuture<List<byte[]>> sort(byte[] key, SortingParams sortingParams);

  /**
   * Async version of SORT_RO command.
   * @param key key to sort
   * @param sortingParams sorting parameters
   * @return CompletableFuture that completes with sorted elements
   */
  CompletableFuture<List<byte[]>> sortReadonly(byte[] key, SortingParams sortingParams);

  /**
   * Async version of DEL command.
   * @param key key to delete
   * @return CompletableFuture that completes with number of keys that were removed
   */
  CompletableFuture<Long> del(byte[] key);

  /**
   * Async version of DEL command.
   * @param keys keys to delete
   * @return CompletableFuture that completes with number of keys that were removed
   */
  CompletableFuture<Long> del(byte[]... keys);

  /**
   * Async version of UNLINK command.
   * @param key key to unlink
   * @return CompletableFuture that completes with number of keys that were unlinked
   */
  CompletableFuture<Long> unlink(byte[] key);

  /**
   * Async version of UNLINK command.
   * @param keys keys to unlink
   * @return CompletableFuture that completes with number of keys that were unlinked
   */
  CompletableFuture<Long> unlink(byte[]... keys);

  /**
   * Async version of COPY command.
   * @param srcKey source key
   * @param dstKey destination key
   * @param replace whether to replace existing key
   * @return CompletableFuture that completes with true if key was copied
   */
  CompletableFuture<Boolean> copy(byte[] srcKey, byte[] dstKey, boolean replace);

  /**
   * Async version of RENAME command.
   * @param oldkey old key name
   * @param newkey new key name
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> rename(byte[] oldkey, byte[] newkey);

  /**
   * Async version of RENAMENX command.
   * @param oldkey old key name
   * @param newkey new key name
   * @return CompletableFuture that completes with 1 if key was renamed, 0 if target key already exists
   */
  CompletableFuture<Long> renamenx(byte[] oldkey, byte[] newkey);

  /**
   * Async version of SORT command.
   * @param key key to sort
   * @param sortingParams sorting parameters
   * @param dstkey destination key
   * @return CompletableFuture that completes with number of elements stored at destination
   */
  CompletableFuture<Long> sort(byte[] key, SortingParams sortingParams, byte[] dstkey);

  /**
   * Async version of SORT command.
   * @param key key to sort
   * @param dstkey destination key
   * @return CompletableFuture that completes with number of elements stored at destination
   */
  CompletableFuture<Long> sort(byte[] key, byte[] dstkey);

  /**
   * Async version of MEMORY USAGE command.
   * @param key key to check
   * @return CompletableFuture that completes with memory usage in bytes
   */
  CompletableFuture<Long> memoryUsage(byte[] key);

  /**
   * Async version of MEMORY USAGE command.
   * @param key key to check
   * @param samples number of samples
   * @return CompletableFuture that completes with memory usage in bytes
   */
  CompletableFuture<Long> memoryUsage(byte[] key, int samples);

  /**
   * Async version of OBJECT REFCOUNT command.
   * @param key key to check
   * @return CompletableFuture that completes with reference count
   */
  CompletableFuture<Long> objectRefcount(byte[] key);

  CompletableFuture<Set<String>> keys(String pattern);

  CompletableFuture<ScanResult<String>> scan(String cursor);

  CompletableFuture<ScanResult<String>> scan(String cursor, ScanParams params);

  CompletableFuture<ScanResult<String>> scan(String cursor, ScanParams params, String type);

  /**
   * <b><a href="http://redis.io/commands/randomkey">RandomKey Command</a></b>
   * Return a randomly selected key from the currently selected DB.
   * <p>
   * Time complexity: O(1)
   * @return The random key, or {@code nil} when the database is empty
   */
  CompletableFuture<String> randomKey();
} 