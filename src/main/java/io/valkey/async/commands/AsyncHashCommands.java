package io.valkey.async.commands;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import io.valkey.params.ScanParams;
import io.valkey.resps.ScanResult;

/**
 * This interface provides async commands for Redis hash operations.
 */
public interface AsyncHashCommands {

  /**
   * Async version of <b><a href="http://redis.io/commands/hset">HSET Command</a></b>
   * Sets the specified field in the hash stored at key to value.
   * @param key the key
   * @param field the field
   * @param value the value
   * @return CompletableFuture that completes with the number of fields that were added
   */
  CompletableFuture<Long> hset(String key, String field, String value);

  /**
   * Async version of <b><a href="http://redis.io/commands/hset">HSET Command</a></b>
   * Sets multiple fields in the hash stored at key to their respective values.
   * @param key the key
   * @param hash the hash containing fields and values
   * @return CompletableFuture that completes with the number of fields that were added
   */
  CompletableFuture<Long> hset(String key, Map<String, String> hash);

  /**
   * Async version of <b><a href="http://redis.io/commands/hget">HGET Command</a></b>
   * Returns the value associated with field in the hash stored at key.
   * @param key the key
   * @param field the field
   * @return CompletableFuture that completes with the value associated with field
   */
  CompletableFuture<String> hget(String key, String field);

  /**
   * Async version of <b><a href="http://redis.io/commands/hsetnx">HSETNX Command</a></b>
   * Sets field in the hash stored at key to value, only if field does not yet exist.
   * @param key the key
   * @param field the field
   * @param value the value
   * @return CompletableFuture that completes with 1 if field was set, 0 if field already existed
   */
  CompletableFuture<Long> hsetnx(String key, String field, String value);

  /**
   * Async version of <b><a href="http://redis.io/commands/hmset">HMSET Command</a></b>
   * Sets multiple hash fields to multiple values.
   * @param key the key
   * @param hash the hash containing fields and values
   * @return CompletableFuture that completes with "OK"
   */
  CompletableFuture<String> hmset(String key, Map<String, String> hash);

  /**
   * Async version of <b><a href="http://redis.io/commands/hmget">HMGET Command</a></b>
   * Returns the values associated with the specified fields in the hash stored at key.
   * @param key the key
   * @param fields the fields
   * @return CompletableFuture that completes with list of values associated with the given fields
   */
  CompletableFuture<List<String>> hmget(String key, String... fields);

  /**
   * Async version of <b><a href="http://redis.io/commands/hincrby">HINCRBY Command</a></b>
   * Increments the number stored at field in the hash stored at key by increment.
   * @param key the key
   * @param field the field
   * @param value the increment value
   * @return CompletableFuture that completes with the value at field after the increment operation
   */
  CompletableFuture<Long> hincrBy(String key, String field, long value);

  /**
   * Async version of <b><a href="http://redis.io/commands/hincrbyfloat">HINCRBYFLOAT Command</a></b>
   * Increment the specified field of a hash stored at key by the specified increment.
   * @param key the key
   * @param field the field
   * @param value the increment value
   * @return CompletableFuture that completes with the value at field after the increment operation
   */
  CompletableFuture<Double> hincrByFloat(String key, String field, double value);

  /**
   * Async version of <b><a href="http://redis.io/commands/hexists">HEXISTS Command</a></b>
   * Returns if field is an existing field in the hash stored at key.
   * @param key the key
   * @param field the field
   * @return CompletableFuture that completes with true if the hash contains field, false otherwise
   */
  CompletableFuture<Boolean> hexists(String key, String field);

  /**
   * Async version of <b><a href="http://redis.io/commands/hdel">HDEL Command</a></b>
   * Removes the specified fields from the hash stored at key.
   * @param key the key
   * @param fields the fields to remove
   * @return CompletableFuture that completes with the number of fields that were removed
   */
  CompletableFuture<Long> hdel(String key, String... fields);

  /**
   * Async version of <b><a href="http://redis.io/commands/hlen">HLEN Command</a></b>
   * Returns the number of fields contained in the hash stored at key.
   * @param key the key
   * @return CompletableFuture that completes with number of fields in the hash
   */
  CompletableFuture<Long> hlen(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/hkeys">HKEYS Command</a></b>
   * Returns all field names in the hash stored at key.
   * @param key the key
   * @return CompletableFuture that completes with set of fields in the hash
   */
  CompletableFuture<Set<String>> hkeys(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/hvals">HVALS Command</a></b>
   * Returns all values in the hash stored at key.
   * @param key the key
   * @return CompletableFuture that completes with list of values in the hash
   */
  CompletableFuture<List<String>> hvals(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/hgetall">HGETALL Command</a></b>
   * Returns all fields and values of the hash stored at key.
   * @param key the key
   * @return CompletableFuture that completes with map of all fields and their values
   */
  CompletableFuture<Map<String, String>> hgetAll(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/hrandfield">HRANDFIELD Command</a></b>
   * Returns a random field from the hash stored at key.
   * @param key the key
   * @return CompletableFuture that completes with the random field name
   */
  CompletableFuture<String> hrandfield(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/hrandfield">HRANDFIELD Command</a></b>
   * Returns an array of count random fields from the hash stored at key.
   * @param key the key
   * @param count the number of fields to return
   * @return CompletableFuture that completes with list of random fields
   */
  CompletableFuture<List<String>> hrandfield(String key, long count);

  /**
   * Async version of <b><a href="http://redis.io/commands/hrandfield">HRANDFIELD Command</a></b>
   * Returns an array of count random fields and their values from the hash stored at key.
   * @param key the key
   * @param count the number of field-value pairs to return
   * @return CompletableFuture that completes with list of random field-value pairs
   */
  CompletableFuture<List<Map.Entry<String, String>>> hrandfieldWithValues(String key, long count);

  /**
   * Async version of <b><a href="http://redis.io/commands/hscan">HSCAN Command</a></b>
   * Incrementally iterate hash fields and associated values.
   * @param key the key
   * @param cursor the cursor
   * @return CompletableFuture that completes with scan result containing field-value pairs
   */
  default CompletableFuture<ScanResult<Entry<String, String>>> hscan(String key, String cursor) {
    return hscan(key, cursor, new ScanParams());
  }

  /**
   * Async version of <b><a href="http://redis.io/commands/hscan">HSCAN Command</a></b>
   * Incrementally iterate hash fields and associated values.
   * @param key the key
   * @param cursor the cursor
   * @param params the scan parameters
   * @return CompletableFuture that completes with scan result containing field-value pairs
   */
  CompletableFuture<ScanResult<Map.Entry<String, String>>> hscan(String key, String cursor, ScanParams params);

  /**
   * Async version of <b><a href="http://redis.io/commands/hscan">HSCAN Command</a></b>
   * Incrementally iterate hash fields without values.
   * @param key the key
   * @param cursor the cursor
   * @return CompletableFuture that completes with scan result containing only fields
   */
  default CompletableFuture<ScanResult<String>> hscanNoValues(String key, String cursor) {
    return hscanNoValues(key, cursor, new ScanParams());
  }

  /**
   * Async version of <b><a href="http://redis.io/commands/hscan">HSCAN Command</a></b>
   * Incrementally iterate hash fields without values.
   * @param key the key
   * @param cursor the cursor
   * @param params the scan parameters
   * @return CompletableFuture that completes with scan result containing only fields
   */
  CompletableFuture<ScanResult<String>> hscanNoValues(String key, String cursor, ScanParams params);

  /**
   * Async version of <b><a href="http://redis.io/commands/hstrlen">HSTRLEN Command</a></b>
   * Returns the string length of the value associated with field in the hash stored at key.
   * @param key the key
   * @param field the field
   * @return CompletableFuture that completes with the string length of the value
   */
  CompletableFuture<Long> hstrlen(String key, String field);
} 