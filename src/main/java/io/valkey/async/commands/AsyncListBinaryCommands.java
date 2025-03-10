package io.valkey.async.commands;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import io.valkey.args.ListDirection;
import io.valkey.args.ListPosition;
import io.valkey.params.LPosParams;
import io.valkey.util.KeyValue;

/**
 * This interface provides async commands for Redis list operations with binary data.
 */
public interface AsyncListBinaryCommands {

  /**
   * Binary version of RPUSH command.
   * @param key the key
   * @param args data to push
   * @return CompletableFuture that completes with the length of the list after push
   */
  CompletableFuture<Long> rpush(byte[] key, byte[]... args);

  /**
   * Binary version of LPUSH command.
   * @param key the key
   * @param args data to push
   * @return CompletableFuture that completes with the length of the list after push
   */
  CompletableFuture<Long> lpush(byte[] key, byte[]... args);

  /**
   * Binary version of LLEN command.
   * @param key the key
   * @return CompletableFuture that completes with the length of the list
   */
  CompletableFuture<Long> llen(byte[] key);

  /**
   * Binary version of LRANGE command.
   * @param key the key
   * @param start start index
   * @param stop stop index (inclusive)
   * @return CompletableFuture that completes with list of elements in the specified range
   */
  CompletableFuture<List<byte[]>> lrange(byte[] key, long start, long stop);

  /**
   * Binary version of LTRIM command.
   * @param key the key
   * @param start start index
   * @param stop stop index (inclusive)
   * @return CompletableFuture that completes with "OK"
   */
  CompletableFuture<String> ltrim(byte[] key, long start, long stop);

  /**
   * Binary version of LINDEX command.
   * @param key the key
   * @param index the index
   * @return CompletableFuture that completes with the element at index
   */
  CompletableFuture<byte[]> lindex(byte[] key, long index);

  /**
   * Binary version of LSET command.
   * @param key the key
   * @param index the index
   * @param value the value
   * @return CompletableFuture that completes with "OK"
   */
  CompletableFuture<String> lset(byte[] key, long index, byte[] value);

  /**
   * Binary version of LREM command.
   * @param key the key
   * @param count number of occurrences to remove
   * @param value the value to remove
   * @return CompletableFuture that completes with the number of removed elements
   */
  CompletableFuture<Long> lrem(byte[] key, long count, byte[] value);

  /**
   * Binary version of LPOP command.
   * @param key the key
   * @return CompletableFuture that completes with the first element
   */
  CompletableFuture<byte[]> lpop(byte[] key);

  /**
   * Binary version of LPOP command.
   * @param key the key
   * @param count number of elements to pop
   * @return CompletableFuture that completes with list of popped elements
   */
  CompletableFuture<List<byte[]>> lpop(byte[] key, int count);

  /**
   * Binary version of LPOS command.
   * @param key the key
   * @param element element to search for
   * @return CompletableFuture that completes with the index of the element
   */
  CompletableFuture<Long> lpos(byte[] key, byte[] element);

  /**
   * Binary version of LPOS command with parameters.
   * @param key the key
   * @param element element to search for
   * @param params additional parameters
   * @return CompletableFuture that completes with the index of the element
   */
  CompletableFuture<Long> lpos(byte[] key, byte[] element, LPosParams params);

  /**
   * Binary version of LPOS command with count.
   * @param key the key
   * @param element element to search for
   * @param params additional parameters
   * @param count number of matches to return
   * @return CompletableFuture that completes with list of matching indexes
   */
  CompletableFuture<List<Long>> lpos(byte[] key, byte[] element, LPosParams params, long count);

  /**
   * Binary version of RPOP command.
   * @param key the key
   * @return CompletableFuture that completes with the last element
   */
  CompletableFuture<byte[]> rpop(byte[] key);

  /**
   * Binary version of RPOP command.
   * @param key the key
   * @param count number of elements to pop
   * @return CompletableFuture that completes with list of popped elements
   */
  CompletableFuture<List<byte[]>> rpop(byte[] key, int count);

  /**
   * Binary version of LINSERT command.
   * @param key the key
   * @param where BEFORE or AFTER the pivot
   * @param pivot the pivot element
   * @param value the value to insert
   * @return CompletableFuture that completes with the length of the list after insert
   */
  CompletableFuture<Long> linsert(byte[] key, ListPosition where, byte[] pivot, byte[] value);

  /**
   * Binary version of LPUSHX command.
   * @param key the key
   * @param args values to push
   * @return CompletableFuture that completes with the length of the list after push
   */
  CompletableFuture<Long> lpushx(byte[] key, byte[]... args);

  /**
   * Binary version of RPUSHX command.
   * @param key the key
   * @param args values to push
   * @return CompletableFuture that completes with the length of the list after push
   */
  CompletableFuture<Long> rpushx(byte[] key, byte[]... args);

  /**
   * Binary version of BLPOP command.
   * @param timeout seconds to wait
   * @param keys the keys
   * @return CompletableFuture that completes with list containing the key and element
   */
  CompletableFuture<List<byte[]>> blpop(int timeout, byte[]... keys);

  /**
   * Binary version of BLPOP command.
   * @param timeout seconds to wait as a double
   * @param keys the keys
   * @return CompletableFuture that completes with key-value pair containing the key and element
   */
  CompletableFuture<KeyValue<byte[], byte[]>> blpop(double timeout, byte[]... keys);

  /**
   * Binary version of BRPOP command.
   * @param timeout seconds to wait
   * @param keys the keys
   * @return CompletableFuture that completes with list containing the key and element
   */
  CompletableFuture<List<byte[]>> brpop(int timeout, byte[]... keys);

  /**
   * Binary version of BRPOP command.
   * @param timeout seconds to wait as a double
   * @param keys the keys
   * @return CompletableFuture that completes with key-value pair containing the key and element
   */
  CompletableFuture<KeyValue<byte[], byte[]>> brpop(double timeout, byte[]... keys);

  /**
   * Binary version of RPOPLPUSH command.
   * @param srckey the source key
   * @param dstkey the destination key
   * @return CompletableFuture that completes with the element being moved
   */
  CompletableFuture<byte[]> rpoplpush(byte[] srckey, byte[] dstkey);

  /**
   * Binary version of BRPOPLPUSH command.
   * @param source the source key
   * @param destination the destination key
   * @param timeout seconds to wait
   * @return CompletableFuture that completes with the element being moved
   */
  CompletableFuture<byte[]> brpoplpush(byte[] source, byte[] destination, int timeout);

  /**
   * Binary version of LMOVE command.
   * @param srcKey the source key
   * @param dstKey the destination key
   * @param from LEFT or RIGHT
   * @param to LEFT or RIGHT
   * @return CompletableFuture that completes with the element being moved
   */
  CompletableFuture<byte[]> lmove(byte[] srcKey, byte[] dstKey, ListDirection from, ListDirection to);

  /**
   * Binary version of BLMOVE command.
   * @param srcKey the source key
   * @param dstKey the destination key
   * @param from LEFT or RIGHT
   * @param to LEFT or RIGHT
   * @param timeout seconds to wait
   * @return CompletableFuture that completes with the element being moved
   */
  CompletableFuture<byte[]> blmove(byte[] srcKey, byte[] dstKey, ListDirection from, ListDirection to, double timeout);

  /**
   * Binary version of LMPOP command.
   * @param direction LEFT or RIGHT
   * @param keys the keys
   * @return CompletableFuture that completes with key and list of popped elements
   */
  CompletableFuture<KeyValue<byte[], List<byte[]>>> lmpop(ListDirection direction, byte[]... keys);

  /**
   * Binary version of LMPOP command.
   * @param direction LEFT or RIGHT
   * @param count number of elements to pop
   * @param keys the keys
   * @return CompletableFuture that completes with key and list of popped elements
   */
  CompletableFuture<KeyValue<byte[], List<byte[]>>> lmpop(ListDirection direction, int count, byte[]... keys);

  /**
   * Binary version of BLMPOP command.
   * @param timeout seconds to wait
   * @param direction LEFT or RIGHT
   * @param keys the keys
   * @return CompletableFuture that completes with key and list of popped elements
   */
  CompletableFuture<KeyValue<byte[], List<byte[]>>> blmpop(double timeout, ListDirection direction, byte[]... keys);

  /**
   * Binary version of BLMPOP command.
   * @param timeout seconds to wait
   * @param direction LEFT or RIGHT
   * @param count number of elements to pop
   * @param keys the keys
   * @return CompletableFuture that completes with key and list of popped elements
   */
  CompletableFuture<KeyValue<byte[], List<byte[]>>> blmpop(double timeout, ListDirection direction, int count, byte[]... keys);
} 