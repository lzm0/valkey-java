package io.valkey.async.commands;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import io.valkey.args.ListDirection;
import io.valkey.args.ListPosition;
import io.valkey.params.LPosParams;
import io.valkey.util.KeyValue;

/**
 * This interface provides async commands for Redis list operations.
 */
public interface AsyncListCommands {

  /**
   * Async version of <b><a href="http://redis.io/commands/rpush">RPUSH Command</a></b>
   * Add the string value to the tail of the list stored at key.
   * @param key the key
   * @param strings data to push
   * @return CompletableFuture that completes with the length of the list after the push operation
   */
  CompletableFuture<Long> rpush(String key, String... strings);

  /**
   * Async version of <b><a href="http://redis.io/commands/lpush">LPUSH Command</a></b>
   * Add the string value to the head of the list stored at key.
   * @param key the key
   * @param strings data to push
   * @return CompletableFuture that completes with the length of the list after the push operation
   */
  CompletableFuture<Long> lpush(String key, String... strings);

  /**
   * Async version of <b><a href="http://redis.io/commands/llen">LLEN Command</a></b>
   * Return the length of the list stored at the specified key.
   * @param key the key
   * @return CompletableFuture that completes with the length of the list
   */
  CompletableFuture<Long> llen(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/lrange">LRANGE Command</a></b>
   * Return the specified elements of the list stored at key.
   * @param key the key
   * @param start start index (0-based)
   * @param stop stop index (inclusive)
   * @return CompletableFuture that completes with list of elements in the specified range
   */
  CompletableFuture<List<String>> lrange(String key, long start, long stop);

  /**
   * Async version of <b><a href="http://redis.io/commands/ltrim">LTRIM Command</a></b>
   * Trim an existing list so that it will contain only the specified range of elements.
   * @param key the key
   * @param start start index
   * @param stop stop index (inclusive)
   * @return CompletableFuture that completes with "OK"
   */
  CompletableFuture<String> ltrim(String key, long start, long stop);

  /**
   * Async version of <b><a href="http://redis.io/commands/lindex">LINDEX Command</a></b>
   * Return the element at index in the list stored at key.
   * @param key the key
   * @param index the index
   * @return CompletableFuture that completes with the requested element
   */
  CompletableFuture<String> lindex(String key, long index);

  /**
   * Async version of <b><a href="http://redis.io/commands/lset">LSET Command</a></b>
   * Set the list element at index to value.
   * @param key the key
   * @param index the index
   * @param value the value
   * @return CompletableFuture that completes with "OK"
   */
  CompletableFuture<String> lset(String key, long index, String value);

  /**
   * Async version of <b><a href="http://redis.io/commands/lrem">LREM Command</a></b>
   * Remove the first count occurrences of elements equal to value from the list.
   * @param key the key
   * @param count number of occurrences to remove
   * @param value the value to remove
   * @return CompletableFuture that completes with the number of removed elements
   */
  CompletableFuture<Long> lrem(String key, long count, String value);

  /**
   * Async version of <b><a href="http://redis.io/commands/lpop">LPOP Command</a></b>
   * Remove and return the first element of the list stored at key.
   * @param key the key
   * @return CompletableFuture that completes with the value of the first element
   */
  CompletableFuture<String> lpop(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/lpop">LPOP Command</a></b>
   * Remove and return up to count elements from the list stored at key.
   * @param key the key
   * @param count number of elements to pop
   * @return CompletableFuture that completes with list of popped elements
   */
  CompletableFuture<List<String>> lpop(String key, int count);

  /**
   * Async version of <b><a href="http://redis.io/commands/lpos">LPOS Command</a></b>
   * Return the index of the first matching element in the list stored at key.
   * @param key the key
   * @param element element to search for
   * @return CompletableFuture that completes with the index of the element
   */
  CompletableFuture<Long> lpos(String key, String element);

  /**
   * Async version of <b><a href="http://redis.io/commands/lpos">LPOS Command</a></b>
   * Return the index of the first matching element in the list with additional parameters.
   * @param key the key
   * @param element element to search for
   * @param params additional parameters
   * @return CompletableFuture that completes with the index of the element
   */
  CompletableFuture<Long> lpos(String key, String element, LPosParams params);

  /**
   * Async version of <b><a href="http://redis.io/commands/lpos">LPOS Command</a></b>
   * Return multiple indexes of matching elements in the list.
   * @param key the key
   * @param element element to search for
   * @param params additional parameters
   * @param count number of matches to return
   * @return CompletableFuture that completes with list of matching indexes
   */
  CompletableFuture<List<Long>> lpos(String key, String element, LPosParams params, long count);

  /**
   * Async version of <b><a href="http://redis.io/commands/rpop">RPOP Command</a></b>
   * Remove and return the last element of the list stored at key.
   * @param key the key
   * @return CompletableFuture that completes with the value of the last element
   */
  CompletableFuture<String> rpop(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/rpop">RPOP Command</a></b>
   * Remove and return up to count elements from the list stored at key.
   * @param key the key
   * @param count number of elements to pop
   * @return CompletableFuture that completes with list of popped elements
   */
  CompletableFuture<List<String>> rpop(String key, int count);

  /**
   * Async version of <b><a href="http://redis.io/commands/linsert">LINSERT Command</a></b>
   * Insert element before or after the pivot element in the list stored at key.
   * @param key the key
   * @param where BEFORE or AFTER the pivot
   * @param pivot the pivot element
   * @param value the value to insert
   * @return CompletableFuture that completes with the length of the list after insert
   */
  CompletableFuture<Long> linsert(String key, ListPosition where, String pivot, String value);

  /**
   * Async version of <b><a href="http://redis.io/commands/lpushx">LPUSHX Command</a></b>
   * Insert specified values at the head of the list stored at key, only if key exists.
   * @param key the key
   * @param strings values to push
   * @return CompletableFuture that completes with the length of the list after push
   */
  CompletableFuture<Long> lpushx(String key, String... strings);

  /**
   * Async version of <b><a href="http://redis.io/commands/rpushx">RPUSHX Command</a></b>
   * Insert specified values at the tail of the list stored at key, only if key exists.
   * @param key the key
   * @param strings values to push
   * @return CompletableFuture that completes with the length of the list after push
   */
  CompletableFuture<Long> rpushx(String key, String... strings);

  /**
   * Async version of <b><a href="http://redis.io/commands/blpop">BLPOP Command</a></b>
   * Remove and get the first element in a list, or block until one is available.
   * @param timeout seconds to wait
   * @param keys the keys
   * @return CompletableFuture that completes with list containing the key and element
   */
  CompletableFuture<List<String>> blpop(int timeout, String... keys);

  /**
   * Async version of <b><a href="http://redis.io/commands/blpop">BLPOP Command</a></b>
   * Remove and get the first element in a list, or block until one is available.
   * @param timeout seconds to wait as a double
   * @param keys the keys
   * @return CompletableFuture that completes with key-value pair containing the key and element
   */
  CompletableFuture<KeyValue<String, String>> blpop(double timeout, String... keys);

  /**
   * Async version of <b><a href="http://redis.io/commands/brpop">BRPOP Command</a></b>
   * Remove and get the last element in a list, or block until one is available.
   * @param timeout seconds to wait
   * @param keys the keys
   * @return CompletableFuture that completes with list containing the key and element
   */
  CompletableFuture<List<String>> brpop(int timeout, String... keys);

  /**
   * Async version of <b><a href="http://redis.io/commands/brpop">BRPOP Command</a></b>
   * Remove and get the last element in a list, or block until one is available.
   * @param timeout seconds to wait as a double
   * @param keys the keys
   * @return CompletableFuture that completes with key-value pair containing the key and element
   */
  CompletableFuture<KeyValue<String, String>> brpop(double timeout, String... keys);

  /**
   * Async version of <b><a href="http://redis.io/commands/rpoplpush">RPOPLPUSH Command</a></b>
   * Remove the last element in a list, prepend it to another list and return it.
   * @param srckey the source key
   * @param dstkey the destination key
   * @return CompletableFuture that completes with the element being moved
   */
  CompletableFuture<String> rpoplpush(String srckey, String dstkey);

  /**
   * Async version of <b><a href="http://redis.io/commands/brpoplpush">BRPOPLPUSH Command</a></b>
   * Pop an element from a list, push it to another list and return it; or block until one is available.
   * @param source the source key
   * @param destination the destination key
   * @param timeout seconds to wait
   * @return CompletableFuture that completes with the element being moved
   */
  CompletableFuture<String> brpoplpush(String source, String destination, int timeout);

  /**
   * Async version of <b><a href="http://redis.io/commands/lmove">LMOVE Command</a></b>
   * Atomically move an element from one list to another.
   * @param srcKey the source key
   * @param dstKey the destination key
   * @param from LEFT or RIGHT
   * @param to LEFT or RIGHT
   * @return CompletableFuture that completes with the element being moved
   */
  CompletableFuture<String> lmove(String srcKey, String dstKey, ListDirection from, ListDirection to);

  /**
   * Async version of <b><a href="http://redis.io/commands/blmove">BLMOVE Command</a></b>
   * Atomically move an element from one list to another, blocking when source is empty.
   * @param srcKey the source key
   * @param dstKey the destination key
   * @param from LEFT or RIGHT
   * @param to LEFT or RIGHT
   * @param timeout seconds to wait
   * @return CompletableFuture that completes with the element being moved
   */
  CompletableFuture<String> blmove(String srcKey, String dstKey, ListDirection from, ListDirection to, double timeout);

  /**
   * Async version of <b><a href="http://redis.io/commands/lmpop">LMPOP Command</a></b>
   * Pop elements from first non-empty list.
   * @param direction LEFT or RIGHT
   * @param keys the keys
   * @return CompletableFuture that completes with key and list of popped elements
   */
  CompletableFuture<KeyValue<String, List<String>>> lmpop(ListDirection direction, String... keys);

  /**
   * Async version of <b><a href="http://redis.io/commands/lmpop">LMPOP Command</a></b>
   * Pop elements from first non-empty list.
   * @param direction LEFT or RIGHT
   * @param count number of elements to pop
   * @param keys the keys
   * @return CompletableFuture that completes with key and list of popped elements
   */
  CompletableFuture<KeyValue<String, List<String>>> lmpop(ListDirection direction, int count, String... keys);

  /**
   * Async version of <b><a href="http://redis.io/commands/blmpop">BLMPOP Command</a></b>
   * Pop elements from first non-empty list, or block until one is available.
   * @param timeout seconds to wait
   * @param direction LEFT or RIGHT
   * @param keys the keys
   * @return CompletableFuture that completes with key and list of popped elements
   */
  CompletableFuture<KeyValue<String, List<String>>> blmpop(double timeout, ListDirection direction, String... keys);

  /**
   * Async version of <b><a href="http://redis.io/commands/blmpop">BLMPOP Command</a></b>
   * Pop elements from first non-empty list, or block until one is available.
   * @param timeout seconds to wait
   * @param direction LEFT or RIGHT
   * @param count number of elements to pop
   * @param keys the keys
   * @return CompletableFuture that completes with key and list of popped elements
   */
  CompletableFuture<KeyValue<String, List<String>>> blmpop(double timeout, ListDirection direction, int count, String... keys);
} 