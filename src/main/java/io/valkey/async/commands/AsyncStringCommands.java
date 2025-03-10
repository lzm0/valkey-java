package io.valkey.async.commands;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import io.valkey.params.GetExParams;
import io.valkey.params.LCSParams;
import io.valkey.params.SetParams;
import io.valkey.resps.LCSMatchResult;

public interface AsyncStringCommands extends AsyncBitCommands {

  /**
   * Async version of <b><a href="http://redis.io/commands/set">Set Command</a></b>
   * Set the string value as value of the key. The string can't be longer than 1073741824 bytes (1 GB).
   * <p>
   * Time complexity: O(1)
   * @param key
   * @param value
   * @return CompletableFuture that completes with OK
   */
  CompletableFuture<String> set(String key, String value);

  /**
   * Async version of <b><a href="http://redis.io/commands/set">Set Command</a></b>
   * Set the string value as value of the key. Can be used with optional params.
   * <p>
   * Time complexity: O(1)
   * @param key
   * @param value
   * @param params {@link SetParams}
   * @return CompletableFuture that completes with simple-string-reply {@code OK} if {@code SET} was executed correctly, or {@code null}
   * if the {@code SET} operation was not performed because the user specified the NX or XX option
   * but the condition was not met.
   */
  CompletableFuture<String> set(String key, String value, SetParams params);

  /**
   * Async version of <b><a href="http://redis.io/commands/get">Get Command</a></b>
   * Get the value of the specified key. If the key does not exist the special value 'nil' is
   * returned. If the value stored at key is not a string an error is returned because GET can only
   * handle string values.
   * <p>
   * Time complexity: O(1)
   * @param key
   * @return CompletableFuture that completes with the value stored in key
   */
  CompletableFuture<String> get(String key);

  CompletableFuture<String> setGet(String key, String value);

  CompletableFuture<String> setGet(String key, String value, SetParams params);

  /**
   * Async version of <b><a href="http://redis.io/commands/getdel">GetDel Command</a></b>
   * Get the value of key and delete the key. This command is similar to GET, except for the fact
   * that it also deletes the key on success (if and only if the key's value type is a string).
   * <p>
   * Time complexity: O(1)
   * @param key
   * @return CompletableFuture that completes with the value stored in key
   */
  CompletableFuture<String> getDel(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/getex">GetEx Command</a></b>
   * Get the value of key and optionally set its expiration. GETEX is similar to {@link AsyncStringCommands#get(String) GET},
   * but is a write command with additional options:
   * EX seconds -- Set the specified expire time, in seconds.
   * PX milliseconds -- Set the specified expire time, in milliseconds.
   * EXAT timestamp-seconds -- Set the specified Unix time at which the key will expire, in seconds.
   * PXAT timestamp-milliseconds -- Set the specified Unix time at which the key will expire, in milliseconds.
   * PERSIST -- Remove the time to live associated with the key.
   * <p>
   * Time complexity: O(1)
   * @param key
   * @param params {@link GetExParams}
   * @return CompletableFuture that completes with the value stored in key
   */
  CompletableFuture<String> getEx(String key, GetExParams params);

  /**
   * Async version of <b><a href="http://redis.io/commands/setrange">SetRange Command</a></b>
   * GETRANGE overwrite part of the string stored at key, starting at the specified offset, for the entire
   * length of value. If the offset is larger than the current length of the string at key, the string is
   * padded with zero-bytes to make offset fit. Non-existing keys are considered as empty strings, so this
   * command will make sure it holds a string large enough to be able to set value at offset.
   * <p>
   * Time complexity: O(1)
   * @param key
   * @param offset
   * @param value
   * @return CompletableFuture that completes with the length of the string after it was modified by the command
   */
  CompletableFuture<Long> setrange(String key, long offset, String value);

  /**
   * Async version of <b><a href="http://redis.io/commands/getrange">GetRange Command</a></b>
   * Return the substring of the string value stored at key, determined by the offsets start
   * and end (both are inclusive). Negative offsets can be used in order to provide an offset starting
   * from the end of the string. So -1 means the last character, -2 the penultimate and so forth.
   * <p>
   * Time complexity: O(N) where N is the length of the returned string
   * @param key
   * @param startOffset
   * @param endOffset
   * @return CompletableFuture that completes with the substring
   */
  CompletableFuture<String> getrange(String key, long startOffset, long endOffset);

  /**
   * Async version of <b><a href="http://redis.io/commands/getset">GetSet Command</a></b>
   * GETSET is an atomic set this value and return the old value command. Set key to the string
   * value and return the old value stored at key. The string can't be longer than 1073741824 byte (1 GB).
   * <p>
   * Time complexity: O(1)
   * @param key
   * @param value
   * @return CompletableFuture that completes with the old value that was stored in key
   * @deprecated Use {@link AsyncStringCommands#setGet(java.lang.String, java.lang.String)}.
   */
  @Deprecated
  CompletableFuture<String> getSet(String key, String value);

  /**
   * Async version of <b><a href="http://redis.io/commands/setnx">SetNE Command</a></b>
   * SETNX works exactly like {@link AsyncStringCommands#set(String, String) SET} with the only difference that if
   * the key already exists no operation is performed. SETNX actually means "SET if Not Exists".
   * <p>
   * Time complexity: O(1)
   * @param key
   * @param value
   * @return CompletableFuture that completes with 1 if the key was set, 0 otherwise
   */
  CompletableFuture<Long> setnx(String key, String value);

  /**
   * Async version of <b><a href="http://redis.io/commands/setex">SetEx Command</a></b>
   * The command is exactly equivalent to the following group of commands:
   * {@link AsyncStringCommands#set(String, String) SET} + {@link AsyncKeyBinaryCommands#expire(byte[], long) EXPIRE}.
   * The operation is atomic.
   * <p>
   * Time complexity: O(1)
   * @param key
   * @param seconds
   * @param value
   * @return CompletableFuture that completes with OK
   */
  CompletableFuture<String> setex(String key, long seconds, String value);

  /**
   * Async version of <b><a href="http://redis.io/commands/psetex">PSetEx Command</a></b>
   * PSETEX works exactly like {@link AsyncStringCommands#setex(String, long, String) SETEX} with the sole difference
   * that the expire time is specified in milliseconds instead of seconds.
   * <p>
   * Time complexity: O(1)
   * @param key
   * @param milliseconds
   * @param value
   * @return CompletableFuture that completes with OK
   */
  CompletableFuture<String> psetex(String key, long milliseconds, String value);

  /**
   * Async version of <b><a href="http://redis.io/commands/mget">MGet Command</a></b>
   * Get the values of all the specified keys. If one or more keys don't exist or is not of type
   * String, a 'nil' value is returned instead of the value of the specified key, but the operation
   * never fails.
   * <p>
   * Time complexity: O(1) for every key
   * @param keys
   * @return CompletableFuture that completes with multi bulk reply
   */
  CompletableFuture<List<String>> mget(String... keys);

  /**
   * Async version of <b><a href="http://redis.io/commands/mset">MSet Command</a></b>
   * Set the respective keys to the respective values. MSET will replace old values with new
   * values, while {@link AsyncStringCommands#msetnx(String...) MSETNX} will not perform any operation at all even
   * if just a single key already exists.
   * <p>
   * Because of this semantic MSETNX can be used in order to set different keys representing
   * different fields of an unique logic object in a way that ensures that either all the fields or
   * none at all are set.
   * <p>
   * Both MSET and MSETNX are atomic operations. This means that for instance if the keys A and B
   * are modified, another connection talking to Redis can either see the changes to both A and B at
   * once, or no modification at all.
   * @param keysvalues pairs of keys and their values
   *                   e.g mset("foo", "foovalue", "bar", "barvalue")
   * @return CompletableFuture that completes with OK
   */
  CompletableFuture<String> mset(String... keysvalues);

  /**
   * Async version of <b><a href="http://redis.io/commands/msetnx">MSetNX Command</a></b>
   * Set the respective keys to the respective values. {@link AsyncStringCommands#mset(String...) MSET} will
   * replace old values with new values, while MSETNX will not perform any operation at all even if
   * just a single key already exists.
   * <p>
   * Because of this semantic MSETNX can be used in order to set different keys representing
   * different fields of an unique logic object in a way that ensures that either all the fields or
   * none at all are set.
   * <p>
   * Both MSET and MSETNX are atomic operations. This means that for instance if the keys A and B
   * are modified, another connection talking to Redis can either see the changes to both A and B at
   * once, or no modification at all.
   * @param keysvalues pairs of keys and their values
   *                   e.g msetnx("foo", "foovalue", "bar", "barvalue")
   * @return CompletableFuture that completes with 1 if the all the keys were set, 0 if no key was set (at least one key already existed)
   */
  CompletableFuture<Long> msetnx(String... keysvalues);

  /**
   * Async version of <b><a href="http://redis.io/commands/incr">Incr Command</a></b>
   * Increment the number stored at key by one. If the key does not exist or contains a value of a
   * wrong type, set the key to the value of "0" before to perform the increment operation.
   * <p>
   * INCR commands are limited to 64-bit signed integers.
   * <p>
   * Note: this is actually a string operation, that is, in Redis there are not "integer" types.
   * Simply the string stored at the key is parsed as a base 10 64-bit signed integer, incremented,
   * and then converted back as a string.
   * <p>
   * Time complexity: O(1)
   * @param key the key to increment
   * @return CompletableFuture that completes with the value of the key after the increment
   */
  CompletableFuture<Long> incr(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/incrby">IncrBy Command</a></b>
   * INCRBY work just like {@link AsyncStringCommands#incr(String) INCR} but instead to increment by 1 the
   * increment is integer.
   * <p>
   * INCR commands are limited to 64-bit signed integers.
   * <p>
   * Note: this is actually a string operation, that is, in Redis there are not "integer" types.
   * Simply the string stored at the key is parsed as a base 10 64-bit signed integer, incremented,
   * and then converted back as a string.
   * <p>
   * Time complexity: O(1)
   * @param key the key to increment
   * @param increment the value to increment by
   * @return CompletableFuture that completes with the value of the key after the increment
   */
  CompletableFuture<Long> incrBy(String key, long increment);

  /**
   * Async version of <b><a href="http://redis.io/commands/incrbyfloat">IncrByFloat Command</a></b>
   * INCRBYFLOAT work just like {@link AsyncStringCommands#incrBy(String, long)} INCRBY} but increments by floats
   * instead of integers.
   * <p>
   * INCRBYFLOAT commands are limited to double precision floating point values.
   * <p>
   * Note: this is actually a string operation, that is, in Redis there are not "double" types.
   * Simply the string stored at the key is parsed as a base double precision floating point value,
   * incremented, and then converted back as a string. There is no DECRYBYFLOAT but providing a
   * negative value will work as expected.
   * <p>
   * Time complexity: O(1)
   * @param key the key to increment
   * @param increment the value to increment by
   * @return CompletableFuture that completes with the value of the key after the increment
   */
  CompletableFuture<Double> incrByFloat(String key, double increment);

  /**
   * Async version of <b><a href="http://redis.io/commands/decr">Decr Command</a></b>
   * Decrement the number stored at key by one. If the key does not exist or contains a value of a
   * wrong type, set the key to the value of "0" before to perform the decrement operation.
   * <p>
   * DECR commands are limited to 64-bit signed integers.
   * <p>
   * Note: this is actually a string operation, that is, in Redis there are not "integer" types.
   * Simply the string stored at the key is parsed as a base 10 64-bit signed integer, incremented,
   * and then converted back as a string.
   * <p>
   * Time complexity: O(1)
   * @param key the key to decrement
   * @return CompletableFuture that completes with the value of the key after the decrement
   */
  CompletableFuture<Long> decr(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/decrby">DecrBy Command</a></b>
   * DECRBY work just like {@link AsyncStringCommands#decr(String) DECR} but instead to decrement by 1 the
   * decrement is integer.
   * <p>
   * DECRBY commands are limited to 64-bit signed integers.
   * <p>
   * Note: this is actually a string operation, that is, in Redis there are not "integer" types.
   * Simply the string stored at the key is parsed as a base 10 64-bit signed integer, incremented,
   * and then converted back as a string.
   * <p>
   * Time complexity: O(1)
   * @param key the key to decrement
   * @param decrement the value to decrement by
   * @return CompletableFuture that completes with the value of the key after the decrement
   */
  CompletableFuture<Long> decrBy(String key, long decrement);

  /**
   * Async version of <b><a href="http://redis.io/commands/append">Append Command</a></b>
   * If the key already exists and is a string, this command appends the provided value at the end
   * of the string. If the key does not exist it is created and set as an empty string, so APPEND
   * will be very similar to SET in this special case.
   * <p>
   * Time complexity: O(1). The amortized time complexity is O(1) assuming the appended value is
   * small and the already present value is of any size, since the dynamic string library used by
   * Redis will double the free space available on every reallocation.
   * @param key the key to append to
   * @param value the value to append
   * @return CompletableFuture that completes with the total length of the string after the append operation.
   */
  CompletableFuture<Long> append(String key, String value);

  /**
   * Async version of <b><a href="http://redis.io/commands/substr">SubStr Command</a></b>
   * Return a subset of the string from offset start to offset end (both offsets are inclusive).
   * Negative offsets can be used in order to provide an offset starting from the end of the string.
   * So -1 means the last char, -2 the penultimate and so forth.
   * <p>
   * The function handles out of range requests without raising an error, but just limiting the
   * resulting range to the actual length of the string.
   * <p>
   * Time complexity: O(start+n) (with start being the start index and n the total length of the
   * requested range). Note that the lookup part of this command is O(1) so for small strings this
   * is actually an O(1) command.
   * @param key
   * @param start
   * @param end
   * @return CompletableFuture that completes with the substring
   */
  CompletableFuture<String> substr(String key, int start, int end);

  /**
   * Async version of <b><a href="http://redis.io/commands/strlen">StrLen Command</a></b>
   * Return the length of the string value stored at key.
   * @param key
   * @return CompletableFuture that completes with the length of the string at key, or 0 when key does not exist
   */
  CompletableFuture<Long> strlen(String key);

  /**
   * Async version of Calculate the longest common subsequence of keyA and keyB.
   * @param keyA
   * @param keyB
   * @param params
   * @return CompletableFuture that completes with LCSMatchResult according to LCSParams
   */
  CompletableFuture<LCSMatchResult> lcs(String keyA, String keyB, LCSParams params);
} 