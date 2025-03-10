package io.valkey.async.commands;

import io.valkey.params.GetExParams;
import io.valkey.params.LCSParams;
import io.valkey.params.SetParams;
import io.valkey.resps.LCSMatchResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This interface provides async commands for Redis string operations with binary data.
 */
public interface AsyncStringBinaryCommands extends AsyncBitBinaryCommands {

  /**
   * Async version of SET command.
   * @param key key
   * @param value value
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> set(byte[] key, byte[] value);

  /**
   * Async version of SET command with params.
   * @param key key
   * @param value value
   * @param params set parameters
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> set(byte[] key, byte[] value, SetParams params);

  /**
   * Async version of GET command.
   * @param key key
   * @return CompletableFuture that completes with the value
   */
  CompletableFuture<byte[]> get(byte[] key);

  /**
   * Async version of SET and GET command.
   * @param key key
   * @param value value
   * @return CompletableFuture that completes with the old value
   */
  CompletableFuture<byte[]> setGet(byte[] key, byte[] value);

  /**
   * Async version of SET and GET command with params.
   * @param key key
   * @param value value
   * @param params set parameters
   * @return CompletableFuture that completes with the old value
   */
  CompletableFuture<byte[]> setGet(byte[] key, byte[] value, SetParams params);

  /**
   * Async version of GETDEL command.
   * @param key key
   * @return CompletableFuture that completes with the value and deletes the key
   */
  CompletableFuture<byte[]> getDel(byte[] key);

  /**
   * Async version of GETEX command.
   * @param key key
   * @param params get parameters
   * @return CompletableFuture that completes with the value
   */
  CompletableFuture<byte[]> getEx(byte[] key, GetExParams params);

  /**
   * Async version of SETRANGE command.
   * @param key key
   * @param offset offset
   * @param value value
   * @return CompletableFuture that completes with the length of the string after it was modified
   */
  CompletableFuture<Long> setrange(byte[] key, long offset, byte[] value);

  /**
   * Async version of GETRANGE command.
   * @param key key
   * @param startOffset start offset
   * @param endOffset end offset
   * @return CompletableFuture that completes with the substring
   */
  CompletableFuture<byte[]> getrange(byte[] key, long startOffset, long endOffset);

  /**
   * Async version of GETSET command.
   * @param key key
   * @param value value
   * @return CompletableFuture that completes with the old value
   * @deprecated Use {@link AsyncStringBinaryCommands#setGet(byte[], byte[])}
   */
  @Deprecated
  CompletableFuture<byte[]> getSet(byte[] key, byte[] value);

  /**
   * Async version of SETNX command.
   * @param key key
   * @param value value
   * @return CompletableFuture that completes with 1 if the key was set, 0 otherwise
   */
  CompletableFuture<Long> setnx(byte[] key, byte[] value);

  /**
   * Async version of SETEX command.
   * @param key key
   * @param seconds expiry in seconds
   * @param value value
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> setex(byte[] key, long seconds, byte[] value);

  /**
   * Async version of PSETEX command.
   * @param key key
   * @param milliseconds expiry in milliseconds
   * @param value value
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> psetex(byte[] key, long milliseconds, byte[] value);

  /**
   * Async version of MGET command.
   * @param keys keys
   * @return CompletableFuture that completes with list of values
   */
  CompletableFuture<List<byte[]>> mget(byte[]... keys);

  /**
   * Async version of MSET command.
   * @param keysvalues keys and values
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> mset(byte[]... keysvalues);

  /**
   * Async version of MSETNX command.
   * @param keysvalues keys and values
   * @return CompletableFuture that completes with 1 if all keys were set, 0 otherwise
   */
  CompletableFuture<Long> msetnx(byte[]... keysvalues);

  /**
   * Async version of INCR command.
   * @param key key
   * @return CompletableFuture that completes with the value after increment
   */
  CompletableFuture<Long> incr(byte[] key);

  /**
   * Async version of INCRBY command.
   * @param key key
   * @param increment increment by value
   * @return CompletableFuture that completes with the value after increment
   */
  CompletableFuture<Long> incrBy(byte[] key, long increment);

  /**
   * Async version of INCRBYFLOAT command.
   * @param key key
   * @param increment increment by value
   * @return CompletableFuture that completes with the value after increment
   */
  CompletableFuture<Double> incrByFloat(byte[] key, double increment);

  /**
   * Async version of DECR command.
   * @param key key
   * @return CompletableFuture that completes with the value after decrement
   */
  CompletableFuture<Long> decr(byte[] key);

  /**
   * Async version of DECRBY command.
   * @param key key
   * @param decrement decrement by value
   * @return CompletableFuture that completes with the value after decrement
   */
  CompletableFuture<Long> decrBy(byte[] key, long decrement);

  /**
   * Async version of APPEND command.
   * @param key key
   * @param value value to append
   * @return CompletableFuture that completes with the length of the string after append
   */
  CompletableFuture<Long> append(byte[] key, byte[] value);

  /**
   * Async version of SUBSTR command.
   * @param key key
   * @param start start index
   * @param end end index
   * @return CompletableFuture that completes with the substring
   */
  CompletableFuture<byte[]> substr(byte[] key, int start, int end);

  /**
   * Async version of STRLEN command.
   * @param key key
   * @return CompletableFuture that completes with the length of the string
   */
  CompletableFuture<Long> strlen(byte[] key);

  /**
   * Async version of LCS command.
   * Calculate the longest common subsequence of keyA and keyB.
   * @param keyA first key
   * @param keyB second key
   * @param params LCS parameters
   * @return CompletableFuture that completes with the LCS match result
   */
  CompletableFuture<LCSMatchResult> lcs(byte[] keyA, byte[] keyB, LCSParams params);
} 